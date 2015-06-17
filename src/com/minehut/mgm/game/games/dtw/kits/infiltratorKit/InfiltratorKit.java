package com.minehut.mgm.game.games.dtw.kits.infiltratorKit;

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
public class InfiltratorKit extends Kit {

    public InfiltratorKit() {
        super("Infiltrator", Material.LADDER, 450);

        super.addItem(ItemStackFactory.createItem(Material.GOLD_AXE, Enchantment.DIG_SPEED, 2));
        super.addItem(Material.BOW);
        super.addItem(ItemStackFactory.createItem(Material.LADDER, 32));
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 64), 8);

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(Material.LEATHER_LEGGINGS);
        super.addItem(ItemStackFactory.createItem(Material.CHAINMAIL_BOOTS, Enchantment.PROTECTION_FALL, 2));
    }

    @Override
    public void onApply(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
    }

    @Override
    public void extraUnload() {

    }
}
