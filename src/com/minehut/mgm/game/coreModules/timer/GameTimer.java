package com.minehut.mgm.game.coreModules.timer;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.module.Module;
import org.bukkit.event.EventHandler;

/**
 * Created by luke on 6/8/15.
 */
public class GameTimer implements Module {
    private long startTime;
    private double endTime;

    public GameTimer() {
        this.endTime = 0;
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        this.startTime = System.currentTimeMillis();
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        this.endTime = ((double) System.currentTimeMillis() - (((GameTimer) GameHandler.getGameHandler().getMatch().getGame().getModule(GameTimer.class)).getTime())) / 1000.0;
    }

    public static double getTimeInSeconds() {
        Match match = GameHandler.getGameHandler().getMatch();
        if (match.isRunning()) {
            return ((double) System.currentTimeMillis() - (((GameTimer) GameHandler.getGameHandler().getMatch().getGame().getModule(GameTimer.class)).getTime())) / 1000.0;
        }
        if (match.getState().equals(MatchState.ENDED) || match.getState().equals(MatchState.CYCLING)) {
            return ((GameTimer) GameHandler.getGameHandler().getMatch().getGame().getModule(GameTimer.class)).getEndTime();
        }
        return 0;
    }

    public long getTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }


    @Override
    public void unload() {

    }
}
