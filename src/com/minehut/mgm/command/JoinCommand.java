package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.PlayerUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class JoinCommand extends Command {


    public JoinCommand(JavaPlugin plugin) {
        super(plugin, "join", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {
        if (!TeamUtils.getSpectators().contains(player)) {
            F.warning(player, "You are already on a team");
            return true;
        }

        TeamModule team = TeamUtils.getTeamWithFewestPlayers();
        team.add(player);

        return false;
    }
}
