package com.minehut.mgm.game.coreModules.damage;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;

import java.util.ArrayList;

/**
 * Created by luke on 6/2/15.
 */
public class DamageManagerModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();
        results.add(new DamageManagerModule());
        return results;
    }
}
