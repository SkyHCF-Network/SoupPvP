package net.skyhcf.souppvp.utils;

import com.google.common.collect.Lists;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiscUtils {

    private static HashMap<Sign, BukkitRunnable> showSignTasks = new HashMap<>();

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

    public static void showSignPacket(Player player, final Sign sign, String... lines) {
        for(int i = 0; i < lines.length; i++){
            lines[i] = ChatColor.translateAlternateColorCodes('&', lines[i]);
        }
        player.sendSignChange(sign.getLocation(), lines);

        if (showSignTasks.containsKey(sign)) {
            showSignTasks.remove(sign).cancel();
        }

        BukkitRunnable br = new BukkitRunnable() {

            @Override
            public void run(){
                sign.update();
                showSignTasks.remove(sign);
            }

        };

        showSignTasks.put(sign, br);
        br.runTaskLater(SoupPvP.getInstance(), 90L);
    }

    public static Map<Integer, ItemStack> fillMapWithSoup(Map<Integer, ItemStack> itemMap){
        for(int i = 0; i < 36; i++){
            try {
                if (itemMap.get(i) == null) {
                    itemMap.put(i, ItemBuilder.of(Material.MUSHROOM_SOUP).name("&eMushroom Stew").build());
                }
            }catch(Exception e){
                itemMap.put(i, ItemBuilder.of(Material.MUSHROOM_SOUP).name("&eMushroom Stew").build());
            }
            i++;
        }
        return itemMap;
    }

}
