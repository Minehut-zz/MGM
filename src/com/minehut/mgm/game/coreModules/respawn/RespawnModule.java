package com.minehut.mgm.game.coreModules.respawn;

import com.minehut.commons.common.color.ColorConverter;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.coreModules.damage.CustomDeathEvent;
import com.minehut.mgm.game.coreModules.damage.CustomRespawnEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class RespawnModule implements Module {

    public RespawnModule() {
    }

    @EventHandler
    public void onDeath(CustomDeathEvent event) {
        ArrayList<Player> ignore = new ArrayList<>();

        if (event.getDeadPlayer() != null) {
            ignore.add(event.getDeadPlayer());

            S.playSound(event.getDeadPlayer(), Sound.IRONGOLEM_HIT);
        }

        if (event.getKillerPlayer() != null) {
            ignore.add(event.getKillerPlayer());

            S.playSound(event.getKillerPlayer(), Sound.IRONGOLEM_HIT);
        }

        for (Entity entity : event.getDeadEntity().getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (!ignore.contains(player)) {
                    player.playSound(event.getDeadEntity().getLocation(), Sound.IRONGOLEM_HIT, 10, 1);
                }
            }
        }

        Bukkit.getServer().broadcastMessage(this.getDeathMessage(event.getKillerPlayer(), event.getDeadPlayer(), event));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRespawn(CustomRespawnEvent event) {
        PlayerUtils.resetPlayer(event.getPlayer());

        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 10));

        if (!GameHandler.getHandler().getMatch().isRunning()) {
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().setFlying(true);
        } else {
//            team.getKit().apply(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawnDouble(CustomRespawnEvent event) {
        TeamModule team = TeamUtils.getTeamByPlayer(event.getPlayer());
        event.setSpawn(team.getRandomSpawn());
        event.getPlayer().teleport(event.getSpawn());
    }


    public String getDeathMessage(Player killer, Player dead, CustomDeathEvent event) {
        String d = event.getCause();
        TeamModule deadTeam = TeamUtils.getTeamByPlayer(dead);
        ChatColor dcolor = deadTeam.getColor();


        if( killer != null) {
            TeamModule killerTeam = TeamUtils.getTeamByPlayer(killer);
            ChatColor kcolor = killerTeam.getColor();

            if (d.equals("void")) {
                return dcolor + dead.getName() + C.gray + " was knocked into the void by " + kcolor + killer.getName();
            } else if (d.equals("slain")) {
                return dcolor + dead.getName() + C.gray + " was slain by " + kcolor + killer.getName();
            } else if (d.equals("projectile")) {
                return dcolor + dead.getName() + C.gray + " was shot by " + kcolor + killer.getName();
            } else {
                return dcolor + dead.getName() + C.gray + " was killed by " + kcolor + killer.getName();
            }
        } else {
            if (d.equals("void")) {
                return dcolor + dead.getName() + C.gray + " fell into the void";
            } else if (d.equals("water")) {
                return dcolor + dead.getName() + C.gray + " ran out of breath underwater";
            } else if (d.equals("explode")) {
                return dcolor + dead.getName() + C.gray + " was blasted by an explosion";
            } else if (d.equals("fire")) {
                return dcolor + dead.getName() + C.gray + " was burnt to a crisp";
            } else {
                return dcolor + dead.getName() + C.gray + " was killed";
            }
        }
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
