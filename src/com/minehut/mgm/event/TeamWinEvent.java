package com.minehut.mgm.event;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.modules.team.TeamModule;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 5/31/15.
 */
public class TeamWinEvent extends Event {
    private Match match;
    private TeamModule winningTeam;

    public TeamWinEvent(Match match, TeamModule winningTeam) {
        this.match = match;
        this.winningTeam = winningTeam;
    }

    public TeamModule getWinningTeam() {
        return winningTeam;
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
