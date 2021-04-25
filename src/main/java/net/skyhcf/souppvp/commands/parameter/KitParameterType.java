package net.skyhcf.souppvp.commands.parameter;

import net.frozenorb.qlib.command.ParameterType;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class KitParameterType implements ParameterType {

    @Override
    public Object transform(CommandSender commandSender, String s) {
        for(Kit kit : SoupPvP.getInstance().getKitManager().getKits()){
            if(kit.getName().equalsIgnoreCase(s)) return kit;
        }
        commandSender.sendMessage(BukkitChat.LIGHT_RED + "No kit with name \"" + s + "\" found.");
        return null;
    }

    @Override
    public List<String> tabComplete(Player player, Set set, String s) {
        return null;
    }
}
