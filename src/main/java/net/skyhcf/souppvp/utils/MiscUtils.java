package net.skyhcf.souppvp.utils;

import com.google.common.collect.Lists;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;

import java.util.List;

public class MiscUtils {

    public static List<Kit> stringsToKits(List<String> strings){
        List<Kit> kits = Lists.newArrayList();
        for(String s : strings){
            kits.add(SoupPvP.getInstance().getKitManager().get(s));
        }
        return kits;
    }

    public static List<String> kitsToStrings(List<Kit> kits){
        List<String> strings = Lists.newArrayList();
        for(Kit k : kits){
            strings.add(k.getName().toLowerCase());
        }
        return strings;
    }

}
