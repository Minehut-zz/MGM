package com.minehut.mgm.game.games.dtw;

import com.minehut.commons.common.chat.F;
import com.minehut.core.Core;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.*;
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
import com.minehut.mgm.game.kit.kitPlayer.GamePlayer;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.game.coreModules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.module.modules.destroyable.Destroyable;
import com.minehut.mgm.module.modules.destroyable.DestroyableBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModuleBuilder;
import com.minehut.mgm.module.modules.region.RegionBuilder;
import com.minehut.mgm.module.modules.score.ScoreModule;
import com.minehut.mgm.module.modules.spawn.SpawnBuilder;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.module.modules.team.TeamModuleBuilder;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.CreditUtils;
import com.minehut.mgm.util.DestroyableUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
public class DTW extends Game {
    private Scoreboard scoreboard;
    private Objective objective;

    public DTW(Match match) {
        super(match, "DTW",
                Arrays.asList(
                        new WarriorKit(),
                        new TankKit(),
                        new EngineerKit(),
                        new FireArcherKit(),
                        new InfiltratorKit(),
                        new AssassinKit(),
                        new StigmaKit(),
                        new GrenadierKit()
                ));
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

        /* Target Score */
        Score goal = objective.getScore(C.italics + "Destroy the");
        goal.setScore(113);

        Score goal2 = objective.getScore(C.italics + "Enemy Wool.");
        goal2.setScore(112);

        /* Target Score */
        Score targetScore = objective.getScore(C.italics + "First to 5 Wins.");
        targetScore.setScore(111);

        /* Gap */
        Score gap = objective.getScore("");
        gap.setScore(110);
    }

    public void increment(TeamModule teamModule) {
        Score score = this.objective.getScore(teamModule.getDisplayName());
        score.setScore(score.getScore() + 1);
    }

    @EventHandler
    public void onDestroyableBreak(DestroyableBreakEvent event) {
        TeamModule team = TeamUtils.getTeamByPlayer(event.getBreaker());

        this.increment(team);

        if (event.getBreaker() != null) {
            Core.getInstance().addCredits(event.getBreaker(), CreditUtils.getCreditsWithBonuses(event.getBreaker(), 4), "Destroy a Wool");
            GameHandler.getGameHandler().getKitManager().getGamePlayerManager().addXP(event.getBreaker(), 6);
        }

        if (event.getDestroyable().decrementAndCheckCompleted()) {
            Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(GameHandler.getGameHandler().getMatch(), team));
        }
    }

//    @EventHandler
//    public void onDeath(CustomDeathEvent event) {
//        if (event.getKillerPlayer() != null && event.getDeadPlayer() != null) {
//            TeamModule team = TeamUtils.getTeamByPlayer(event.getKillerPlayer());
//            this.increment(team);
//        }
//    }

    @EventHandler
    public void onPlayerChangeTeam(PlayerChangeTeamEvent event) {
        if (event.getOldTeam() != null) {
            this.scoreboard.getTeam(event.getOldTeam().getDisplayName()).removePlayer(event.getPlayer());
        }

        this.scoreboard.getTeam(event.getNewTeam().getDisplayName()).addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult().getType() == Material.SHEARS) {
            event.getInventory().setResult(new ItemStack(Material.AIR));

            for(HumanEntity he:event.getViewers()) {
                if(he instanceof Player) {
                    ((Player) he).closeInventory();
                    F.warning((Player) he, "You cannot craft shears in DTW");
                }
            }
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onKill(CustomDeathEvent event) {
        if (event.getDeadPlayer() != null && event.getKillerPlayer() != null) {
            if (event.getDeadPlayer() != event.getKillerPlayer()) {
                Core.getInstance().addCredits(event.getKillerPlayer(), CreditUtils.getCreditsWithBonuses(event.getKillerPlayer(), 2), "Kill an Enemy");
                GameHandler.getGameHandler().getKitManager().getGamePlayerManager().addXP(event.getKillerPlayer(), 4);
            }
        }
    }

    @EventHandler
    public void onGameEnd(TeamWinEvent event) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(event.getWinningTeam().getPlayers().contains(player)) {
                Core.getInstance().addCredits(player, CreditUtils.getCreditsWithBonuses(player, 10), "Win a Game");
                GameHandler.getGameHandler().getKitManager().getGamePlayerManager().addXP(player, 10);
            } else {
                Core.getInstance().addCredits(player, CreditUtils.getCreditsWithBonuses(player, 6), "Finish a Game");
                GameHandler.getGameHandler().getKitManager().getGamePlayerManager().addXP(player, 8);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            List<Block> toRemove = new ArrayList<>();
            for (Block block : event.blockList()) {
                toRemove.add(block);
            }
            for (Block block : toRemove) {
                event.blockList().remove(block);
            }
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
