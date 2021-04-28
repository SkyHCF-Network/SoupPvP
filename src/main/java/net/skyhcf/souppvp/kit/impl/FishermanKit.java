package net.skyhcf.souppvp.kit.impl;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.souppvp.kit.ArmorType;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.utils.MiscUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class FishermanKit implements Kit {

    @Override
    public String getName() {
        return "Fisherman";
    }

    @Override
    public String getDescription() {
        return "Use this to gain items and experience.";
    }

    @Override
    public Material getGUIMaterial() {
        return Material.FISHING_ROD;
    }

    @Override
    public boolean requiresPermission() {
        return true;
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        Map<Integer, ItemStack> items = Maps.newHashMap();
        items.put(0, ItemBuilder.of(Material.STONE_SWORD).name("&eStone Sword").setUnbreakable(true).build());
        items.put(1, ItemBuilder.of(Material.FISHING_ROD).name("&eFishing Rod").setUnbreakable(true).build());
        return MiscUtils.fillMapWithSoup(items);
    }

    @Override
    public Map<ArmorType, ItemStack> getArmorItems() {
        Map<ArmorType, ItemStack> armor = Maps.newHashMap();
        armor.put(ArmorType.HELMET, ItemBuilder.of(Material.LEATHER_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setUnbreakable(true).name("&eLeather Helmet").build());
        armor.put(ArmorType.CHESTPLATE, ItemBuilder.of(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setUnbreakable(true).name("&eLeather Chestplate").build());
        armor.put(ArmorType.LEGGINGS, ItemBuilder.of(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setUnbreakable(true).name("&eLeather Leggings").build());
        armor.put(ArmorType.BOOTS, ItemBuilder.of(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setUnbreakable(true).name("&eLeather Boots").build());
        return armor;
    }
}
