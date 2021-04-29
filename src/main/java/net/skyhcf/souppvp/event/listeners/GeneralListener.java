package net.skyhcf.souppvp.event.listeners;

import com.google.common.collect.Lists;
import net.frozenorb.qlib.scoreboard.ScoreFunction;
import net.frozenorb.qlib.util.ItemUtils;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.atmosphere.shared.profile.Profile;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.event.PlayerKillEvent;
import net.skyhcf.souppvp.event.PlayerLaunchPadEvent;
import net.skyhcf.souppvp.event.ProfileSaveEvent;
import net.skyhcf.souppvp.killstreak.Killstreak;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.profile.KitProfile;
import net.skyhcf.souppvp.utils.Cooldowns;
import net.skyhcf.souppvp.utils.MiscUtils;
import net.skyhcf.souppvp.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralListener implements Listener {

    private static final List<Material> allowedDropPickupItems = Lists.newArrayList();

    public static List<Block> blocksToRemove = Lists.newArrayList();

    public GeneralListener(){
        allowedDropPickupItems.clear();
        allowedDropPickupItems.add(Material.GOLDEN_APPLE);
        allowedDropPickupItems.add(Material.DIAMOND_SWORD);
        allowedDropPickupItems.add(Material.DIAMOND_HELMET);
        allowedDropPickupItems.add(Material.DIAMOND_CHESTPLATE);
        allowedDropPickupItems.add(Material.DIAMOND_LEGGINGS);
        allowedDropPickupItems.add(Material.DIAMOND_BOOTS);
        allowedDropPickupItems.add(Material.ENDER_PEARL);
        allowedDropPickupItems.add(Material.TRIPWIRE_HOOK);
        allowedDropPickupItems.add(Material.MUSHROOM_SOUP);
        allowedDropPickupItems.add(Material.BOWL);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        if(!SoupPvP.getInstance().getKitProfileManager().exists(e.getPlayer())){
            SoupPvP.getInstance().getKitProfileManager().createProfile(e.getPlayer());
        }
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation().add(0.5, 0.5, 0.5));
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        if (Cooldowns.isOnCooldown("combat", e.getPlayer().getUniqueId())) e.getPlayer().setHealth(0);
    }

    @EventHandler
    public void profileSave(ProfileSaveEvent e){
        SoupPvP.getInstance().getServer().getConsoleSender().sendMessage(BukkitChat.format("&a[SoupPvP] Called &eProfileSaveEvent &a- " + e.getKitProfile().getUuid().toString()));
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e){
        if(e.getEntity().getKiller() == null || e.getEntity().getKiller() instanceof Player) {
            Profile attackerProfile = SharedAPI.getProfile(e.getEntity().getKiller().getUniqueId());
            Profile victimProfile = SharedAPI.getProfile(e.getEntity().getUniqueId());

            KitProfile attackerKitProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getEntity().getKiller());
            KitProfile victimKitProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getEntity());

            int attackerKills = attackerKitProfile.getKills();
            int victimKills = victimKitProfile.getKills();

            ItemStack item = e.getEntity().getKiller().getItemInHand();

            if (item != null && item.getType() == Material.AIR) {
                item = null;
            }

            if (e.getEntity().getKiller().getItemInHand() == null) {
                e.setDeathMessage(BukkitChat.format(SharedAPI.formatNameOnScope(attackerProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + attackerKills + "]&r killed &r" + SharedAPI.formatNameOnScope(victimProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + victimKills + "] &rwith &btheir fists&r."));
            } else {
                e.setDeathMessage(BukkitChat.format(SharedAPI.formatNameOnScope(attackerProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + attackerKills + "]&r killed &r" + SharedAPI.formatNameOnScope(victimProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + victimKills + "] &rwith &b" + (item == null ? "their fists" : ItemUtils.getName(item)) + "&r."));
            }
            e.getDrops().removeIf(drop -> !allowedDropPickupItems.contains(drop.getType()));
            SoupPvP.getInstance().getServer().getPluginManager().callEvent(new PlayerKillEvent(e.getEntity().getKiller(), e.getEntity()));
            (new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldowns.removeCooldown("combat", e.getEntity());
                    e.getEntity().spigot().respawn();
                }
            }).runTaskLater(SoupPvP.getInstance(), 7);
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e){
        e.getPlayer().sendMessage(BukkitChat.format("&aYou have been respawned."));
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player) && !(e.getDamager() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if(!e.isCancelled()) {
            Cooldowns.addCooldown("combat", attacker, 25);
            Cooldowns.addCooldown("combat", victim, 25);
        }
    }

    @EventHandler
    public void fish(PlayerFishEvent e){
        if(e.getCaught() != null && e.getCaught() instanceof Player){
            Player player = (Player) e.getCaught();
            player.setNoDamageTicks(7);
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e){
        if(e.getPlayer().getLocation().clone().subtract(0, 2, 0).getBlock().getType().name().contains("PISTON") && e.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK){
            //Sulfur.instance.getPlayerDataManager().getPlayerData(e.getPlayer()).setExempt(true);
            if(Cooldowns.isOnCooldown("launchpad", e.getPlayer().getUniqueId())) {
                e.getPlayer().sendMessage(Util.color("&cYou are on cooldown for &c&l" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("launchpad", e.getPlayer()) / 1000.0f)));
                return;
            }
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).add(new Vector(0, 0.92, 0)));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
            Cooldowns.addCooldown("launchpad", e.getPlayer(), 30);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLaunchPadEvent(e.getPlayer(), e.getPlayer().getLocation()));
            if (Arrays.stream(e.getPlayer().getInventory().getContents()).filter(is -> is != null && !is.getType().equals(Material.AIR)).count() == 0) Bukkit.getServer().dispatchCommand(e.getPlayer(), "kit pvp");
