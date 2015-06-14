package com.minehut.mgm.game.coreModules.postgame;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.pregame.PregameModule;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.util.C;
import org.bukkit.Bukkit;

/**
 * Created by luke on 6/7/15.
 */
public class PostgameModule implements Module {
    private int timerID;
    private int timeLeft;
    private boolean paused;

    public PostgameModule() {
        this.timerID = -1;
    }

    public void startCycleCountdown() {
        /* Clear pregame countdown if exists */
        GameHandler.getHandler().getMatch().getGame().getModule(PregameModule.class).unload();

        this.timeLeft = 30;

        this.timerID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!paused) {
                    if (timeLeft > 0) {

                        if (timeLeft <= 5) {
                            F.broadcast("Cycling to " + C.aqua + MGM.getInstance().getGameHandler().getRotation().getNext().getName() + C.white + " in " + C.red + timeLeft + C.white + " seconds.");
                        } else if (timeLeft <= 60 && (timeLeft % 10 == 0)) {
                            F.broadcast("Cycling to " + C.aqua + MGM.getInstance().getGameHandler().getRotation().getNext().getName() + C.white + " in " + C.red + timeLeft + C.white + " seconds.");
                        }

                    } else {
                        GameHandler.getHandler().cycleAndMakeMatch();
                        unload();
                    }

                    timeLeft--;
                }
            }
        }, 0, 20L);
    }

    @Override
    public void unload() {
        if(timeLeft != -1) {
            Bukkit.getServer().getScheduler().cancelTask(this.timerID);
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
