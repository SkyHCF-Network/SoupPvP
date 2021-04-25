package net.skyhcf.souppvp.kit;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.kit.impl.PvPKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class KitManager {

    @Getter private List<Kit> kits = Lists.newArrayList();

    public KitManager(){
        kits.add(new PvPKit());
    }

    public void equip(Player player, Kit kit){
        player.getInventory().clear();
        for(int i = 0; i < 36; i++){
            player.getInventory().setItem(i, kit.getItems().getOrDefault(i, ItemBuilder.of(Material.AIR).build()));
        }
        player.getInventory().setHelmet(kit.getArmorItems().getOrDefault(ArmorType.HELMET, ItemBuilder.of(Material.AIR).build()));
        player.getInventory().setChestplate(kit.getArmorItems().getOrDefault(ArmorType.CHESTPLATE, ItemBuilder.of(Material.AIR).build()));
        player.getInventory().setLeggings(kit.getArmorItems().getOrDefault(ArmorType.LEGGINGS, ItemBuilder.of(Material.AIR).build()));
        player.getInventory().setBoots(kit.getArmorItems().getOrDefault(ArmorType.BOOTS, ItemBuilder.of(Material.AIR).build()));
        player.sendMessage(BukkitChat.format("&aYou have equipped the &e" + kit.getName() + "&r &akit."));
        player.setFoodLevel(50);
    }

    public Kit get(String name){
        for(Kit kit : kits){
            if(kit.getName().toLowerCase().equalsIgnoreCase(name)) return kit;
        }
        return null;
    }

}
