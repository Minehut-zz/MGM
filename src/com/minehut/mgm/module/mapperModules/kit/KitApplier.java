package com.minehut.mgm.module.mapperModules.kit;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.game.coreModules.damage.CustomRespawnEvent;
import com.minehut.mgm.module.mapperModules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

public class KitApplier implements Module {

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onCustomRespawn(CustomRespawnEvent event) {
        TeamModule team = TeamUtils.getTeamByPlayer(event.getPlayer());

        if (GameHandler.getGameHandler().getMatch().isRunning()) {
            team.getKit().apply(event.getPlayer());
        } else if (team.isSpectator()) {
            team.getKit().apply(event.getPlayer());
        }
    }

}