/*            (new BukkitRunnable() {
                @Override
                public void run() {
                    Sulfur.instance.getPlayerDataManager().getPlayerData(e.getPlayer()).setExempt(false);
                }
            }).runTaskLater(SoupPvP.getInstance(), 2L);*/
        }
    }

    @EventHandler
    public void launchPad(PlayerLaunchPadEvent e){
        System.out.println("[SoupPvP] " + e.getPlayer().getName() + " used a launch pad!");
    }

    @EventHandler
    public void useSoup(PlayerInteractEvent e){
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getPlayer());
        if(e.getAction().name().contains("RIGHT_CLICK") && e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP){
            if(e.getPlayer().getHealth() < 20){
                ItemStack item = e.getPlayer().getItemInHand();
                e.getPlayer().setHealth(e.getPlayer().getHealth() + 7.5D > e.getPlayer().getMaxHealth() ? 20.0D : e.getPlayer().getHealth() + 7.5D);
                item.setType(Material.BOWL);
                e.getPlayer().updateInventory();
            }
        }
        if(e.getClickedBlock() != null){
            if(e.getClickedBlock().getType().name().contains("SIGN")){
                Sign sign = (Sign) e.getClickedBlock().getState();
                if(sign.getLine(0).contains("- Buy -")){
                    if(ItemUtils.get(sign.getLine(2)) != null){
                        try{
                            int amount = Integer.parseInt(sign.getLine(1));
                            int cost = Integer.parseInt(sign.getLine(3).replace("$", ""));
                            if(!(kitProfile.getBalance() >= cost)){
                                MiscUtils.showSignPacket(e.getPlayer(), sign, "&cInsufficient", "&cfunds for", sign.getLine(2), sign.getLine(3));
                                return;
                            }
                            kitProfile.setBalance(kitProfile.getBalance() - cost);
                            kitProfile.save();
                            MiscUtils.showSignPacket(e.getPlayer(), sign, "&aBOUGHT &r" + amount, "&rfor &a$64", "New Balance:", "$" + kitProfile.getBalance());
                            e.getPlayer().getInventory().addItem(ItemUtils.get(sign.getLine(2), amount));
                        }catch(NumberFormatException ex) {
                        }
                    }
                }else if(sign.getLine(0).contains("- Sell -")){
                    if(ItemUtils.get(sign.getLine(2)) != null){
                        try{
                            int amount = Integer.parseInt(sign.getLine(1));
                            int cost = Integer.parseInt(sign.getLine(3).replace("$", ""));
                            int amountInInventory = countItems(e.getPlayer(), ItemUtils.get(sign.getLine(2)).getType(), 0);
                            float pricePerItem = (float) cost / (float) amount;
                            int finalCost = (int) (amountInInventory * pricePerItem);
                            if(amountInInventory <= 0){
                                MiscUtils.showSignPacket(e.getPlayer(), sign, "&cYou cannot", "&cafford", sign.getLine(2), sign.getLine(3));
                                return;
                            }
                            kitProfile.incrementBalance(finalCost);
                            kitProfile.save();
                            removeItem(e.getPlayer(), ItemUtils.get(sign.getLine(2)), amount);
                            MiscUtils.showSignPacket(e.getPlayer(), sign, "&aSOLD &r" + ItemUtils.getName(ItemUtils.get(sign.getLine(2))), "&rfor &a" + cost, "New Balance:", "$" + kitProfile.getBalance());
                        }catch(NumberFormatException ex){}
                    }
                }else if(sign.getLine(0).contains("- Buy Kit -")){
                    if(SoupPvP.getInstance().getKitManager().get(sign.getLine(1)) != null){
                        try{
                            int cost = Integer.parseInt(sign.getLine(2).replace("$", ""));
                            if(kitProfile.getBalance() < cost){
                                MiscUtils.showSignPacket(e.getPlayer(), sign, "&cCannot buy", sign.getLine(1) + "&r&c;", "&cinsufficient funds.");
                                return;
                            }
                            if(kitProfile.getAllowedKits().contains(SoupPvP.getInstance().getKitManager().get(sign.getLine(1)))){
                                MiscUtils.showSignPacket(e.getPlayer(), sign, "&cYou own", sign.getLine(1), "&calready!", "");
                                return;
                            }
                            Kit kit = SoupPvP.getInstance().getKitManager().get(sign.getLine(1));
                            kitProfile.setBalance(kitProfile.getBalance() - cost);
                            kitProfile.getAllowedKits().add(kit);
                            MiscUtils.showSignPacket(e.getPlayer(), sign, "&aBOUGHT", sign.getLine(1), "New Balance:", "$" + kitProfile.getBalance());
                            kitProfile.save();
                        }catch (NumberFormatException ex){}
                    }
                }
            }
        }
    }

    public int countItems(Player player, Material material, int damageValue) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] items = inventory.getContents();
        int amount = 0;

        for (ItemStack item : items) {
            if (item != null) {
                boolean specialDamage = material.getMaxDurability() == (short) 0;

                if (item.getType() != null && item.getType() == material && (!specialDamage || item.getDurability() == (short) damageValue)) {
                    amount += item.getAmount();
                }
            }
        }

        return (amount);
    }

    public void removeItem(Player p, ItemStack it, int amount) {
        boolean specialDamage = it.getType().getMaxDurability() == (short) 0;

        for (int a = 0; a < amount; a++) {
            for (ItemStack i : p.getInventory()) {
                if (i != null) {
                    if (i.getType() == it.getType() && (!specialDamage || it.getDurability() == i.getDurability())) {
                        if (i.getAmount() == 1) {
                            p.getInventory().clear(p.getInventory().first(i));
                            break;
                        } else {
                            i.setAmount(i.getAmount() - 1);
                            break;
                        }
                    }
                }
            }
        }
        p.updateInventory();

    }


    @EventHandler
    public void drop(PlayerDropItemEvent e){
        if(!allowedDropPickupItems.contains(e.getItemDrop().getItemStack().getType())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void drop(PlayerPickupItemEvent e){
        if(!allowedDropPickupItems.contains(e.getItem().getItemStack().getType())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent e){
        e.setFoodLevel(50);
    }

    @EventHandler
    public void eat(PlayerItemConsumeEvent e){
        if(e.getItem().getType() == Material.GOLDEN_APPLE){
            if(Cooldowns.isOnCooldown("golden_apple", e.getPlayer())){
                e.getPlayer().sendMessage(BukkitChat.format("&cYou cannot eat another golden apple for another &l" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("golden_apple", e.getPlayer()) / 1000.0f) + "&r&c."));
                e.setCancelled(true);
                return;
            }
            Cooldowns.addCooldown("golden_apple", e.getPlayer(), 45);
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(e.getItem().getType() == Material.ENDER_PEARL){
            if(Cooldowns.isOnCooldown("enderpearl", e.getPlayer())){
                e.getPlayer().sendMessage(BukkitChat.format("&cYou cannot use another enderpearl for another &l" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("golden_apple", e.getPlayer()) / 1000.0f) + ""));
                e.setCancelled(true);
                e.getPlayer().updateInventory();
                return;
            }
            Cooldowns.addCooldown("enderpearl", e.getPlayer(), 16);
        }
    }

    @EventHandler
    public void playerKill(PlayerKillEvent e){

        KitProfile victimProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getVictim());
        KitProfile killerProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getKiller());

        victimProfile.incrementDeath();
        killerProfile.incrementKill();

        killerProfile.incrementBalance(20);
        e.getKiller().sendMessage(BukkitChat.format("&aYou have received &l$20 &r&afor killing &r" + e.getVictim().getDisplayName() + "&r&a."));

        victimProfile.setKillstreak(0);
        killerProfile.incrementKillstreak();

        victimProfile.save();
        killerProfile.save();

        for(Killstreak killstreak : SoupPvP.getInstance().getKillstreakManager().getKillstreaks()){
            if(killstreak.getKills() == killerProfile.getKillstreak()){
                killstreak.runKillstreak(e.getKiller());
            }
        }

    }

    @EventHandler
    public void noPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() != Material.WEB){
            if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                e.setCancelled(true);
            }
        }else{
            blocksToRemove.add(e.getBlock());
            (new BukkitRunnable() {
                @Override
                public void run() {
                    blocksToRemove.remove(e.getBlock());
                    e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
                }
            }).runTaskLater(SoupPvP.getInstance(), (20 * 8));
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }

}
