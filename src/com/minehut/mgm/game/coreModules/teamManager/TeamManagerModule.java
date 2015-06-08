package com.minehut.mgm.game.coreModules.teamManager;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.mapperModules.team.TeamModule;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by luke on 6/1/15.
 */
public class TeamManagerModule implements Module {

    public TeamManagerModule() {}

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerUtils.resetPlayer(player);

        TeamModule spectators = TeamUtils.getSpectators();
        spectators.add(player);
        player.teleport(spectators.getRandomSpawn());

        event.getPlayer().getInventory().setItem(0, new ItemStack(Material.COMPASS));
    }

    @EventHandler
    public void onCycleComplete(CycleCompleteEvent event) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            TeamModule spectators = TeamUtils.getSpectators();
            spectators.add(player);
            player.teleport(spectators.getRandomSpawn());

            player.getInventory().setItem(0, new ItemStack(Material.COMPASS));
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
        for (Module module : MGM.getInstance().getGameHandler().getMatch().getGame().getModules(TeamModule.class)) {
            TeamModule team = (TeamModule) module;
            if (team.contains(player)) {
                team.remove(player);
            }
        }
    }

}
