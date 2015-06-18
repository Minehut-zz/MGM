package com.minehut.mgm.game.games.tnt.kits;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/14/15.
 */
public class BlasterKit extends Kit {

    public BlasterKit() {
        super("Blaster", Material.TNT, 0);

        super.addItem(Material.TNT, 64);
        super.addItem(Material.STONE_BUTTON, 64);
        super.addItem(Material.REDSTONE_TORCH_ON, 64);
        super.addItem(Material.WATER_BUCKET, 64);
        super.addItem(Material.REDSTONE, 64);
        super.addItem(Material.REDSTONE, 64);
        super.addItem(Material.LEVER, 64);
        super.addItem(ItemStackFactory.createItem(Material.COBBLESTONE, 64));
        super.addItem(ItemStackFactory.createItem(Material.COBBLESTONE, 64));
        super.addItem(ItemStackFactory.createItem(Material.COBBLESTONE, 64));
        super.addItem(ItemStackFactory.createItem(Material.COBBLESTONE, 64));
        super.addItem(Material.TNT, 64);
        super.addItem(Material.TNT, 64);
        super.addItem(Material.TNT, 64);
        super.addItem(Material.TNT, 64);
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {

    }
}
