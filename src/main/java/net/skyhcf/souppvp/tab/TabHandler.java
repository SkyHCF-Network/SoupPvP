package net.skyhcf.souppvp.tab;

import net.frozenorb.qlib.tab.LayoutProvider;
import net.frozenorb.qlib.tab.TabLayout;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.skyhcf.souppvp.SoupPvP;

public class TabHandler implements LayoutProvider {

    @Override
    public TabLayout provide(Player player) {
        TabLayout tabLayout = TabLayout.create(player);
        tabLayout.set(1, 0, "&b&lSkyHCF SoupPvP");
        tabLayout.set(1, 1, "&bOnline&f: &f" + (Bukkit.getOnlinePlayers().size()) + "/" + (Bukkit.getMaxPlayers()));

        tabLayout.set(0, 3, "&b&lYour Stats");
        tabLayout.set(0, 4, "&fKills: &b" + (SoupPvP.getInstance().getKitProfileManager().get(player).getKills()));
        tabLayout.set(0, 5, "&fKillstreak: &b" + (SoupPvP.getInstance().getKitProfileManager().get(player).getKillstreak()));
        tabLayout.set(0, 6, "&fDeaths: &b" + (SoupPvP.getInstance().getKitProfileManager().get(player).getDeaths()));
        tabLayout.set(0, 7, "&fBalance: &b$" + (SoupPvP.getInstance().getKitProfileManager().get(player).getBalance()));


        return tabLayout;
    }
}