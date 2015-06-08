package com.minehut.mgm.game.coreModules.wrapper;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.game.Game;
import com.minehut.mgm.game.coreModules.peace.PeaceModule;
import com.minehut.mgm.game.coreModules.postgame.PostgameModule;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.mapperModules.team.TeamModule;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

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

            TeamModule team = TeamUtils.getTeamByPlayer(player); //Team Selector
            if (team == null || team.isSpectator()) {
                team = TeamUtils.getTeamWithFewestPlayers();
                team.add(player);
            }

            player.teleport(team.getRandomSpawn());
            team.getKit().apply(player);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        GameHandler.getHandler().getMatch().setState(MatchState.ENDED);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            PlayerUtils.resetPlayer(player);
            player.setFlying(true);
        }

        ((PeaceModule) GameHandler.getHandler().getMatch().getGame().getModule(PeaceModule.class)).setPeace(true);

        ((PostgameModule) GameHandler.getHandler().getMatch().getGame().getModule(PostgameModule.class)).startCycleCountdown();
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
