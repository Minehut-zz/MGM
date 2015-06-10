package com.minehut.mgm.game.coreModules.teamManager;

import com.minehut.commons.common.chat.C;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        TeamModule spectators = TeamUtils.getSpectators();
        spectators.add(event.getPlayer());
        event.getPlayer().teleport(spectators.getRandomSpawn());

        TeamUtils.setupSpectator(event.getPlayer());

        if (GameHandler.getHandler().getMatch().isRunning()) {
            event.getPlayer().sendMessage("Type " + C.aqua + "/join " + C.white + "to join the match");
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

    @EventHandler
    public void onPlayerChangeTeam(PlayerChangeTeamEvent event) {
        if (event.getNewTeam() != null && !event.getNewTeam().isSpectator() && GameHandler.getGameHandler().getMatch().isRunning()) {
            Bukkit.dispatchCommand(event.getPlayer(), "match");
        }
    }

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
