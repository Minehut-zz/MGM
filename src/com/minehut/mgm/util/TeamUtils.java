package com.minehut.mgm.util;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by luke on 6/1/15.
 */
public class TeamUtils {

    public static TeamModule getTeamWithFewestPlayers(Match match) {
        TeamModule result = getTeams().get(0);
        for (TeamModule teamModule : getTeams()) {
            if (teamModule.getSize() > result.getSize()) {
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
            F.log("while searching for team found team: " + ((TeamModule) module).getName());
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

}
