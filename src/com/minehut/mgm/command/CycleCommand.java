package com.minehut.mgm.command;

import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.MGM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class CycleCommand extends Command {

    public CycleCommand(JavaPlugin plugin) {
        super(plugin, "cycle", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {
        Bukkit.getServer().broadcastMessage("Cycling maps");
        MGM.getInstance().getGameHandler().cycleAndMakeMatch();

        return false;
    }
}
