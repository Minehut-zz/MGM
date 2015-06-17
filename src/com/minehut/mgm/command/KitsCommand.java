package com.minehut.mgm.command;

import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class KitsCommand extends Command {


    public KitsCommand(JavaPlugin plugin) {
        super(plugin, "kits", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {
        GameHandler.getHandler().getKitManager().openKitMenu(player);
        return false;
    }
}
