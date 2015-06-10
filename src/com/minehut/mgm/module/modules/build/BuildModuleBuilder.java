package com.minehut.mgm.module.modules.build;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModule;
import org.jdom2.Element;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class BuildModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();
        boolean enabled = false;

        Element element = match.getDocument().getRootElement().getChild("build");
        if (element != null) {
            String data = element.getText();

            if (data != null && data.equalsIgnoreCase("off")) {
                enabled = false;
            } else {
                enabled = true;
            }

            results.add(new BuildModule(enabled));
        }


        return results;
    }

}
