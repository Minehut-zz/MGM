package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.coreModules.pregame.PregameModule;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.util.C;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class StartCommand extends Command {

    public StartCommand(JavaPlugin plugin) {
        super(plugin, "start", Rank.Mod);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        if (GameHandler.getHandler().getMatch().getState() == MatchState.STARTING) {
            ((PregameModule) GameHandler.getHandler().getMatch().getGame().getModule(PregameModule.class)).startGame();
            F.broadcast(C.red + C.bold + player.getName() + C.white + " has force started the match");

        } else {
            player.sendMessage(C.red + "Game has already started");
        }
        return false;
    }
}
