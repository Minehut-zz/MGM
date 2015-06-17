package com.minehut.mgm.game.games.dtw.kits.grenadierKit;

import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.core.player.Rank;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.util.C;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/14/15.
 */
public class GrenadierKit extends Kit {
    private ItemStack tnt;

    public GrenadierKit() {
        super("Grenadier", Material.TNT, 4000, Rank.Mega);

        super.addItem(Material.STONE_SWORD);
        super.addItem(Material.BOW);
        super.addItem(Material.WOOD_AXE);

        this.tnt = ItemStackFactory.createItem(Material.TNT, C.red + C.bold + "Grenade");
        tnt.setAmount(2);
        super.addItem(tnt);

        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 64), 8);

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(Material.GOLD_CHESTPLATE);
        super.addItem(Material.LEATHER_LEGGINGS);
        super.addItem(Material.LEATHER_BOOTS);
    }

    @EventHandler
    public void onKill(CustomDeathEvent event) {
        if (event.getKillerPlayer() != null && event.getDeadPlayer() != null) {
            if (event.getKillerPlayer() != event.getDeadPlayer()) {
                if (super.usingThisKit(event.getKillerPlayer())) {
                    ItemStack singleTNT = this.tnt = ItemStackFactory.createItem(Material.TNT, C.red + C.bold + "Grenade");

                    event.getKillerPlayer().getInventory().addItem(singleTNT);
                    event.getKillerPlayer().updateInventory();
                    event.getKillerPlayer().playSound(event.getKillerPlayer().getEyeLocation(), Sound.ITEM_PICKUP, 10, 10);
                }
            }
        }
    }

    @EventHandler
    public void onGrenadeThrow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand().getType() != null) {
            if(player.getItemInHand().getType() == Material.TNT) {
                if(super.usingThisKit(player)) {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
                        tnt.setVelocity(player.getLocation().getDirection().multiply(1.15));

                        tnt.setMetadata("source", new FixedMetadataValue(MGM.getInstance(), player.getUniqueId()));

                        ItemStack updatedItem = player.getItemInHand();
                        updatedItem.setAmount(player.getItemInHand().getAmount() - 1);
                        player.setItemInHand(updatedItem);
                        player.updateInventory();

                        player.playSound(player.getEyeLocation(), Sound.ITEM_PICKUP, 10, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != null) {
            if (event.getBlock().getType() == Material.TNT) {
                event.setCancelled(true);
                event.getPlayer().updateInventory();
            }
        }
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {

    }
}
