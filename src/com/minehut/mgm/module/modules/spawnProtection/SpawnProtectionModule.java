package com.minehut.mgm.module.modules.spawnProtection;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.game.coreModules.damage.CustomRespawnEvent;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by luke on 6/7/15.
 */
public class SpawnProtectionModule implements Module {
    private int safeTime;
    private HashMap<UUID, Integer> timers;
    private int runnableID;

    public SpawnProtectionModule(int safeTime) {
        this.safeTime = safeTime;

        this.timers = new HashMap<>();
        this.runnableID = startCountdowns();
    }

    private int startCountdowns() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (!timers.isEmpty()) {
                    for (UUID uuid : timers.keySet()) {
                        if (timers.get(uuid) <= 1) {
                            timers.remove(uuid);
                        } else {
                            timers.put(uuid, timers.get(uuid) - 1);
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    @EventHandler
    public void onRespawn(CustomRespawnEvent event) {
        this.timers.put(event.getPlayer().getUniqueId(), this.safeTime);
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
        Bukkit.getServer().getScheduler().cancelTask(this.runnableID);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCustomDamage(CustomDamageEvent event) {
        if(event.isCancelled()) return;

        if (event.getHurtPlayer() != null) {
            if(timers.containsKey(event.getHurtPlayer().getUniqueId())) {
                event.setCancelled(true);

                if (event.getDamagerPlayer() != null) {
                    F.warning(event.getDamagerPlayer(), "Target is spawn protected");
                }
            }
        }
    }
}
