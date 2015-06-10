package com.minehut.mgm.module.modules.grenade;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.build.BuildModule;
import org.jdom2.Element;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class GrenadeModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element element = match.getDocument().getRootElement().getChild("grenades");
        if (element != null) {
            results.add(new GrenadeModule());
        }

        return results;
    }

}
