package com.minehut.mgm.module.modules.instakill;

import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

/**
 * Created by luke on 6/8/15.
 */
public class InstakillModule implements Module {
    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    public InstakillModule() {

    }

    @EventHandler
    public void onCustomDamage(CustomDamageEvent event) {
        if(event.getDamagerPlayer() != null) {
            if(event.getDamagerPlayer() != event.getHurtPlayer()) {
                event.setDamage(9999);
            }
        }
    }
}
