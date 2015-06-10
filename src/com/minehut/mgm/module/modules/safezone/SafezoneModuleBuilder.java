package com.minehut.mgm.module.modules.safezone;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.RegionUtils;
import com.minehut.mgm.util.TeamUtils;
import org.jdom2.Element;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class SafezoneModuleBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element safezones = match.getDocument().getRootElement().getChild("safezones");
        for (Element node : safezones.getChildren("safezone")) {
            TeamModule team = TeamUtils.getTeamById(node.getAttributeValue("team"));
            Region region = RegionUtils.getRegion(node.getAttributeValue("region"));

            results.add(new SafezoneModule(region, team));
        }
        return results;
    }
}
