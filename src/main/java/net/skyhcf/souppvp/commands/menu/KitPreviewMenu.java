package net.skyhcf.souppvp.commands.menu;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.Menu;
import net.frozenorb.qlib.util.ItemBuilder;
import net.skyhcf.souppvp.commands.menu.button.KitItemButton;
import net.skyhcf.souppvp.kit.ArmorType;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class KitPreviewMenu extends Menu {

    private final Kit kit;

    public KitPreviewMenu(Kit kit){
        this.kit = kit;
    }

    @Override
    public String getTitle(Player player) {
        return "Kit Preview - " + kit.getName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for(int i = 0; i < 36; i++){
            buttons.put(i, new KitItemButton(kit.getItems().getOrDefault(i, ItemBuilder.of(Material.AIR).build())));
        }
        buttons.put(44, new KitItemButton(kit.getArmorItems().getOrDefault(ArmorType.HELMET, ItemBuilder.of(Material.AIR).build())));
        buttons.put(43, new KitItemButton(kit.getArmorItems().getOrDefault(ArmorType.CHESTPLATE, ItemBuilder.of(Material.AIR).build())));
        buttons.put(42, new KitItemButton(kit.getArmorItems().getOrDefault(ArmorType.LEGGINGS, ItemBuilder.of(Material.AIR).build())));
        buttons.put(41, new KitItemButton(kit.getArmorItems().getOrDefault(ArmorType.BOOTS, ItemBuilder.of(Material.AIR).build())));
        return buttons;
    }
}
