package com.minehut.mgm.game.games;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.game.Game;
import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.modules.score.ScoreModule;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

/**
 * Created by luke on 6/5/15.
 */
public class TDM extends Game {
    private Scoreboard scoreboard;
    private Objective objective;

    public TDM(Match match) {
        super(match, "TDM");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(this.scoreboard);
    }

    @EventHandler
    public void onCycleComplete(CycleCompleteEvent event) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(this.scoreboard);
        }
    }

    @Override
    public void postModulesLoaded() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        /* Objective */
        this.objective = scoreboard.registerNewObjective("scores", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(C.gold + "Scores");

        /* Team */
        for (TeamModule teams : TeamUtils.getTeams()) {
            Team prefixTeam = scoreboard.registerNewTeam(teams.getDisplayName());
            prefixTeam.setPrefix(teams.getColor() + "");
            prefixTeam.setDisplayName(teams.getDisplayName());

            if(!teams.isSpectator()) {
                Score score = objective.getScore(teams.getDisplayName());
                score.setScore(0);
            }
        }

        ScoreModule scoreModule = (ScoreModule) GameHandler.getGameHandler().getMatch().getGame().getModule(ScoreModule.class);

        /* Goal Score */
        Score goal = objective.getScore(C.italics + "Kill the Enemies");
        goal.setScore(999);

        /* Target Score */
        Score targetScore = objective.getScore(C.italics + "First to " + Integer.toString(scoreModule.getTargetScore()) + " wins!");
        targetScore.setScore(998);

        /* Gap */
        Score gap = objective.getScore("");
        gap.setScore(997);
    }

    public void increment(TeamModule teamModule) {
        Score score = this.objective.getScore(teamModule.getDisplayName());
        score.setScore(score.getScore() + 1);
    }

    @EventHandler
    public void onDeath(CustomDeathEvent event) {
        if (event.getKillerPlayer() != null && event.getDeadPlayer() != null) {
            TeamModule team = TeamUtils.getTeamByPlayer(event.getKillerPlayer());
            this.increment(team);
        }
    }

    @EventHandler
    public void onPlayerChangeTeam(PlayerChangeTeamEvent event) {
        if (event.getOldTeam() != null) {
            this.scoreboard.getTeam(event.getOldTeam().getDisplayName()).removePlayer(event.getPlayer());
        }

        this.scoreboard.getTeam(event.getNewTeam().getDisplayName()).addPlayer(event.getPlayer());
    }


    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
