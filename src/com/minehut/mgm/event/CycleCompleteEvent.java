package com.minehut.mgm.event;

import com.minehut.mgm.match.Match;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 5/31/15.
 */
public class CycleCompleteEvent extends Event {
    private Match match;

    public CycleCompleteEvent(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
