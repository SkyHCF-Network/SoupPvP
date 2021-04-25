package net.skyhcf.souppvp.killstreak;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Killstreak {

    String getName();
    int getKills();
    void runKillstreak(Player player);

}
