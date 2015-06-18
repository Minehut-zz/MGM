package com.minehut.mgm.game.games.tnt;

import com.minehut.commons.common.chat.F;
import com.minehut.core.Core;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.DestroyableBreakEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.event.TeamWinEvent;
import com.minehut.mgm.game.Game;
import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.game.games.dtw.kits.assassinKit.AssassinKit;
import com.minehut.mgm.game.games.dtw.kits.engineerKit.EngineerKit;
import com.minehut.mgm.game.games.dtw.kits.fireArcherKit.FireArcherKit;
import com.minehut.mgm.game.games.dtw.kits.grenadierKit.GrenadierKit;
import com.minehut.mgm.game.games.dtw.kits.infiltratorKit.InfiltratorKit;
import com.minehut.mgm.game.games.dtw.kits.stigmaKit.StigmaKit;
import com.minehut.mgm.game.games.dtw.kits.tankKit.TankKit;
import com.minehut.mgm.game.games.dtw.kits.warriorKit.WarriorKit;
import com.minehut.mgm.game.games.tnt.kits.BlasterKit;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.CreditUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luke on 6/5/15.
 */
public class TNT extends Game {
    private Scoreboard scoreboard;
    private Objective objective;

    public TNT(Match match) {
        super(match, "TNT",
                Arrays.asList(
                        new BlasterKit()
                ));

        super.setKillCredits(2);
        super.setKillXP(4);
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
                score.setScore(8);
            }
        }

        /* Target Score */
        Score goal = objective.getScore(C.italics + "Kill Enemies");
        goal.setScore(113);

        Score goal2 = objective.getScore(C.italics + "until their lives");
        goal2.setScore(112);

        Score goal3 = objective.getScore(C.italics + "run out.");
        goal3.setScore(111);

        /* Gap */
        Score gap = objective.getScore("");
        gap.setScore(110);
    }

    public void decrement(TeamModule teamModule) {
        Score score = this.objective.getScore(teamModule.getDisplayName());
        score.setScore(score.getScore() - 1);

        if (score.getScore() <= 0) {
            Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(GameHandler.getGameHandler().getMatch(), teamModule));
        }
    }

    @EventHandler
    public void onDeath(CustomDeathEvent event) {
        if (event.getKillerPlayer() != null && event.getDeadPlayer() != null) {
            if(event.getKillerPlayer() != event.getDeadPlayer()) {
                TeamModule team = TeamUtils.getTeamByPlayer(event.getDeadPlayer());
                this.decrement(team);
            }
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
