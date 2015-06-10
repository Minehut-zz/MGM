package com.minehut.mgm.module.modules.spawn;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.F;
import com.minehut.mgm.util.LocationUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Location;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/7/15.
 */
public class SpawnBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        Document doc = match.getDocument();
        Element spawns = doc.getRootElement().getChild("spawns");
        List<Element> spawnElements = spawns.getChildren();
        for (Element spawnNode : spawnElements) {
            Location location = LocationUtils.convert(GameHandler.getGameHandler().getMatchWorld(), spawnNode.getText());

            String yaw = spawnNode.getAttributeValue("yaw");
            if (yaw != null) {
                location.setYaw(Float.parseFloat(yaw));
            }

            if (spawnNode.getName().equalsIgnoreCase("spawn")) {
                TeamModule teamModule = TeamUtils.getTeamById(spawnNode.getAttributeValue("team"));
                teamModule.addSpawn(location);
                F.log("Added spawn point for team " + teamModule.getName());
            } else if (spawnNode.getName().equalsIgnoreCase("default")) {
                TeamUtils.getSpectators().addSpawn(location);
                F.log("Added spectator spawn point.");
            } else {
                F.log("Unknown element found in spawns");
            }

        }

        return null;
    }
}
