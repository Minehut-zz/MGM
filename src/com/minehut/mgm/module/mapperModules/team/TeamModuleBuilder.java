package com.minehut.mgm.module.mapperModules.team;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.util.ParseUtils;
import org.bukkit.ChatColor;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/5/15.
 */
public class TeamModuleBuilder implements ModuleBuilder {

    @Override
    public ArrayList<Module> load(Match match) {
        ArrayList<Module> results = new ArrayList<>();

        Document doc = match.getDocument();
        Element teams = doc.getRootElement().getChild("teams");
        List<Element> teamElements = teams.getChildren();
        for (Element teamNode : teamElements) {
            String name = teamNode.getText();
            String id = teamNode.getAttributeValue("id");
            ChatColor color = ParseUtils.parseChatColor(teamNode.getAttributeValue("color"));

            results.add(new TeamModule(name, id, color, false));
            F.log("Added team: " + name + " with id: " + id);
        }
        results.add(new TeamModule("Spectators", "spectators", ChatColor.AQUA, true));
        F.log("Added team: Spectators");

        return results;
    }
}
