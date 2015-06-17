package com.minehut.mgm;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.sound.S;
import com.minehut.core.Core;
import com.minehut.core.player.PlayerInfo;
import com.minehut.core.player.Rank;
import com.minehut.mgm.command.*;
import com.minehut.mgm.util.C;
import com.minehut.status.Status;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by luke on 5/30/15.
 */
public class MGM extends JavaPlugin implements Listener {
    private static MGM instance;
    private GameHandler gameHandler;

    /* Database */
    private MongoClient mongo;
    private DB db;

    @Override
    public void onEnable() {
        this.instance = this;
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.connect();

        /* Commands */
        this.registerCommands();

        try {
            this.gameHandler = new GameHandler();
        } catch (Exception e) {
            F.log("failed to initiate game handler");
            e.printStackTrace();
        }

        /* Status */
        Status.getStatus().startStatusUpload("DTW", "Warzone", "Bravo", "Destroy the enemy team's wool core.");

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    private void connect() {
        try {
            this.mongo = new MongoClient("localhost", 27017);
            this.db = mongo.getDB("minehut");
            db.addOption(Bytes.QUERYOPTION_NOTIMEOUT);

            if (this.db == null) {
                F.log("Failed to connect to Database.");
                return;
            }

        } catch (Exception e) {
            F.log("Failed to connect to Database.");
        }
    }

    private void registerCommands() {
        new CycleCommand(this);
        new EndCommand(this);
        new StartCommand(this);
        new PauseCommand(this);
        new JoinCommand(this);
        new LeaveCommand(this);
        new KitCommand(this);
        new KitsCommand(this);
        new SetNextCommand(this);
        new MapsCommand(this);
        new LoadMapsCommand(this);
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        boolean safe = true;
        for (String line : event.getLines()) {
            if (line.length()>200) {
                event.setCancelled(true);
                safe=false;
                break;
            }
        }
        if (!safe) {
            event.setLine(0, "");
            event.setLine(1, "");
            event.setLine(2, "");
            event.setLine(3, "");

            for (PlayerInfo playerInfo : Core.getInstance().getPlayersInfos()) {
                if (playerInfo.getRank().has(null, Rank.Mod, false)) {
                    Player staff = Bukkit.getPlayer(playerInfo.getUuid());
                    S.playSound(staff, Sound.GHAST_SCREAM);
                    F.message(staff, "Cheat", C.aqua + event.getPlayer().getName() + C.red + " attempted the Sign Exploit Hack");
                }
            }

            S.playSound(event.getPlayer(), Sound.GHAST_SCREAM);
            event.getPlayer().sendMessage("");
            event.getPlayer().sendMessage(C.green + "such sign, much long, wow, so ban");
            event.getPlayer().sendMessage("");
        }
    }

    public DB getDb() {
        return db;
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public static MGM getInstance() {
        return instance;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
