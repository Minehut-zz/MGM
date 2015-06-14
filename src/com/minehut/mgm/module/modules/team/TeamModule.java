package com.minehut.mgm.module.modules.team;

import com.minehut.commons.common.sound.S;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.util.C;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.kit.Kit;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by luke on 6/2/15.
 */
public class TeamModule implements Module {
    private ArrayList<Player> players;
    private ArrayList<Location> spawns;
    private Kit kit;

    private String name;
    private ChatColor color;
    private String id;
    private boolean spectator;

    private int maxSize;

    public TeamModule(String name, String id, ChatColor color, boolean spectator) {
        this.name = name;
        this.id = id;
        this.color = color;
        this.spectator = spectator;

        this.players = new ArrayList<>();
        this.spawns = new ArrayList<>();

        this.maxSize = 50;
    }

    public void addSpawn(Location location) {
        this.spawns.add(location);
    }

    public void removeSpawn(Location location) {
        if (this.spawns.contains(this.spawns)) {
            this.spawns.remove(location);
        }
    }

    @EventHandler
     public void onQuit(PlayerQuitEvent event) {
        this.remove(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        this.remove(event.getPlayer());
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }

    public void add(Player player) {
        TeamModule oldTeam = TeamUtils.getTeamByPlayer(player);
        if (oldTeam != null) {
            oldTeam.remove(player);
        }

        this.players.add(player);
        player.setDisplayName(player.getName());

        Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeTeamEvent(player, this, oldTeam));

        player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
        player.sendMessage(C.white + "You have joined " + this.color + this.name);
    }

    public void remove(Player player) {
        if (this.players.contains(player)) {
            this.players.remove(player);
        }
    }

    public String getDisplayName() {
        return this.color + this.name;
    }

    public Location getRandomSpawn() {
        if(this.spawns.size() > 1) {
            ArrayList<Location> shuffled = new ArrayList<>();
            Collections.shuffle(shuffled);

            return shuffled.get(0);
        } else {
            return this.spawns.get(0);
        }
    }

    public int getSize() {
        return this.players.size();
    }

    public boolean isSpectator() {
        return spectator;
    }

    public boolean contains(Player player) {
        if (this.players.contains(player)) {
            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Location> getSpawns() {
        return spawns;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
