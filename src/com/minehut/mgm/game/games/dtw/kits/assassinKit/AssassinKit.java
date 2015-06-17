package com.minehut.mgm.game.games.dtw.kits.assassinKit;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/14/15.
 */
public class AssassinKit extends Kit {

    public AssassinKit() {
        super("Assassin", Material.DIAMOND_SWORD, 800);

        super.addItem(Material.DIAMOND_SWORD);
        super.addItem(Material.BOW);
        super.addItem(Material.GOLD_AXE);
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 32), 8);

        super.addItem(Material.LEATHER_CHESTPLATE);
        super.addItem(ItemStackFactory.createItem(Material.CHAINMAIL_BOOTS, Enchantment.PROTECTION_FALL, 2));
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {

    }
}
