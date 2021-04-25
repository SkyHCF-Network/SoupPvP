package net.skyhcf.souppvp.event;

import lombok.Getter;
import net.skyhcf.souppvp.profile.KitProfile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProfileSaveEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Getter private final KitProfile kitProfile;

    public ProfileSaveEvent(KitProfile kitProfile){
        this.kitProfile = kitProfile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
