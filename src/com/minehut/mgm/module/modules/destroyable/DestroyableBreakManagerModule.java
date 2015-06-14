package com.minehut.mgm.module.modules.destroyable;

import com.minehut.commons.common.chat.C;
import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.event.DestroyableBreakEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.util.DestroyableUtils;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by luke on 6/7/15.
 */
public class DestroyableBreakManagerModule implements Module {


    @EventHandler(priority = EventPriority.HIGH)
    public void onDestroyableBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(!GameHandler.getGameHandler().getMatch().isRunning()) return;

        for (Destroyable destroyable : DestroyableUtils.getAllDestroyables()) {
            Destroyable.DestroyableBreakAttempt attempt = destroyable.getBreakAttempt(event.getPlayer(), event.getBlock());
            if (attempt == Destroyable.DestroyableBreakAttempt.SUCCESSFUL) {
                S.playSound(Sound.FIREWORK_TWINKLE);
                F.broadcast(TeamUtils.getTeamByPlayer(event.getPlayer()).getColor() + C.bold + event.getPlayer().getDisplayName() + C.gray + " destroyed a " + destroyable.getTeam().getDisplayName() + C.aqua + " " + destroyable.getName());

                Bukkit.getServer().getPluginManager().callEvent(new DestroyableBreakEvent(destroyable, event.getPlayer()));
                event.getBlock().setType(Material.AIR);
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
