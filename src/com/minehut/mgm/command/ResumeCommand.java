package com.minehut.mgm.command;

import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
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
public class ResumeCommand extends Command {

    public ResumeCommand(JavaPlugin plugin) {
        super(plugin, "resume", Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        MatchState matchState = GameHandler.getGameHandler().getMatch().getState();

        if (matchState == MatchState.STARTING) {
            ((PregameModule) GameHandler.getHandler().getMatch().getGame().getModule(PregameModule.class)).setPaused(false);
        } else if (matchState == MatchState.WAITING) {
            ((PostgameModule) GameHandler.getHandler().getMatch().getGame().getModule(PostgameModule.class)).setPaused(false);
        }

        F.broadcast(C.red + C.bold + player.getName() + C.white + " has resumed the timer");
        return false;
    }
}
