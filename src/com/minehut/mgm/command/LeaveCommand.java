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
public class LeaveCommand extends Command {
    public LeaveCommand(JavaPlugin plugin) {
        super(plugin, "leave", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        if (TeamUtils.getSpectators().contains(player)) {
            F.warning(player, "You are already out of the match");
            return true;
        }

        TeamModule spectators = TeamUtils.getSpectators();
        spectators.add(player);

        return false;
    }
}
