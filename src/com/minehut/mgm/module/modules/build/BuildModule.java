package com.minehut.mgm.module.modules.build;

import com.minehut.mgm.module.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by luke on 6/7/15.
 */
public class BuildModule implements Module {
    private boolean allowBuild;

    public BuildModule(boolean allowBuild) {
        this.allowBuild = allowBuild;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!allowBuild) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!allowBuild) {
            event.setCancelled(true);
        }
    }
}
