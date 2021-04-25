package net.skyhcf.souppvp.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.commands.menu.KitMenu;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.entity.Player;

public class KitCommand {

    @Command(names = {"kit"}, permission = "")
    public static void kit(Player player, @Param(name = "kit", defaultValue = "kitMenu")String input){
        Kit kit = SoupPvP.getInstance().getKitManager().get(input);
        if(kit == null){
            new KitMenu().openMenu(player);
            return;
        }
        SoupPvP.getInstance().getKitManager().equip(player, kit);
    }

}
