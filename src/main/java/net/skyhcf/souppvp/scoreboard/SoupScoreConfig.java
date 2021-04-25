package net.skyhcf.souppvp.scoreboard;

import net.frozenorb.qlib.scoreboard.ScoreboardConfiguration;
import net.frozenorb.qlib.scoreboard.TitleGetter;
import net.skyhcf.atmosphere.shared.utils.Locale;

public class SoupScoreConfig {

    public static ScoreboardConfiguration create(){
        ScoreboardConfiguration configuration = new ScoreboardConfiguration();
        configuration.setTitleGetter(new TitleGetter("&b&lSky &7" + Locale.VERTICAL_STRAIGHT_LINE.getCharacter() + "&r SoupPvP".replace("&", "\u00a7")));
        configuration.setScoreGetter(new SoupScoreGetter());
        return configuration;
    }

}
