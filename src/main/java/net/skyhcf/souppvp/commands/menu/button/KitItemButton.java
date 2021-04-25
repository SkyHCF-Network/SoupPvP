package net.skyhcf.souppvp.commands.menu.button;

import net.frozenorb.qlib.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitItemButton extends Button {

    private final ItemStack itemStack;

    public KitItemButton(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    @Override
    public String getName(Player player) {
        return null;
    }

    @Override
    public List<String> getDescription(Player player) {
        return null;
    }

    @Override
    public Material getMaterial(Player player) {
        return null;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return itemStack;
    }
}
