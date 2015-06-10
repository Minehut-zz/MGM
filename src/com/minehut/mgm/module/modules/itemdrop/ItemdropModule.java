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
    private boolean allowAll;

    public ItemDropModule(ArrayList<ItemDropInfo> allowedDrops) {
        this.allowedDrops = allowedDrops;
        this.allowAll = false;
    }

    public ItemDropModule(boolean allowAll) {
        this.allowAll = allowAll;
        this.allowedDrops = new ArrayList<>();
    }

    @EventHandler
    public void onCustomDeath(CustomDeathEvent event) {
        if (event.getDeadPlayer() != null) {
            for (ItemStack item : event.getDeadPlayer().getInventory().getContents()) {
                if (item != null && item.getType() != null && allowDrop(item)) {
                    dropDeadPlayerItem(item, event.getDeadPlayer());
                }
            }
        }
    }

    public boolean allowDrop(ItemStack itemStack) {
        if(allowAll) return true;

        for (ItemDropInfo itemDropInfo : this.allowedDrops) {
            if (itemDropInfo.allowDrop(itemStack)) {
               return true;
            }
        }

        return false;
    }

    public void dropDeadPlayerItem(ItemStack itemStack, Player player) {
        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if(allowAll) return;

        boolean allow = false;
        for (ItemDropInfo itemDropInfo : this.allowedDrops) {
            if (itemDropInfo.allowDrop(event.getEntity().getItemStack())) {
                allow = true;
            }
        }

        if (!allow) {
            event.setCancelled(true);
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }


}
