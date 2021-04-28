package net.skyhcf.souppvp.event;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLaunchPadEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Getter private final Player player;
    @Getter private final Location location;

    public PlayerLaunchPadEvent(Player player, Location location){
        this.player = player;
        this.location = location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
