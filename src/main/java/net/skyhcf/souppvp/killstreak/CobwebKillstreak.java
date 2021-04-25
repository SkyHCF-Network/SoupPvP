package net.skyhcf.souppvp.killstreak;

import net.frozenorb.qlib.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CobwebKillstreak implements Killstreak {

    @Override
    public String getName() {
        return "Cobweb";
    }

    @Override
    public int getKills() {
        return 5;
    }

    @Override
    public void runKillstreak(Player player) {
        player.getInventory().addItem(ItemBuilder.of(Material.WEB).name("&eCobweb").amount(3).build());
    }
}
