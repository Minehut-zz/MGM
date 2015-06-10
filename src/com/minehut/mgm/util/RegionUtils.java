package com.minehut.mgm.util;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.modules.region.Region;

/**
 * Created by luke on 6/7/15.
 */
public class RegionUtils {
    public static Region getRegion(String name) {
        for (Region region : GameHandler.getHandler().getMatch().getGame().getModules(Region.class)) {
            if (region.getName().equalsIgnoreCase(name)) {
                return region;
            }
        }
        return null;
    }
}
