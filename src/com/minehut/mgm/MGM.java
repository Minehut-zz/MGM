package com.minehut.mgm;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.command.*;
import com.minehut.status.Status;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by luke on 5/30/15.
 */
public class MGM extends JavaPlugin {
    private static MGM instance;
    private GameHandler gameHandler;

    @Override
    public void onEnable() {
        this.instance = this;

        /* Commands */
        this.registerCommands();

        try {
            this.gameHandler = new GameHandler();
        } catch (Exception e) {
            F.log("failed to initiate game handler");
            e.printStackTrace();
        }

        /* Status */
        Status.getStatus().startStatusUpload("dev", "dev", "echo", "MGM Testing");
    }

    private void registerCommands() {
        new CycleCommand(this);
        new EndCommand(this);
        new StartCommand(this);
        new PauseCommand(this);
        new JoinCommand(this);
        new LeaveCommand(this);
    }

    public static MGM getInstance() {
        return instance;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
