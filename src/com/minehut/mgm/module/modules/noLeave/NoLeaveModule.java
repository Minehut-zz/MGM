package com.minehut.mgm.module.modules.noLeave;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by luke on 6/7/15.
 */
public class NoLeaveModule implements Module {
    private Region region;
    private int runnableID;

    public NoLeaveModule(Region region) {
        this.region = region;
        this.runnableID = startLocationCheck();
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
        Bukkit.getServer().getScheduler().cancelTask(this.runnableID);
    }

    private int startLocationCheck() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (GameHandler.getHandler().getMatch().isRunning()) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (!TeamUtils.getSpectators().contains(player)) {
                            if (!region.insideRegionAllowBelow(player.getLocation())) {
                                F.warning(player, "Do not leave the battlefield!");
                                player.teleport(TeamUtils.getTeamByPlayer(player).getRandomSpawn());
                            }
                        }
                    }
                }
            }
        }, 20L, 20L * 2);
    }
}
