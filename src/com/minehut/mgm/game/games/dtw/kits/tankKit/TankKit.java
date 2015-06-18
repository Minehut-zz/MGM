package com.minehut.mgm.game.games.dtw.kits.tankKit;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by luke on 6/14/15.
 */
public class TankKit extends Kit {

    public TankKit() {
        super("Tank", Material.DIAMOND_CHESTPLATE, 150);

        super.addItem(ItemStackFactory.createItem(Material.WOOD_SWORD, Enchantment.KNOCKBACK, 1));
        super.addItem(Material.WOOD_AXE);
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(ItemStackFactory.createItem(Material.DIAMOND_CHESTPLATE, Enchantment.PROTECTION_PROJECTILE, 2));
        super.addItem(ItemStackFactory.createItem(Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_PROJECTILE, 2));
        super.addItem(Material.LEATHER_BOOTS);
    }

    @Override
    public void onApply(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1));
    }

    @Override
    public void extraUnload() {

    }
}
