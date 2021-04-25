package net.skyhcf.souppvp.kit.impl;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.kit.ArmorType;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PvPKit implements Kit {

    @Override
    public String getName() {
        return "PvP";
    }

    @Override
    public String getDescription() {
        return "The standard default kit with no additions.";
    }

    @Override
    public Material getGUIMaterial() {
        return Material.STONE_SWORD;
    }

    @Override
    public boolean requiresPermission() {
        return false;
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        Map<Integer, ItemStack> items = Maps.newHashMap();
        items.put(0, ItemBuilder.of(Material.IRON_SWORD).name(BukkitChat.format("&eIron Sword")).setUnbreakable(true).build());
        for(int i = 1; i < 36; i++){
            items.put(i, ItemBuilder.of(Material.MUSHROOM_SOUP).name("&eMushroom Stew").build());
        }
        return items;
    }

    @Override
    public Map<ArmorType, ItemStack> getArmorItems() {
        Map<ArmorType, ItemStack> armorItems = Maps.newHashMap();
        armorItems.put(ArmorType.HELMET, ItemBuilder.of(Material.CHAINMAIL_HELMET).name("&eChainmail Helmet").setUnbreakable(true).build());
        armorItems.put(ArmorType.CHESTPLATE, ItemBuilder.of(Material.CHAINMAIL_CHESTPLATE).name("&eChainmail Chestplate").setUnbreakable(true).build());
        armorItems.put(ArmorType.LEGGINGS, ItemBuilder.of(Material.CHAINMAIL_LEGGINGS).name("&eChainmail Leggings").setUnbreakable(true).build());
        armorItems.put(ArmorType.BOOTS, ItemBuilder.of(Material.CHAINMAIL_BOOTS).name("&eChainmail Boots").setUnbreakable(true).build());
        return armorItems;
    }

}
