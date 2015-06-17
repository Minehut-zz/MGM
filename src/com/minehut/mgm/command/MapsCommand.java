package com.minehut.mgm.command;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.rotation.LoadedMap;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luke on 6/8/15.
 */
public class MapsCommand extends Command {


    public MapsCommand(JavaPlugin plugin) {
        super(plugin, "maps", Arrays.asList("rotation"), Rank.regular);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        player.sendMessage("");

        int i = 1;
        for (LoadedMap loadedMap : GameHandler.getHandler().getRotation().getRotation()) {
            if(loadedMap == GameHandler.getGameHandler().getMatch().getLoadedMap()) {
                player.sendMessage(C.gray + i + ". " + C.yellow + loadedMap.getName());
            } else {
                player.sendMessage(C.gray + i + ". " + C.aqua + loadedMap.getName());
            }
            i++;
        }

        player.sendMessage("");

        return false;
    }
}
