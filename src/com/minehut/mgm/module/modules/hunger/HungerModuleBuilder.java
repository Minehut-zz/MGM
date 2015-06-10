package com.minehut.mgm.module.modules.hunger;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class HungerModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();
        try {
            String data = match.getDocument().getRootElement().getChild("hunger").getChildText("depletion");
            if (data.equalsIgnoreCase("off")) {
                results.add(new HungerModule(false));
            } else {
                results.add(new HungerModule(true));
            }
        } catch (NullPointerException e) {
            results.add(new HungerModule(true));
        }
        return results;
    }

}
