package com.minehut.mgm.game.coreModules.wrapper;

import com.minehut.commons.common.fireworks.Fireworks;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.event.TeamWinEvent;
import com.minehut.mgm.game.coreModules.peace.PeaceModule;
import com.minehut.mgm.game.coreModules.postgame.PostgameModule;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by luke on 6/7/15.
 */
public class WrapperModule implements Module {

    public WrapperModule() {

    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        GameHandler.getHandler().getMatch().setState(MatchState.PLAYING);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            PlayerUtils.resetPlayer(player);
            player.setGameMode(GameMode.SURVIVAL);

            TeamModule team = TeamUtils.getTeamByPlayer(player); //Team Selector
            if (team == null || team.isSpectator()) {
                team = TeamUtils.getTeamWithFewestPlayers();
                team.add(player);
            } else {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeTeamEvent(player, team, null));
            }
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        GameHandler.getHandler().getMatch().setState(MatchState.ENDED);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            PlayerUtils.resetPlayer(player);
            player.setAllowFlight(true);
            player.setFlying(true);

            S.playSound(player, Sound.FIREWORK_TWINKLE);
        }

        ((PeaceModule) GameHandler.getHandler().getMatch().getGame().getModule(PeaceModule.class)).setPeace(true);

        ((PostgameModule) GameHandler.getHandler().getMatch().getGame().getModule(PostgameModule.class)).startCycleCountdown();
    }

    @EventHandler
    public void onTeamWinEvent(TeamWinEvent event) {
        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(C.divider);

        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(
                "        "
                + C.bold + event.getWinningTeam().getDisplayName()
                + C.white + " won the match");

        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(C.divider);
        Bukkit.getServer().broadcastMessage("");

        for (Player player : event.getWinningTeam().getPlayers()) {
            Fireworks.spawnRandomFirework(player.getLocation());
        }

        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameHandler.getGameHandler().getMatch()));
    }

    @EventHandler
    public void onLavaFlow(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.LAVA
                || event.getBlock().getType() == Material.STATIONARY_LAVA) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setFoodLevel(10);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onLeave(PlayerKickEvent event) {
        event.setLeaveMessage("");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(C.aqua + event.getPlayer().getName() + C.white + " joined the match");
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
