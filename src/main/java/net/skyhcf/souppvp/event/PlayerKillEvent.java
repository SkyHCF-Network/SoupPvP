package net.skyhcf.souppvp.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    @Getter private final Player killer;
    @Getter private final Player victim;

    public PlayerKillEvent(Player killer, Player victim){
        this.killer = killer;
        this.victim = victim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }


}
