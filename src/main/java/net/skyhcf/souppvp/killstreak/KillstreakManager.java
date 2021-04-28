package net.skyhcf.souppvp.killstreak;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.frozenorb.qlib.util.ClassUtils;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.killstreak.impl.CobwebKillstreak;

import java.util.List;

public class KillstreakManager {

    @Getter private final List<Killstreak> killstreaks = Lists.newArrayList();

    public KillstreakManager(){
        ClassUtils.getClassesInPackage(SoupPvP.getInstance(), "net.skyhcf.souppvp.killstreak.impl").forEach(clazz -> {
            try {
                killstreaks.add((Killstreak) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Killstreak> getKillstreaks(int kills){
        List<Killstreak> killstreaks = Lists.newArrayList();
        for(Killstreak killstreak : this.killstreaks){
            if(killstreak.getKills() == kills){
                killstreaks.add(killstreak);
            }
        }
        return killstreaks;
    }

}
