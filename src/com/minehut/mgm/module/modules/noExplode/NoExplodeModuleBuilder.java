package com.minehut.mgm.module.modules.noExplode;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.noModify.NoModifyModule;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.RegionUtils;
import com.minehut.mgm.util.TeamUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/8/15.
 */
public class NoExplodeModuleBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Document doc = match.getDocument();
        Element filters = doc.getRootElement().getChild("filters");

        if(filters == null) return null;

        List<Element> elements = filters.getChildren();
        if(elements == null) return null;

        for (Element node : elements) {
            if(!node.getName().equalsIgnoreCase("noexplode")) continue;

            Region region = RegionUtils.getRegion(node.getAttributeValue("region"));
            TeamModule team = TeamUtils.getTeamById(node.getAttributeValue("team"));

            results.add(new NoExplodeModule(region, team));
        }
        return results;
    }

}
