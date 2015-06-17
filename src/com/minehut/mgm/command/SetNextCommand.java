package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.rotation.LoadedMap;
import com.minehut.mgm.util.C;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luke on 5/31/15.
 */
public class SetNextCommand extends Command {

    public SetNextCommand(JavaPlugin plugin) {
        super(plugin, "setnext", Arrays.asList("sn"), Rank.Mod);
    }

    @Override
    public boolean call(Player player, ArrayList<String> args) {

        if(args == null || args.size() < 1) {
            player.sendMessage(C.red + "/setnext (map)");
            return false;
        }

        String mapName = args.get(0);
        for (String s : args) {
            if (args.indexOf(s) != 0) {
                mapName += " " + s;
            }
        }

        LoadedMap nextMap = getMap(mapName);
        if (nextMap == null) {
            F.message(player, "Maps", "Couldn't find map " + C.aqua + mapName);
        }
        else if (nextMap == GameHandler.getGameHandler().getMatch().getLoadedMap()) {
            F.message(player, "Maps", "Next map cannot be same as the current map");
        }
        else {
            setCycleMap(nextMap);
            F.broadcast("Maps", C.red + C.bold + player.getName() + C.yellow + " set the next map to " + C.aqua + nextMap.getName());
        }

        return false;
    }

    private static LoadedMap getMap(String input) {
        input = input.toLowerCase().replaceAll(" ", "");
        LoadedMap result = null;
        for (LoadedMap loadedMap : GameHandler.getGameHandler().getRotation().getLoaded()) {
            if (loadedMap.getName().equalsIgnoreCase(input.toLowerCase())) {
                result = loadedMap;
            }
        }
        if (result == null) {
            for (LoadedMap loadedMap : GameHandler.getGameHandler().getRotation().getLoaded()) {
                if (loadedMap.getName().toLowerCase().startsWith(input.toLowerCase())) {
                    result = loadedMap;
                }
            }
        }
        return result;
    }

    private static void setCycleMap(LoadedMap map) {
        GameHandler.getGameHandler().getCycle().setMap(map);
    }
}
