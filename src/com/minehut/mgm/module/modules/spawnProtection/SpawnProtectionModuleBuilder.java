package com.minehut.mgm.module.modules.spawnProtection;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.safezone.SafezoneModule;
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
public class SpawnProtectionModuleBuilder implements ModuleBuilder {
    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Document doc = match.getDocument();
        Element node = doc.getRootElement().getChild("protect");

        if(node == null) return null;

        int time = 3;
        String timeString = node.getAttributeValue("time");
        if (timeString != null) {
            time = Integer.parseInt(timeString);
        }

        results.add(new SpawnProtectionModule(time));

        return results;
    }

}
