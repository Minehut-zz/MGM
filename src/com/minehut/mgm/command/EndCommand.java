package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.GameEndEvent;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.util.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class EndCommand extends Command {

    public EndCommand(JavaPlugin plugin) {
        super(plugin, "end", Rank.Mod);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        if (GameHandler.getHandler().getMatch().getState() == MatchState.PLAYING) {
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameHandler.getGameHandler().getMatch()));
            F.broadcast(C.red + C.bold + player.getName() + C.white + " has force ended the match");
        } else {
            player.sendMessage(C.red + "The game hasn't started yet.");
        }

        return false;
    }
}
