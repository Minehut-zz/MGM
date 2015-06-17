package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class KitCommand extends Command {


    public KitCommand(JavaPlugin plugin) {
        super(plugin, "kit", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {
        GameHandler.getHandler().getKitManager().openKitMenu(player);
        return false;
    }
}
