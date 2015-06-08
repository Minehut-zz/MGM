package com.minehut.mgm.game.coreModules.pregame;

import com.minehut.mgm.util.C;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.util.F;
import org.bukkit.Bukkit;

/**
 * Created by luke on 6/7/15.
 */
public class PregameModule implements Module {
    private int timerID;
    private int timeLeft;
    private boolean paused;

    public PregameModule() {
        this.timerID = countdown();
    }

    private int countdown() {
        this.timeLeft = 30;

        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!paused) {
                    if (timeLeft > 0) {

                        if (timeLeft <= 5) {
                            F.broadcast("The match will start in " + C.red + timeLeft + C.white + " seconds.");
                        } else if (timeLeft <= 60 && (timeLeft % 10 == 0)) {
                            F.broadcast("The match will start in " + C.red + timeLeft + C.white + " seconds.");
                        }

                    } else {
                        startGame();
                    }
                    timeLeft--;
                }

            }
        }, 0, 20L);
    }

    public void startGame() {
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(MGM.getInstance().getGameHandler().getMatch()));
        unload();
    }

    public boolean getPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void unload() {
        Bukkit.getServer().getScheduler().cancelTask(this.timerID);
    }
}
