package com.minehut.mgm.game.kit.kitPlayer;

import com.minehut.mgm.game.coreModules.damage.CustomRespawnEvent;
import com.minehut.mgm.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

/**
 * Created by luke on 6/13/15.
 */
public class KitPlayerManager implements Module {
    private ArrayList<KitPlayer> kitPlayers;

    public KitPlayerManager() {
        this.kitPlayers = new ArrayList<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //Todo
    }

    @EventHandler
    public void onRespawn(CustomRespawnEvent event) {
        this.getKitPlayer(event.getPlayer()).getSelectedKit().apply(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.kitPlayers.remove(this.getKitPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        this.kitPlayers.remove(this.getKitPlayer(event.getPlayer()));
    }

    public KitPlayer getKitPlayer(Player player) {
        for (KitPlayer kitPlayer : this.kitPlayers) {
            if (kitPlayer.getName().equalsIgnoreCase(player.getName())) {
                return kitPlayer;
            }
        }
        return null;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
