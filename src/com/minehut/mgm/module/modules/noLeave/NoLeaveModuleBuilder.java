package com.minehut.mgm.module.modules.noLeave;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.safezone.SafezoneModule;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.RegionUtils;
import com.minehut.mgm.util.TeamUtils;
import org.jdom2.Element;

import java.util.ArrayList;

/**
 * Created by luke on 6/8/15.
 */
public class NoLeaveModuleBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element filters = match.getDocument().getRootElement().getChild("filters");
        if(filters == null) return null;

        Element node = filters.getChild("noleave");
        if(node == null) return null;

        Region region = RegionUtils.getRegion(node.getAttributeValue("region"));

        results.add(new NoLeaveModule(region));

        return results;
    }
}
