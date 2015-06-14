package com.minehut.mgm.game.coreModules.teamPicker;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.items.EnchantGlow;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.core.Core;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.MiscUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by luke on 6/10/15.
 */
public class TeamPickerModule implements Module {


    public Inventory getTeamPicker() {
        int size = ((GameHandler.getGameHandler().getMatch().getGame().getModules(TeamModule.class).size() / 9) + 1) * 9;
        Inventory picker = Bukkit.createInventory(null, size, C.aqua + C.bold + "Team Picker");
        int item = 0;

        int maxPlayers = 0;
        int totalPlayers = 0;
        for (TeamModule team : GameHandler.getGameHandler().getMatch().getGame().getModules(TeamModule.class)) {
            if (!team.isSpectator()) {
                maxPlayers += team.getMaxSize();
                totalPlayers += team.getSize();
            }
        }
        ItemStack autoJoin = ItemStackFactory.createItem(Material.NETHER_STAR, C.gray + C.bold + "AUTO JOIN");
        picker.setItem(item, autoJoin);

        item += 2;
        for (TeamModule team : GameHandler.getGameHandler().getMatch().getGame().getModules(TeamModule.class)) {
            if (!team.isSpectator()) {
                ItemStack itemStack = ItemStackFactory.createColoredArmor(Material.LEATHER_CHESTPLATE,
                        team.getColor() + C.bold + team.getName() + C.dgray + " (" + C.yellow + team.getSize() + C.gray + "/" + C.yellow + team.getMaxSize() + C.dgray + ")",
                        MiscUtils.convertChatColorToColor(team.getColor()));
                picker.setItem(item, itemStack);
                item++;
            }
        }
        item = size;
        return picker;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (item != null) {
            if (TeamUtils.getTeamByPlayer(player).isSpectator() || !GameHandler.getGameHandler().getMatch().isRunning()) {
                if (event.getInventory().getName().equals(C.aqua + C.bold + "Team Picker")) {
                    event.setCancelled(true);

                    if (item.getType().equals(Material.NETHER_STAR)) {
                        player.closeInventory();
                        TeamUtils.getTeamWithFewestPlayers().add(player);
                    }

                    else if (item.getType().equals(Material.LEATHER_CHESTPLATE)) {
                        if (Core.getInstance().getPlayerInfo(player).getRank().has(player, Rank.Mega, false)) {
                            if (item.hasItemMeta()) {
                                if (item.getItemMeta().hasDisplayName()) {

                                    String[] parts = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" \\(");
                                    String name = parts[0];

                                    TeamModule selected = TeamUtils.getTeamByName(name);
                                    if (selected != null) {
                                        event.setCancelled(true);
                                        player.closeInventory();

                                        selected.add(player);
                                    }
                                }
                            }
                        }
                    } else {
                        F.warning(player, "Only " + C.purple + C.bold + "DONATORS " + C.gray + " have access pick teams.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (TeamUtils.getTeamByPlayer(event.getPlayer()).isSpectator() || !GameHandler.getGameHandler().getMatch().isRunning()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getPlayer().getItemInHand() != null) {
                    if (event.getPlayer().getItemInHand().getType().equals(Material.LEATHER_CHESTPLATE)) {
                        event.setCancelled(true);
                        event.getPlayer().getInventory().setChestplate(null);
                        if (event.getPlayer().getItemInHand().hasItemMeta()) {
                            if (event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(C.aqua + C.bold + "Team Picker")) {
                                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CLICK, 1, 2);
                                    event.getPlayer().openInventory(getTeamPicker());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
