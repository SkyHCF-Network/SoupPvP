package net.skyhcf.souppvp.commands.menu;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.pagination.PaginatedMenu;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.commands.menu.button.KitButton;
import org.bukkit.entity.Player;

import java.util.Map;

public class KitMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Kits List";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new KitButton(SoupPvP.getInstance().getKitManager().get("PvP")));
        return buttons;
    }
}
