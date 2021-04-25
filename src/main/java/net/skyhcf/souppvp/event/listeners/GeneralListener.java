package net.skyhcf.souppvp.event.listeners;

import com.google.common.collect.Lists;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import net.frozenorb.qlib.scoreboard.ScoreFunction;
import net.frozenorb.qlib.util.ItemUtils;
import net.skyhcf.atmosphere.shared.AtmosphereShared;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.atmosphere.shared.profile.Profile;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.event.PlayerKillEvent;
import net.skyhcf.souppvp.event.ProfileSaveEvent;
import net.skyhcf.souppvp.profile.KitProfile;
import net.skyhcf.souppvp.utils.Cooldowns;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class GeneralListener implements Listener {

    private static final List<Material> allowedDropPickupItems = Lists.newArrayList();

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
        if(!SoupPvP.getInstance().getKitProfileManager().exists(e.getPlayer())){
            SoupPvP.getInstance().getKitProfileManager().createProfile(e.getPlayer());
        }
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation().add(0, 0.5, 0));
    }

    @EventHandler
    public void profileSave(ProfileSaveEvent e){
        SoupPvP.getInstance().getServer().getConsoleSender().sendMessage(BukkitChat.format("&a[SoupPvP] Called &eProfileSaveEvent &a- " + e.getKitProfile().getUuid().toString()));
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e){
        Profile attackerProfile = SharedAPI.getProfile(e.getEntity().getKiller().getUniqueId());
        Profile victimProfile = SharedAPI.getProfile(e.getEntity().getUniqueId());

        KitProfile attackerKitProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getEntity().getKiller());
        KitProfile victimKitProfile = SoupPvP.getInstance().getKitProfileManager().get(e.getEntity());

        int attackerKills = attackerKitProfile.getKills();
        int victimKills = victimKitProfile.getKills();

        ItemStack item = e.getEntity().getKiller().getItemInHand();

        if(item != null && item.getType() == Material.AIR){
            item = null;
        }
        if(e.getEntity().getKiller().getItemInHand() == null) {
            e.setDeathMessage(BukkitChat.format(SharedAPI.formatNameOnScope(attackerProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + attackerKills + "]&r killed &r" + SharedAPI.formatNameOnScope(victimProfile.getUuid(), SharedAPI.getServer(Bukkit.getServerName())) + "&7[" + victimKills + "] &rwith &btheir fists&r."));
        }else {
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
    public void playerMove(PlayerMoveEvent e){
        if(e.getPlayer().getLocation().clone().subtract(0, 2, 0).getBlock().getType().name().contains("PISTON") && e.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK){
            //Sulfur.instance.getPlayerDataManager().getPlayerData(e.getPlayer()).setExempt(true);
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).add(new Vector(0, 0.92, 0)));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
            System.out.println("[SoupPvP] " + e.getPlayer().getName() + " used a launch pad!");
/*            (new BukkitRunnable() {
                @Override
                public void run() {
                    Sulfur.instance.getPlayerDataManager().getPlayerData(e.getPlayer()).setExempt(false);
                }
            }).runTaskLater(SoupPvP.getInstance(), 2L);*/
        }
    }

    @EventHandler
    public void useSoup(PlayerInteractEvent e){
        if(e.getAction().name().contains("RIGHT_CLICK") && e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP){
            if(e.getPlayer().getHealth() < 20){
                ItemStack item = e.getPlayer().getItemInHand();
                e.getPlayer().setHealth(e.getPlayer().getHealth() + 5.0D > e.getPlayer().getMaxHealth() ? 20.0D : e.getPlayer().getHealth() + 5.0D);
                item.setType(Material.BOWL);
                e.getPlayer().updateInventory();
            }
        }
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

        victimProfile.setKillstreak(0);
        killerProfile.incrementKillstreak();

        victimProfile.save();
        killerProfile.save();

    }

}
