package com.minehut.mgm.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Created by luke on 6/1/15.
 */
public class PlayerUtils {
    public static void resetPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        for (PotionEffect effect : player.getActivePotionEffects()) {
            try {
                player.removePotionEffect(effect.getType());
            } catch (NullPointerException e) {
            }
        }
        player.setTotalExperience(0);
        player.setExp(0);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
    }
}
