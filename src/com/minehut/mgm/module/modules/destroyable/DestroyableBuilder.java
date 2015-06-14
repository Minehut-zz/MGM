package com.minehut.mgm.module.modules.destroyable;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.*;
import org.bukkit.Material;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/7/15.
 */
public class DestroyableBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element destroyables = match.getDocument().getRootElement().getChild("destroyables");
        if(destroyables == null) {
            F.log("Couldn't find any destroyables.");
            return null;
        }

        results.add(new DestroyableBreakManagerModule());
        List<Element> elements = destroyables.getChildren("destroyable");
        for (Element node : elements) {
            TeamModule teamModule = TeamUtils.getTeamById(node.getAttributeValue("team"));
            String name = node.getAttributeValue("name");
            int amount = Integer.parseInt(node.getAttributeValue("amount"));
            Region region = RegionUtils.getRegion(node.getAttributeValue("region"));
            Material material = Material.matchMaterial(node.getAttributeValue("material"));

            results.add(new Destroyable(teamModule, name, amount, region, material));
            F.log("Added destroyable for team " + teamModule.getName());
        }

        return results;
    }
}
