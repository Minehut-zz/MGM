package com.minehut.mgm.module.modules.destroyableBreakManager;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.TeamWinEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.destroyable.Destroyable;
import com.minehut.mgm.util.DestroyableUtils;
import com.minehut.mgm.util.F;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by luke on 6/7/15.
 */
public class DestroyableBreakManagerModule implements Module {


    @EventHandler
    public void onDestroyableBreak(BlockBreakEvent event) {
        for (Destroyable destroyable : DestroyableUtils.getAllDestroyables()) {
            Destroyable.DestroyableBreakAttempt attempt = destroyable.getBreakAttempt(event.getPlayer(), event.getBlock());
            if (attempt == Destroyable.DestroyableBreakAttempt.SUCCESSFUL) {
                S.playSound(Sound.FIREWORK_TWINKLE);
                F.broadcast(event.getPlayer().getDisplayName() + C.gray + " destroyed a " + destroyable.getTeam().getDisplayName() + C.aqua + " " + destroyable.getName());

                if (destroyable.decrementAndCheckCompleted()) {
                    Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(GameHandler.getGameHandler().getMatch(), TeamUtils.getTeamByPlayer(event.getPlayer())));
                }
            }

            else if (attempt == Destroyable.DestroyableBreakAttempt.OWNS) {
                F.noPerm(event.getPlayer(), "Don't break your team's objective maggot");
                event.setCancelled(true);
            }
        }
    }





    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }
}
