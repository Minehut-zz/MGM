package com.minehut.mgm.event;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.modules.destroyable.Destroyable;
import com.minehut.mgm.module.modules.team.TeamModule;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 5/31/15.
 */
public class DestroyableBreakEvent extends Event {
    private Destroyable destroyable;
    private Player breaker;

    public DestroyableBreakEvent(Destroyable destroyable, Player breaker) {
        this.destroyable = destroyable;
        this.breaker = breaker;
    }

    public Destroyable getDestroyable() {
        return destroyable;
    }

    public Player getBreaker() {
        return breaker;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
