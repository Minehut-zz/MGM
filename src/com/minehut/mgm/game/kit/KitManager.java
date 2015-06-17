package com.minehut.mgm.game.kit;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.items.EnchantGlow;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.commons.common.sound.S;
import com.minehut.core.Core;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.kit.kitPlayer.GamePlayer;
import com.minehut.mgm.game.kit.kitPlayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luke on 6/14/15.
 */
public class KitManager implements Listener {
    private GamePlayerManager gamePlayerManager;
    public static String kitMenuName = C.underline + "Kit Menu";


    public KitManager() {
        this.gamePlayerManager = new GamePlayerManager(this);

        MGM.getInstance().getServer().getPluginManager().registerEvents(this, MGM.getInstance());
    }

    public Kit getDefaultKit() {
        for (Kit kit : GameHandler.getHandler().getMatch().getGame().getKits()) {
            if (kit.getPrice() == 0) {
                return kit;
            }
        }
        return null;
    }

    public List<Kit> getFreeKits() {
        List<Kit> freeKits = new ArrayList<>();
        for (Kit kit : this.getKits()) {
            if (kit.getPrice() == 0) {
                freeKits.add(kit);
            }
        }
        return freeKits;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(kitMenuName)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            /* Air Click */
            if (event.getCurrentItem().getType() == null) {
                S.click(player);
                return;
            }

            /* Kit Click */
            Kit kit = getKitFromMaterial(event.getCurrentItem().getType());
            if (kit != null) {
                GamePlayer gamePlayer = this.gamePlayerManager.getGamePlayer(player);

                /* Owns Kit */
                if (gamePlayer.hasKit(kit)) {
                    gamePlayer.setSelectedKit(kit);

                    player.closeInventory();
                    S.playSound(player, Sound.ANVIL_LAND);

                    player.sendMessage("");
                    F.message(player, "Kits", "You have equipped " + C.red + C.bold + kit.getName());
                    player.sendMessage("");
                    return;
                }

                /* Doesn't own kit */
                else {
                    PlayerInfo playerInfo = Core.getInstance().getPlayerInfo(player);
                    if (playerInfo.hasEnoughForCreditsPurchase(kit.getPrice())) {

                        if(playerInfo.getRank().has(null, kit.getRank(), false)) {

                            gamePlayer.addOwnedKit(kit);
                            gamePlayer.setSelectedKit(kit);

                            player.closeInventory();
                            F.broadcast("Kits", C.aqua + player.getName() + C.yellow + " has unlocked " + C.green + C.bold + kit.getName());
                            S.playSound(player, Sound.LEVEL_UP);

                            Bukkit.getServer().getScheduler().runTaskAsynchronously(MGM.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    Core.getInstance().addCredits(player, -kit.getPrice(), "Purchase " + C.yellow + C.bold + kit.getName());
                                    gamePlayerManager.saveKits(gamePlayer);
                                }
                            });

                            return;

                            /* Doesn't have required rank */
                        } else {
                            S.playSound(player, Sound.GLASS);
                            F.message(player, "Kits", "This kit requires rank " + kit.getRank());

                            return;
                        }
                    }
                    /* Not enough credits */
                    else {
                        S.playSound(player, Sound.GLASS);
                        F.message(player, "Kits", "You do not have enough credits!");

                        return;
                    }
                }
            }
        }
    }

    public Kit getKitFromMaterial(Material material) {
        for (Kit kit : this.getKits()) {
            if (kit.getIcon() == material) {
                return kit;
            }
        }
        return null;
    }

    public void openKitMenu(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 27, kitMenuName);

        int i = 1;
        for (Kit kit : this.getKits()) {
            GamePlayer gamePlayer = this.gamePlayerManager.getGamePlayer(player);
            boolean owned = gamePlayer.hasKit(kit);

            String ownedMessage;
            if (owned) {
                ownedMessage = C.green + C.bold + "OWNED";
            } else {
                ownedMessage = C.red + C.bold + "UNOWNED";
            }

            String rankMessage;
            if(kit.getRank() != Rank.regular) {
                rankMessage = C.yellow + "Requires Rank " + kit.getRank().getTag();
            } else {
                rankMessage = "";
            }

            boolean equipped = false;
            if (owned && gamePlayer.getSelectedKit() == kit) {
                equipped = true;
            }

            String equippedMessage = "";
            if (equipped) {
                equippedMessage = C.green + C.bold + " [SELECTED]";
            }

            ItemStack kitItem = ItemStackFactory.createItem(
                    kit.getIcon(),
                    C.yellow + C.bold + kit.getName() + equippedMessage,
                    Arrays.asList(
                            "",
                            ownedMessage,
                            C.gray + "Price: " + C.yellow + kit.getPrice(),
                            rankMessage));
            if (equipped) {
                EnchantGlow.addGlow(kitItem);
            }

            inventory.setItem(i, kitItem);

            i += 2;

            if (i == 9) {
                i += 2;
            } else if (i == 17) {
                i += 4;
            }
        }

        player.openInventory(inventory);
        S.click(player);
    }


    public GamePlayerManager getGamePlayerManager() {
        return gamePlayerManager;
    }

    public List<Kit> getKits() {
        return GameHandler.getGameHandler().getMatch().getGame().getKits();
    }

}
