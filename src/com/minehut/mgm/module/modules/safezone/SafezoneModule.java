package com.minehut.mgm.module.modules.safezone;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 6/8/15.
 */
public class SafezoneModule implements Module {
    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    public Region region;
    public TeamModule team;

    public SafezoneModule(Region region, TeamModule team) {
        this.region = region;
        this.team = team;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomDamage(CustomDamageEvent event) {
        if(event.isCancelled()) return;
        if (event.getHurtPlayer() != null) {
            if (TeamUtils.getTeamByPlayer(event.getHurtPlayer()) == this.team) {
                if (region.insideRegion(event.getHurtPlayer().getLocation())) {
                    event.setCancelled(true);

                    if (event.getDamagerPlayer() != null) {
                        F.warning(event.getDamagerPlayer(), "Target is inside a safezone");
                    }
                }
            }
        }
    }
}
