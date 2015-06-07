package com.minehut.mgm.module.modules.team;

import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

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

    public TeamModule(String name, String id, ChatColor color, boolean spectator) {
        this.name = name;
        this.id = id;
        this.color = color;
        this.spectator = spectator;

        this.players = new ArrayList<>();
        this.spawns = new ArrayList<>();
    }

    public void addSpawn(Location location) {
        this.spawns.add(location);
    }

    public void removeSpawn(Location location) {
        if (this.spawns.remove(this.spawns)) {
            this.spawns.remove(location);
        }
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }

    public void add(Player player) {
        this.players.add(player);
    }

    public void remove(Player player) {
        if (this.players.contains(player)) {
            this.players.remove(player);
        }
    }

    public Location getRandomSpawn() {
        ArrayList<Location> shuffled = new ArrayList<>();
        Collections.shuffle(shuffled);

        return shuffled.get(0);
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

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
