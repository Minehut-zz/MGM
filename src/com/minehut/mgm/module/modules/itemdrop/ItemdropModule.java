package com.minehut.mgm.module.modules.itemdrop;

import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class ItemDropModule implements Module {
    private ArrayList<ItemDropInfo> allowedDrops;
    private DropMode dropMode;

    public ItemDropModule(ArrayList<ItemDropInfo> allowedDrops, DropMode dropMode) {
        this.allowedDrops = allowedDrops;
        this.dropMode = dropMode;
    }

    public enum DropMode {
        DENY,
        ALLOW,
        ALL;
    }

    @EventHandler
    public void onCustomDeath(CustomDeathEvent event) {
        if (event.getDeadPlayer() != null) {
            for (ItemStack item : event.getDeadPlayer().getInventory().getContents()) {
                if (item != null && item.getType() != null && canDrop(item)) {
                    dropDeadPlayerItem(item, event.getDeadPlayer());
                }
            }
        }
    }

    public boolean canDrop(ItemStack itemStack) {
        if(this.dropMode == DropMode.ALL) return true;

        for (ItemDropInfo itemDropInfo : this.allowedDrops) {
            if (itemDropInfo.isMatch(itemStack)) {
                if (this.dropMode == DropMode.ALLOW) {
                    return true;
                }
                else if (this.dropMode == DropMode.DENY) {
                    return false;
                }
            }
        }

        return false;
    }

    public void dropDeadPlayerItem(ItemStack itemStack, Player player) {
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }


}
