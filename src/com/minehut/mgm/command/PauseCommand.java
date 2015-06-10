package com.minehut.mgm.command;

import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.postgame.PostgameModule;
import com.minehut.mgm.game.coreModules.pregame.PregameModule;
import com.minehut.mgm.match.MatchState;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.F;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class PauseCommand extends Command {

    public PauseCommand(JavaPlugin plugin) {
        super(plugin, "p", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        MatchState matchState = GameHandler.getGameHandler().getMatch().getState();

        boolean paused = false;

        if (matchState == MatchState.STARTING) {
            paused = ((PregameModule) GameHandler.getHandler().getMatch().getGame().getModule(PregameModule.class)).isPaused();

            ((PregameModule) GameHandler.getHandler().getMatch().getGame().getModule(PregameModule.class)).setPaused(!paused);
        } else if (matchState == MatchState.WAITING) {
            paused = ((PostgameModule) GameHandler.getHandler().getMatch().getGame().getModule(PostgameModule.class)).isPaused();

            ((PostgameModule) GameHandler.getHandler().getMatch().getGame().getModule(PostgameModule.class)).setPaused(!paused);
        }

        if (!paused) { //Inveres since it was changed.
            F.broadcast(C.red + C.bold + player.getName() + C.white + " has paused the timer");
        } else {
            F.broadcast(C.red + C.bold + player.getName() + C.white + " has resumed the timer");
        }

        return false;
    }
}
