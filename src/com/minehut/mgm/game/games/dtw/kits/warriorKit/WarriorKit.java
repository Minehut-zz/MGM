package com.minehut.mgm.game.games.dtw.kits.warriorKit;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/14/15.
 */
public class WarriorKit extends Kit {

    public WarriorKit() {
        super("Warrior", Material.STONE_SWORD, 0);

        super.addItem(Material.STONE_SWORD);
        super.addItem(Material.BOW);
        super.addItem(Material.STONE_AXE);
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 32), 8);

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(Material.LEATHER_CHESTPLATE);
        super.addItem(Material.LEATHER_LEGGINGS);
        super.addItem(Material.LEATHER_BOOTS);
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {

    }
}
