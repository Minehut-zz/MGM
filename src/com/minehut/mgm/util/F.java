package com.minehut.mgm.util;

import com.minehut.commons.common.sound.S;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by luke on 6/7/15.
 */
public class F {
    public static void broadcast(String message) {
        Bukkit.getServer().broadcastMessage(C.gray + C.bold + "MGM » " + C.white + message);
    }

    public static void log(String message) {
        System.out.println("[Log] MGM » " + message);
    }

    public static void noPerm(Player player, String message) {
        S.playSound(player, Sound.NOTE_BASS);
        player.sendMessage(C.warning + C.gray + " " + message);
    }
}
