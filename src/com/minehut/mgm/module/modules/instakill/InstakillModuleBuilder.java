package com.minehut.mgm.module.modules.instakill;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModule;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class InstakillModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        if (match.getDocument().getRootElement().getChild("instakill") != null) {
            results.add(new InstakillModule());
        }

        return results;
    }

}
