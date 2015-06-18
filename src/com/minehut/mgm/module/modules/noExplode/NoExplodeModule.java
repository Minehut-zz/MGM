package com.minehut.mgm.module.modules.noExplode;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.game.coreModules.tntTracker.TntTracker;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/8/15.
 */
public class NoExplodeModule implements Module {
    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    public Region region;
    public TeamModule team;

    public NoExplodeModule(Region region, TeamModule teamModule) {
        this.region = region;
        this.team = teamModule;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            if (event.getEntity().hasMetadata("source")) {
                Player realDamager = Bukkit.getServer().getPlayer(TntTracker.getWhoPlaced(event.getEntity()));
                if(realDamager != null) {
                    if (TeamUtils.getTeamByPlayer(realDamager) == this.team) {
                        List<Block> toRemove = new ArrayList<>();
                        for (Block block : event.blockList()) {
                            if (this.region.insideRegion(block.getLocation())) {
                                toRemove.add(block);
                            }
                        }
                        if (toRemove != null) {
                            for (Block block : toRemove) {
                                event.blockList().remove(block);
                            }
                        }
                    }
                }
            }
        }
    }
}
