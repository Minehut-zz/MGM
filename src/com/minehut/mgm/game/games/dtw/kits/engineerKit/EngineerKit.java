package com.minehut.mgm.game.games.dtw.kits.engineerKit;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.util.C;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 6/14/15.
 */
public class EngineerKit extends Kit {
    private ItemStack axe;

    public EngineerKit() {
        super("Engineer", Material.WOOD_STAIRS, 250);

        super.addItem(Material.WOOD_SWORD);
        super.addItem(Material.BOW);


        this.axe = ItemStackFactory.createItem(Material.IRON_AXE, C.gray + "(Deals No Damage)");
        super.addItem(this.axe);

        super.addItem(ItemStackFactory.createItem(Material.WOOD, 64));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 64));

        super.addItem(Material.WOOD_STAIRS, 64);
        super.addItem(Material.WOOD_STEP, 64);
        super.addItem(Material.LADDER, 64);

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 64), 8);

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(Material.CHAINMAIL_CHESTPLATE);
        super.addItem(Material.LEATHER_LEGGINGS);
        super.addItem(Material.LEATHER_BOOTS);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        for(HumanEntity he:event.getViewers()) {
            if (he instanceof Player) {
                Player player = (Player) he;
                if (!super.usingThisKit(player)) {
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                    F.warning(player, "Only " + C.yellow + C.bold + "ENGINEERS" + C.gray + " can craft items");
                }
            }
        }
    }

    @EventHandler
    public void onDamage(CustomDamageEvent event) {
        if (event.getDamagerPlayer() != null && event.getHurtPlayer() != null) {
            if (event.getProjectile() == null) {
                if (usingThisKit(event.getDamagerPlayer())) {
                    if (event.getDamagerPlayer().getItemInHand().getItemMeta() != null) {
                        if (event.getDamagerPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
                            if (event.getDamagerPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(this.axe.getItemMeta().getDisplayName())) {
                                event.setDamage(0);
                                F.log("cancelled damage");
                            }
                        }
                    }
                }
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
