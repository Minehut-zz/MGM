package com.minehut.mgm.module.modules.noModify;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by luke on 6/8/15.
 */
public class NoModifyModule implements Module {
    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    public Region region;

    public NoModifyModule(Region region) {
        this.region = region;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;

        if (this.region.insideRegion(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.isCancelled()) return;

        if (this.region.insideRegion(event.getBlock().getLocation())) {
            event.setCancelled(true);
            F.warning(event.getPlayer(), "You cannot modify this region");
        }
    }
}
