package com.minehut.mgm.game.kit;

import com.minehut.commons.common.items.ArmorType;
import com.minehut.mgm.module.Module;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luke on 6/12/15.
 */
public abstract class Kit implements Module {
    public HashMap<ItemStack, Integer> items;
    public String name;
    public Material icon;
    public int price;

    public double maxHealth;

    public Kit(String name, Material icon, int price) {
        this.name = name;
        this.icon = icon;
        this.price = price;

        this.items = new HashMap<>();
    }

    public void addItem(ItemStack itemStack) {
        if (this.items.isEmpty()) {
            this.items.put(itemStack, 0);
            return;
        }

        for (int i = 0; i < 35; i++) {
            if (!this.items.containsValue(i)) {
                this.items.put(itemStack, i);
            }
        }
    }

    public void setItem(ItemStack itemStack, int slot) {
        this.items.put(itemStack, slot);
    }

    public void apply(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(this.maxHealth);
        player.setHealth(player.getMaxHealth());
        for (ItemStack item : this.items.keySet()) {
            if (ArmorType.getArmorType(item) == ArmorType.helmet) {
                player.getInventory().setHelmet(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.chestplate) {
                player.getInventory().setChestplate(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.leggings) {
                player.getInventory().setLeggings(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.boots) {
                player.getInventory().setBoots(item);
            } else {
                player.getInventory().addItem(item);
            }
        }

        this.onApply(player);
    }

    public abstract void onApply(Player player);
}
