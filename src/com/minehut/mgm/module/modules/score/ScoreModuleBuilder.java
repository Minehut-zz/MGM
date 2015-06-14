package com.minehut.mgm.module.modules.score;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.grenade.GrenadeModule;
import org.jdom2.Element;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class ScoreModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element element = match.getDocument().getRootElement().getChild("scores");
        if (element != null) {
            int target = Integer.parseInt(element.getAttributeValue("target"));
            results.add(new ScoreModule(target));
        }

        return results;
    }

}
