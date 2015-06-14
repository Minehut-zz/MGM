package com.minehut.mgm.module.modules.grenade;

import com.minehut.commons.common.sound.S;
import com.minehut.mgm.MGM;
import com.minehut.mgm.module.Module;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by luke on 6/7/15.
 */
public class GrenadeModule implements Module {

    public GrenadeModule() {
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onGrenadeThrow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand().getType() != null) {
            if(player.getItemInHand().getType() == Material.TNT) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
                    tnt.setVelocity(player.getLocation().getDirection().multiply(1.15));

                    tnt.setMetadata("source", new FixedMetadataValue(MGM.getInstance(), player.getUniqueId()));

                    ItemStack updatedItem = player.getItemInHand();
                    updatedItem.setAmount(player.getItemInHand().getAmount() - 1);
                    player.setItemInHand(updatedItem);
                    player.updateInventory();

                    player.playSound(player.getEyeLocation(), Sound.ITEM_PICKUP, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != null) {
            if (event.getBlock().getType() == Material.TNT) {
                event.setCancelled(true);
                event.getPlayer().updateInventory();
            }
        }
    }
}
