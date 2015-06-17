package com.minehut.mgm.game.kit.kitPlayer;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.event.GameStartEvent;
import com.minehut.mgm.event.PlayerChangeTeamEvent;
import com.minehut.mgm.game.coreModules.damage.CustomRespawnEvent;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.game.kit.KitManager;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.ParseUtils;
import com.minehut.mgm.util.TeamUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by luke on 6/13/15.
 */
public class GamePlayerManager implements Listener {
    private KitManager kitManager;

    private ArrayList<GamePlayer> gamePlayers;
    private DBCollection gamePlayersCollection;

    public GamePlayerManager(KitManager kitManager) {
        this.kitManager = kitManager;
        this.gamePlayers = new ArrayList<>();

        this.gamePlayersCollection = MGM.getInstance().getDb().getCollection("gamePlayers");

        MGM.getInstance().getServer().getPluginManager().registerEvents(this, MGM.getInstance());
    }

    @EventHandler
    public void onCycle(CycleCompleteEvent event) {
        for (GamePlayer gamePlayer : this.gamePlayers) {

            Bukkit.getServer().getScheduler().runTaskAsynchronously(MGM.getInstance(), new Runnable() {
                @Override
                public void run() {
                    gamePlayer.resetKits();
                    load(gamePlayer);
                }
            });
            gamePlayer.setSelectedKit(this.kitManager.getDefaultKit());
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        for (GamePlayer gamePlayer : this.gamePlayers) {
            gamePlayer.setLastDamagedFrom(null);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        GamePlayer gamePlayer = new GamePlayer(event.getPlayer().getName(), event.getPlayer().getUniqueId());
        gamePlayers.add(gamePlayer);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                initiate(gamePlayer);
                gamePlayer.setLoaded(true);
            }
        });
    }

    /* Returns true if player is new */
    private boolean initiate(GamePlayer gamePlayer) {
        DBObject r = new BasicDBObject("uuid", gamePlayer.getUuid().toString());
        DBObject found = this.gamePlayersCollection.findOne(r);

        if (found == null) {
            /* New Player */
            storeFirstTimePlayer(gamePlayer);

            return true;
        } else {
            /* Returning Player */

            /* Update player name (name changes) */
            DBObject updated = new BasicDBObject("uuid", gamePlayer.getUuid().toString());
            updated.put("name", gamePlayer.getName());

            /* Load kits */
            load(gamePlayer);

            return false;
        }
    }

    public void saveKits(GamePlayer gamePlayer) {
        DBObject query = new BasicDBObject("uuid", gamePlayer.getUuid().toString());
        DBObject found = gamePlayersCollection.findOne(query);

        List<String> kits = new ArrayList<>();
        for (Kit kit : gamePlayer.getOwnedKits()) {
            kits.add(kit.getName());
        }

        found.put("kits", kits);
        gamePlayersCollection.findAndModify(query, found);
    }

    public void load(GamePlayer gamePlayer) {
        DBObject query = new BasicDBObject("uuid", gamePlayer.getUuid().toString());
        DBObject found = this.gamePlayersCollection.findOne(query);

        BasicDBList list = (BasicDBList) found.get("kits");

        for (Kit kit : kitManager.getKits()) {
            if (kit.getPrice() == 0) {
                gamePlayer.addOwnedKit(kit);
            }
        }

        for (Object kitName : list) {
            String name = (String) kitName;
            for (Kit kit : kitManager.getKits()) {
                if (kit.getName().equalsIgnoreCase(name)) {
                    if(!gamePlayer.hasKit(kit)) {
                        gamePlayer.addOwnedKit(kit);
                        continue;
                    }
                }
            }
        }

        /* XP */
        long xp = (long) found.get("xp");
        gamePlayer.setXp(xp);
    }

    public void addXP(Player player, long amount) {
        DBObject query = new BasicDBObject("uuid", player.getUniqueId().toString());
        DBObject found = this.gamePlayersCollection.findOne(query);

        long oldXP = (long) found.get("xp");
        long updatedXP = oldXP + amount;

        found.put("xp", (long) oldXP + amount);

        this.gamePlayersCollection.findAndModify(query, found);

        this.getGamePlayer(player).addXP(amount);
    }

    private void storeFirstTimePlayer(GamePlayer gamePlayer) {
        /* Key */
        UUID uuid = gamePlayer.getUuid();
        DBObject obj = new BasicDBObject("uuid", uuid.toString());
        obj.put("name", gamePlayer.getName());

        /* Kits */
        List<String> kits = new ArrayList<>();
        for (Kit kit : kitManager.getFreeKits()) {
            kits.add(kit.getName());
            gamePlayer.addOwnedKit(kit);
        }
        obj.put("kits", kits);

        /* XP */
        obj.put("xp", gamePlayer.getXp()); //Defaults to 0

        /* Push to Database */
        this.gamePlayersCollection.insert(obj);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onRespawn(CustomRespawnEvent event) {
        TeamModule teamModule = TeamUtils.getTeamByPlayer(event.getPlayer());
        this.getGamePlayer(event.getPlayer()).getSelectedKit().apply(event.getPlayer(), ParseUtils.getColorFromChatColor(teamModule.getColor()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameStart(GameStartEvent event) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!TeamUtils.isSpectator(player)) {
                TeamModule teamModule = TeamUtils.getTeamByPlayer(player);
                this.getGamePlayer(player).getSelectedKit().apply(player, ParseUtils.getColorFromChatColor(teamModule.getColor()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeamChangeEvent(PlayerChangeTeamEvent event) {
        if (GameHandler.getGameHandler().getMatch().isRunning()) {
            if (!TeamUtils.isSpectator(event.getPlayer())) {
                TeamModule teamModule = TeamUtils.getTeamByPlayer(event.getPlayer());
                this.getGamePlayer(event.getPlayer()).getSelectedKit().apply(event.getPlayer(), ParseUtils.getColorFromChatColor(teamModule.getColor()));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.gamePlayers.remove(this.getGamePlayer(event.getPlayer()));
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        this.gamePlayers.remove(this.getGamePlayer(event.getPlayer()));
    }

    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gamePlayer : this.gamePlayers) {
            if (gamePlayer.getName().equalsIgnoreCase(player.getName())) {
                return gamePlayer;
            }
        }
        return null;
    }

}
