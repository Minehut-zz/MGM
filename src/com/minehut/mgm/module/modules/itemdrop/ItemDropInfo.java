package com.minehut.mgm.module.modules.itemdrop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 6/8/15.
 */
public class ItemDropInfo {
    private Material material;

    public ItemDropInfo(Material material) {
        this.material = material;
    }

    public boolean allowDrop(ItemStack itemStack) {
        if (itemStack.getType() == this.material) {
            return true;
        }
        return false;
    }
}
