package net.skyhcf.souppvp.commands.menu.button;

import com.google.common.collect.Lists;
import net.frozenorb.qlib.menu.Button;
import net.skyhcf.atmosphere.bukkit.utils.ColorUtil;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.commands.menu.KitPreviewMenu;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class KitButton extends Button {

    private final Kit kit;

    public KitButton(Kit kit){
        this.kit = kit;
    }

    @Override
    public String getName(Player player) {
        return ChatColor.YELLOW + kit.getName();
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = Lists.newArrayList();
        description.add("&r");
        description.add("&7" + kit.getDescription());
        if(kit.requiresPermission() && !player.hasPermission("kitpvp.kit." + kit.getName().toLowerCase())){
            description.add("&r");
            description.add("&c&lYou do not have access to this kit.");
        }
        description = ColorUtil.formatList(description);
        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return kit.getGUIMaterial();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        switch(clickType){
            case RIGHT: {
                new KitPreviewMenu(kit).openMenu(player);
                break;
            }case LEFT: {
                player.closeInventory();
                if(kit.requiresPermission() && !player.hasPermission("kitpvp.kit." + kit.getName().toLowerCase())){
                    player.sendMessage(BukkitChat.format("&cYou do not have access to use the &l" + kit.getName() + " &r&ckit."));
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    return;
                }
                SoupPvP.getInstance().getKitManager().equip(player, kit);
                break;
            }
        }
    }

}
