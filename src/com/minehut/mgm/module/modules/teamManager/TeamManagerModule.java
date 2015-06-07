package com.minehut.mgm.module.modules.teamManager;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 6/1/15.
 */
public class TeamManagerModule implements Module {

    private final Match match;

    protected TeamManagerModule(Match match) {
        this.match = match;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerUtils.resetPlayer(player);
        TeamUtils.getTeamById("blue").add(player);
        TeamUtils.getTeamByPlayer(player).getKit().apply(player);
        event.getPlayer().getInventory().setItem(0, new ItemStack(Material.COMPASS));
        player.sendMessage("Added to team: " + TeamUtils.getTeamByPlayer(player).getName());
    }

    @EventHandler
    public void onCycleComplete(CycleCompleteEvent event) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            TeamUtils.getTeamById("spectators").add(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChangeTeam(PlayerChangeTeamEvent event) {
        if (event.getNewTeam() != null && !event.getNewTeam().isSpectator() && GameHandler.getGameHandler().getMatch().isRunning()) {
            Bukkit.dispatchCommand(event.getPlayer(), "match");
        }
    }

    private void removePlayer(Player player) {
        for (Module module : match.getGame().getModules(TeamModule.class)) {
            TeamModule team = (TeamModule) module;
            if (team.contains(player)) {
                team.remove(player);
            }
        }
    }

}
