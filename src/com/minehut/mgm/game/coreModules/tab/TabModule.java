package com.minehut.mgm.game.coreModules.tab;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.timer.GameTimer;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/8/15.
 */
public class TabModule implements Module {
    int taskID;
    private ProtocolManager protocolManager;

    public TabModule() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        this.taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    updatePlayer(player);
                }
            }
        }, 20L, 20L);
    }

    public void updatePlayer(Player player) {
        String timerColor = "";
        switch (GameHandler.getHandler().getMatch().getState()) {
            case STARTING: timerColor = C.gold;
                break;
            case PLAYING: timerColor = C.green;
                break;
            default : timerColor = C.red;
        }



        String header = C.gray + "You are playing on " + C.aqua + "mc.minehut.com";
        String footer = C.gray + "Time: "
                + timerColor
                + StringUtils.formatTime(GameTimer.getTimeInSeconds())
                + C.white + "  -  "
                + C.gray + "Map: "
                + C.white + GameHandler.getHandler().getMatch().getLoadedMap().getName()
                + C.white + "  -  "
                + C.gray + "Gamemode: "
                + C.white + C.bold + GameHandler.getHandler().getMatch().getGame().getName();
        PlayerUtils.setTab(player, header, footer);
    }

    private String fixColors(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void unload() {
        Bukkit.getServer().getScheduler().cancelTask(this.taskID);
    }
}
