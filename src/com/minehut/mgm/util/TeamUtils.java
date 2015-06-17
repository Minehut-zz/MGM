package com.minehut.mgm.util;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.coreModules.teamPicker.TeamPickerModule;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by luke on 6/1/15.
 */
public class TeamUtils {

    public static TeamModule getTeamWithFewestPlayers() {
        TeamModule result = getTeams().get(0);
        for (TeamModule teamModule : getTeams()) {
            if (teamModule.getSize() < result.getSize() && !teamModule.isSpectator()) {
                result = teamModule;
            }
        }
        return result;
    }

    public static TeamModule getSpectators() {
        for (TeamModule teamModule : getTeams()) {
            if (teamModule.isSpectator()) {
                return teamModule;
            }
        }
        F.log("Couldn't find spectator team.");
        return null;
    }

    public static TeamModule getTeamByName(String name) {
        if (name == null) return null;
        for (TeamModule team : getTeams()) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public static TeamModule getTeamById(String id) {
        for (TeamModule team : getTeams()) {
            if (team.getId().equalsIgnoreCase(id)) {
                return team;
            }
        }
        return null;
    }

    public static TeamModule getTeamByPlayer(Player player) {
        for (Module team : GameHandler.getGameHandler().getMatch().getGame().getModules(TeamModule.class)) {
            if (((TeamModule) team).contains(player)) {
                return (TeamModule) team;
            }
        }
        return null;
    }

    public static ArrayList<TeamModule> getTeams() {
        ArrayList<TeamModule> teams = new ArrayList<>();
        for (Module module : GameHandler.getGameHandler().getMatch().getGame().getModules(TeamModule.class)) {
            teams.add((TeamModule) module);
        }
        return teams;
    }

    public static ChatColor getTeamColorByPlayer(OfflinePlayer player) {
        if (player.isOnline()) {
            TeamModule team = getTeamByPlayer(player.getPlayer());
            if (team != null) return team.getColor();
            else return ChatColor.DARK_AQUA;
        } else return ChatColor.DARK_AQUA;
    }

    public static void setupSpectator(Player player) {
        PlayerUtils.resetPlayer(player);

        player.setAllowFlight(true);
        player.setFlying(true);

        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));

        player.getInventory().setItem(1, ItemStackFactory.createItem(Material.CHEST, C.yellow + C.bold + "/kit"));
        player.getInventory().setItem(3, ItemStackFactory.createItem(Material.LEATHER_CHESTPLATE, C.aqua + C.bold + "Team Picker"));
        player.getInventory().setItem(8, ItemStackFactory.createItem(Material.WATCH, C.red + C.bold + "/hub"));

        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            TeamModule team = TeamUtils.getTeamByPlayer(player1);

            if (team != null && !team.isSpectator()) {
                player1.hidePlayer(player);
            } else {
                player1.showPlayer(player);
            }
        }
    }

    public static boolean onSameTeam(Player p1, Player p2) {
        return getTeamByPlayer(p1) == getTeamByPlayer(p2);
    }

    public static boolean isSpectator(Player player) {
        if (getSpectators().contains(player)) {
            return true;
        }
        return false;
    }

    public static void setupMatchPlayer(Player player) {
        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            player1.showPlayer(player);
        }
    }

}
