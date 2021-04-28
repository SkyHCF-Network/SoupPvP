package net.skyhcf.souppvp.commands;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.souppvp.SoupPvP;
import net.skyhcf.souppvp.kit.Kit;
import net.skyhcf.souppvp.profile.KitProfile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand {

    @Command(names = {"economy set"}, permission = "souppvp.admin")
    public static void economySet(CommandSender sender, @Param(name = "target")OfflinePlayer target, @Param(name = "amount") Integer amount){
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(target);
        kitProfile.setBalance(amount);
        kitProfile.save();
        sender.sendMessage(BukkitChat.format("&6Set balance of &r" + SharedAPI.formatNameOnScope(target.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + "&r &6to &a$" + amount + "&6."));
    }

    @Command(names = {"economy add"}, permission = "souppvp.admin")
    public static void economyAdd(CommandSender sender, @Param(name = "target")OfflinePlayer target, @Param(name = "amount") Integer amount){
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(target);
        kitProfile.incrementBalance(amount);
        kitProfile.save();
        sender.sendMessage(BukkitChat.format("&6Added &a$" + amount + "&r &6to " + SharedAPI.formatNameOnScope(target.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + "&r&6's balance."));
    }

    @Command(names = {"economy remove"}, permission = "souppvp.admin")
    public static void economyRemove(CommandSender sender, @Param(name = "target")OfflinePlayer target, @Param(name = "amount") Integer amount){
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(target);
        if(amount > kitProfile.getBalance()){
            sender.sendMessage(BukkitChat.format(SharedAPI.formatNameOnScope(target.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + "&r &cdoes not have sufficient funds."));
            return;
        }
        kitProfile.setBalance(kitProfile.getBalance() - amount);
        kitProfile.save();
        sender.sendMessage(BukkitChat.format("&6Removed &a$" + amount + " from &r" + SharedAPI.formatNameOnScope(target.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + "&r&6's balance."));
    }

    @Command(names = {"balance", "bal"}, permission = "")
    public static void balance(Player sender, @Param(name = "target", defaultValue = "self") OfflinePlayer player){
        KitProfile kitProfile = SoupPvP.getInstance().getKitProfileManager().get(player);
        sender.sendMessage(BukkitChat.format("&6Balance of &r" + SharedAPI.formatNameOnScope(player.getUniqueId(), SharedAPI.getServer(Bukkit.getServerName())) + "&r&6: &a$" + kitProfile.getBalance()));
    }


}
