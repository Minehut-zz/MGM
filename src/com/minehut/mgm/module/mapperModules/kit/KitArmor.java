package com.minehut.mgm.module.mapperModules.kit;

import com.minehut.mgm.util.ArmorType;
import org.bukkit.inventory.ItemStack;

class KitArmor {

    private ItemStack item;
    private ArmorType type;

    protected KitArmor(ItemStack item, ArmorType type) {
        this.item = item;
        this.type = type;
    }

    public ItemStack getItem() {
        return item;
    }

    public ArmorType getType() {
        return type;
    }
}
