package com.minehut.mgm.game.coreModules.tntTracker;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by luke on 6/9/15.
 */
public class TntTracker implements Module {
    private HashMap<String, UUID> tntPlaced = new HashMap<>();

    public TntTracker() {
    }

    public static UUID getWhoPlaced(Entity tnt) {
        if (tnt.getType().equals(EntityType.PRIMED_TNT)) {
            if (tnt.hasMetadata("source")) {
                return (UUID) tnt.getMetadata("source").get(0).value();
            }
        }
        return null;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.TNT) {
            Location location = event.getBlock().getLocation();
            tntPlaced.put(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
            Location location = event.getEntity().getLocation();
            if (tntPlaced.containsKey(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ())) {
                UUID player = tntPlaced.get(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
                event.getEntity().setMetadata("source", new FixedMetadataValue(MGM.getInstance(), player));
                tntPlaced.remove(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCustomDamage(CustomDamageEvent event) {
        if (event.getTnt() != null) {

                Entity tnt = event.getTnt();
                if (tnt.hasMetadata("source")) {
                    Player realDamager = Bukkit.getServer().getPlayer(getWhoPlaced(tnt));
                    if (realDamager != null) {
                        event.setDamagerEntity(realDamager);
                        event.setDamagerPlayer(realDamager);
                    }
                }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
                for (Block block : event.blockList()) {
                    if (block.getType() == Material.TNT && getWhoPlaced(event.getEntity()) != null) {
                        Location location = block.getLocation();
                        tntPlaced.put(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), getWhoPlaced(event.getEntity()));
                    }
                }
            }
        }
    }
}
