package net.skyhcf.souppvp.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.utils.Cooldowns;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {

    @Command(names = {"spawn"}, permission = "")
    public static void spawn(CommandSender sender, @Param(name = "target", defaultValue = "self") Player target){
        if(Cooldowns.isOnCooldown("combat", target)){
            target.sendMessage(BukkitChat.format("&cYou cannot enter spawn while you are combat tagged!"));
            return;
        }
        target.sendMessage(BukkitChat.format("&aYou have been teleported to spawn."));
        target.teleport(target.getWorld().getSpawnLocation().add(0.5, 0, 0.5));
    }

}
