package com.minehut.mgm.module.modules.score;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.TeamWinEvent;
import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

/**
 * Created by luke on 6/7/15.
 */
public class ScoreModule implements Module {
    private int targetScore;
    private HashMap<TeamModule, Integer> scores;

    public ScoreModule(int targetScore) {
        this.targetScore = targetScore;
        this.scores = new HashMap<>();
        for (TeamModule teamModule : TeamUtils.getTeams()) {
            this.scores.put(teamModule, 0);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(CustomDeathEvent event) {
        if (event.getDeadPlayer() != null && event.getKillerPlayer() != null) {

            /* Suicide */
            if(event.getDeadPlayer() == event.getKillerPlayer()) return;

            TeamModule team = TeamUtils.getTeamByPlayer(event.getKillerPlayer());
            this.scores.put(team, this.scores.get(team) + 1);

            if (this.scores.get(team) >= this.targetScore) {
                Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(GameHandler.getGameHandler().getMatch(), team));
            }
        }
    }

    public TeamModule getWinningTeam() {
        TeamModule winningTeam = null;
        for (TeamModule team : this.scores.keySet()) {
            if (team == null) {
                winningTeam = team;
            } else {
                if (this.scores.get(team) > this.scores.get(winningTeam)) {
                    winningTeam = team;
                }
            }
        }

        return winningTeam;
    }

    public int getTargetScore() {
        return targetScore;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

}
