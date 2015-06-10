package com.minehut.mgm.module.modules.region;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.util.LocationUtils;
import org.bukkit.Location;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/7/15.
 */
public class RegionBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element regions = match.getDocument().getRootElement().getChild("regions");

        /* Cubes */
        List<Element> cubes = regions.getChildren("cube");
        for (Element node : cubes) {
            String name = node.getAttributeValue("name");
            Location loc1 = LocationUtils.convert(GameHandler.getGameHandler().getMatchWorld(), node.getAttributeValue("min"));
            Location loc2 = LocationUtils.convert(GameHandler.getGameHandler().getMatchWorld(), node.getAttributeValue("max"));

            results.add(new Region(name, loc1, loc2));
        }

        /* Cylinders */
        List<Element> cylinders = regions.getChildren("cyl");
        for (Element node : cylinders) {
            String name = node.getAttributeValue("name");
            Location center = LocationUtils.convert(GameHandler.getGameHandler().getMatchWorld(), node.getAttributeValue("center"));
            int radius = Integer.parseInt(node.getAttributeValue("radius"));
            int height = Integer.parseInt(node.getAttributeValue("height"));

            results.add(new Region(name, center, radius, height));
        }

        return results;
    }
}
