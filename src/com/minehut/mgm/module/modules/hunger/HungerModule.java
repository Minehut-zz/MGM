package com.minehut.mgm.module.modules.hunger;

import com.minehut.mgm.module.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by luke on 6/7/15.
 */
public class HungerModule implements Module {
    private final boolean hungerEnabled;

    public HungerModule(boolean hungerEnabled) {
        this.hungerEnabled = hungerEnabled;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onHungerDepletion(FoodLevelChangeEvent event) {
        if (!hungerEnabled) {
            event.setCancelled(true);
        }
    }
}
