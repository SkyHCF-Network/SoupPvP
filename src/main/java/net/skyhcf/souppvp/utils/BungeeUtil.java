package net.skyhcf.souppvp.utils;

import net.skyhcf.atmosphere.shared.AtmosphereShared;
import net.skyhcf.atmosphere.shared.server.Server;
import org.bukkit.entity.Player;

public class BungeeUtil {

    public static int getGlobalPlayerCount(Player player) {
        int global = 0;
        for(Server server : AtmosphereShared.getInstance().getServerManager().getServers()){
            global += server.getPlayerCount();
        }
        return global;
    }

    public static int getPlayerCount(String server) {
        return (AtmosphereShared.getInstance().getServerManager().getServer(server) == null ? 0 : AtmosphereShared.getInstance().getServerManager().getServer(server).getPlayerCount());
    }

    public static boolean isServerOnline(String server){
        for(Server servers : AtmosphereShared.getInstance().getServerManager().getServers()){
            if(servers.getName().equalsIgnoreCase(server)){
                return servers.isOnline();
            }
        }
        return false;
    }

}