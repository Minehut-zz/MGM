package com.minehut.mgm.game.coreModules.spectator;

import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by luke on 6/8/15.
 */
public class SpectatorModule implements Module {

    public SpectatorModule() {
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onDamage(CustomDamageEvent event) {
        if (event.getDamagerPlayer() != null) {
            if (TeamUtils.isSpectator(event.getDamagerPlayer())) {
                event.setCancelled(true);
            }
        }

        if (event.getHurtPlayer() != null) {
            if (TeamUtils.isSpectator(event.getHurtPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (TeamUtils.isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (TeamUtils.isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (TeamUtils.isSpectator(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
