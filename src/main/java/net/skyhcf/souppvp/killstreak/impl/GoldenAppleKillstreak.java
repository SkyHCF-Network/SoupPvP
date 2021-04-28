package net.skyhcf.souppvp.killstreak.impl;

import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.killstreak.Killstreak;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GoldenAppleKillstreak implements Killstreak {
    @Override
    public String getName() {
        return "Golden Apple";
    }

    @Override
    public int getKills() {
        return 10;
    }

    @Override
    public void runKillstreak(Player player) {
        player.getInventory().addItem(ItemBuilder.of(Material.GOLDEN_APPLE).amount(3).name(BukkitChat.YELLOW + "Golden Apple").build());
        Bukkit.broadcastMessage(BukkitChat.format(SharedAPI.formatNameOnScope(player.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + " &rhas gotten the &b" + getName() +" Killstreak&r! &7(" + getKills() + " kills)"));
    }
}
