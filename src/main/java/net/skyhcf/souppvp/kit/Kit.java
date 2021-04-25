package net.skyhcf.souppvp.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface Kit {

    String getName();
    String getDescription();
    Material getGUIMaterial();
    boolean requiresPermission();
    Map<Integer, ItemStack> getItems();
    Map<ArmorType, ItemStack> getArmorItems();

}
