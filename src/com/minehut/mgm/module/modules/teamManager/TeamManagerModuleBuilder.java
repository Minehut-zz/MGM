package com.minehut.mgm.module.modules.teamManager;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class TeamManagerModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();
        results.add(new TeamManagerModule(match));
        return results;
    }
}
