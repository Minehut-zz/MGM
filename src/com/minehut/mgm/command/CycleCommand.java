package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.MGM;
import com.minehut.mgm.util.C;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class CycleCommand extends Command {

    public CycleCommand(JavaPlugin plugin) {
        super(plugin, "cycle", Rank.Mod);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {
        F.broadcast(C.red + C.bold + player.getName() + C.white + " has cycled maps");
        MGM.getInstance().getGameHandler().cycleAndMakeMatch();

        return false;
    }
}
