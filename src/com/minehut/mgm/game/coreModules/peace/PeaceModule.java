package com.minehut.mgm.game.coreModules.peace;

import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 6/7/15.
 */
public class PeaceModule implements Module {
    private boolean peace;

    public PeaceModule() {
        this.peace = true;
    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent event) {
        if (this.peace) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        this.peace = false;
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        this.peace = true;
    }

    public boolean isPeace() {
        return peace;
    }

    public void setPeace(boolean peace) {
        this.peace = peace;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
