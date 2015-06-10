package com.minehut.mgm.module.modules.tntProtection;

import com.minehut.mgm.MGM;
import com.minehut.mgm.module.Module;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/7/15.
 */
public class TntProtectionModule implements Module {

    public TntProtectionModule() {
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            List<Block> toRemove = new ArrayList<>();
            for (Block block : event.blockList()) {
                toRemove.add(block);
            }
            for (Block block : toRemove) {
                event.blockList().remove(block);
            }
        }
    }
}
