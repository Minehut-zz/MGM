package com.minehut.mgm.command;

import com.minehut.commons.common.chat.F;
import com.minehut.core.command.Command;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.rotation.exception.RotationLoadException;
import com.minehut.mgm.util.C;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luke on 5/31/15.
 */
public class LoadMapsCommand extends Command {

    public LoadMapsCommand(JavaPlugin plugin) {
        super(plugin, "loadmaps", Rank.Mod);
    }

    @Override
    public boolean call(Player player, ArrayList<String> arrayList) {

        try {
            GameHandler.getHandler().getRotation().refreshRepo();
            GameHandler.getGameHandler().getRotation().refreshRotation();
            player.sendMessage("");
            F.message(player, "Maps", "Map Rotation has been refreshed");
            player.sendMessage("");
        } catch (Exception e) {

            F.message(player, "Maps", C.red + "Error while loading maps");

            e.printStackTrace();
        }

        return false;
    }
}
