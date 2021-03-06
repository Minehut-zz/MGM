package com.minehut.mgm.game.coreModules.teamManager;

import com.minehut.commons.common.chat.C;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by luke on 6/1/15.
 */
public class TeamManagerModule implements Module {

    public TeamManagerModule() {}

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!GameHandler.getHandler().getMatch().isRunning()) {
            TeamModule spectators = TeamUtils.getSpectators();
            spectators.add(event.getPlayer());
            event.getPlayer().teleport(spectators.getRandomSpawn());

            TeamUtils.setupSpectator(event.getPlayer());
        } else {
            TeamModule currentTeam = TeamUtils.getTeamByPlayer(event.getPlayer());

            if(currentTeam == null || currentTeam.isSpectator()) {
                TeamModule team = TeamUtils.getTeamWithFewestPlayers();
                team.add(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onTeamChange(PlayerChangeTeamEvent event) {
        if (GameHandler.getHandler().getMatch().isRunning()) {
            Player player = event.getPlayer();
            PlayerUtils.resetPlayer(player);
            player.teleport(event.getNewTeam().getRandomSpawn());

            if (!event.getNewTeam().isSpectator()) {
                TeamUtils.setupMatchPlayer(player);
            } else {
                TeamUtils.setupSpectator(player);
            }
        }
    }

    @EventHandler
    public void onCycleComplete(CycleCompleteEvent event) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            TeamModule spectators = TeamUtils.getSpectators();
            spectators.add(player);
            player.teleport(spectators.getRandomSpawn());

            TeamUtils.setupSpectator(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        removePlayer(event.getPlayer());
    }

//    @EventHandler
//    public void onPlayerChangeTeam(PlayerChangeTeamEvent event) {
//        if (event.getNewTeam() != null && !event.getNewTeam().isSpectator() && GameHandler.getGameHandler().getMatch().isRunning()) {
//            Bukkit.dispatchCommand(event.getPlayer(), "match");
//        }
//    }

    @EventHandler
    public void onFriendlyFire(CustomDamageEvent event) {
        if (event.getDamagerPlayer() != null && event.getHurtPlayer() != null) {

            // Allow self-hit
            if(event.getDamagerPlayer() == event.getHurtPlayer()) return;

            TeamModule damagerTeam = TeamUtils.getTeamByPlayer(event.getDamagerPlayer());
            TeamModule hurtTeam = TeamUtils.getTeamByPlayer(event.getHurtPlayer());

            if (damagerTeam == hurtTeam) {
                event.setCancelled(true);
            }
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
