package com.minehut.mgm.module.modules.itemdrop;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import org.bukkit.Material;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/8/15.
 */
public class ItemDropModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Element drops = match.getDocument().getRootElement().getChild("drops");
        if(drops != null) {
            List<Element> elements = drops.getChildren("drop");
            if (elements != null) {

                ItemDropModule.DropMode dropMode = ItemDropModule.DropMode.ALLOW;
                String dropModeName = drops.getAttributeValue("mode");
                if (dropModeName != null) {
                    if (dropModeName.equalsIgnoreCase("allow")) {
                        dropMode = ItemDropModule.DropMode.ALLOW;
                    }
                    else if (dropModeName.equalsIgnoreCase("deny")) {
                        dropMode = ItemDropModule.DropMode.DENY;
                    }
                    else if (dropModeName.equalsIgnoreCase("all")) {
                        dropMode = ItemDropModule.DropMode.ALL;
                    }
                }

                ArrayList<ItemDropInfo> allowedDrops = new ArrayList<>();
                for (Element node : elements) {
                    Material material = Material.matchMaterial(node.getText());
                    allowedDrops.add(new ItemDropInfo(material));
                }

                results.add(new ItemDropModule(allowedDrops, dropMode));
            }
        } else {
            /* Drops wasn't inside map.xml, default to all */
            results.add(new ItemDropModule(null, ItemDropModule.DropMode.ALL));
        }

        return results;
    }
}
