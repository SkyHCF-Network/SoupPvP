package net.skyhcf.souppvp.scoreboard;

import net.frozenorb.qlib.scoreboard.ScoreFunction;
import net.frozenorb.qlib.scoreboard.ScoreGetter;
import net.frozenorb.qlib.util.LinkedList;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.profile.KitProfile;
import net.skyhcf.souppvp.utils.Cooldowns;
import org.bukkit.entity.Player;

public class SoupScoreGetter implements ScoreGetter {

    @Override
    public void getScores(LinkedList<String> scores, Player player) {
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(player);
        scores.add("Kills&7: &b" + kitProfile.getKills());
        scores.add("Killstreak&7: &b" + kitProfile.getKillstreak());
        scores.add("Deaths&7: &b" + kitProfile.getDeaths());
        if(Cooldowns.isOnCooldown("combat", player)){
            scores.add("&4&lCombat&7: &c" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("combat", player) / 1000.0f));
        }
        if(Cooldowns.isOnCooldown("golden_apple", player)){
            scores.add("&e&lGolden Apple&7: &c" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("golden_apple", player) / 1000.0f));
        }
        if(Cooldowns.isOnCooldown("enderpearl", player)){
            scores.add("&3&lEnderpearl&7: &c" + ScoreFunction.TIME_FANCY.apply(Cooldowns.getCooldownForPlayerLong("enderpearl", player) / 1000.0f));
        }
        if(!scores.isEmpty()){
            scores.add("&r");
            scores.add("&7&owww.skyhcf.net");
            scores.addFirst("&7&m--------------------");
            scores.addLast("&1&7&m--------------------");
        }
    }
}
