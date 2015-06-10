package com.minehut.mgm.util;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.module.modules.destroyable.Destroyable;

import java.util.ArrayList;

/**
 * Created by luke on 6/7/15.
 */
public class DestroyableUtils {

    public static ArrayList<Destroyable> getDestroyablesByTeam(TeamModule teamModule) {
        ArrayList<Destroyable> results = new ArrayList<>();
        for (Destroyable destroyable : GameHandler.getHandler().getMatch().getGame().getModules(Destroyable.class)) {
            if (destroyable.getTeam() == teamModule) {
                results.add(destroyable);
            }
        }
        return results;
    }

    public static ArrayList<Destroyable> getAllDestroyables() {
        return GameHandler.getGameHandler().getMatch().getGame().getModules(Destroyable.class);
    }
}
