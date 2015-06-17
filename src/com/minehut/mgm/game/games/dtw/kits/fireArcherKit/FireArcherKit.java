package com.minehut.mgm.game.games.dtw.kits.fireArcherKit;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.util.C;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 6/14/15.
 */
public class FireArcherKit extends Kit {

    public FireArcherKit() {
        super("Fire Archer", Material.BOW, 350);

        ItemStack bow = ItemStackFactory.createItem(Material.BOW, C.red + C.bold + "Fire Bow");
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        super.addItem(bow);

        super.addItem(Material.WOOD_SWORD);

        super.addItem(Material.WOOD_AXE);
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 1), 8);

        super.addItem(Material.CHAINMAIL_CHESTPLATE);
        super.addItem(Material.LEATHER_CHESTPLATE);
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {

    }
}
