package com.minehut.mgm.game.kit;

import com.minehut.mgm.MGM;
import com.minehut.mgm.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by luke on 6/14/15.
 */
public abstract class Ability implements Module {
    private HashMap<UUID, Integer> cooldowns;
    private int runnableID;
    private ItemStack itemStack;
    private String name;
    private int cooldown;

    public Ability(String name, ItemStack itemStack, int cooldown) {
        this.name = name;
        this.itemStack = itemStack;
        this.cooldown = cooldown;

        this.cooldowns = new HashMap<>();
        this.runnableID = cooldownRunnable();
    }

    private int cooldownRunnable() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(cooldowns.isEmpty()) return;

                ArrayList<UUID> remove = new ArrayList<UUID>();
                for (UUID uuid : cooldowns.keySet()) {
                    cooldowns.put(uuid, cooldowns.get(uuid) - 1);

                    if (cooldowns.get(uuid) <= 0) {
                        remove.add(uuid);
                    }
                }

                for (UUID uuid : remove) {
                    cooldowns.remove(uuid);
                    offCooldown(Bukkit.getServer().getPlayer(uuid));
                }
            }
        }, 20L, 20L);
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand() == this.itemStack) {

            /* Air Clicks */
            if (event.getAction() == Action.LEFT_CLICK_AIR) {
                this.onLeftClickAir(event.getPlayer());
            }
            else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                this.onRightClickAir(event.getPlayer());
            }

            /* Block Clicks */
            else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                this.onLeftClickBlock(event.getPlayer(), event.getClickedBlock());
            }
            else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                this.onRightClickBlock(event.getPlayer(), event.getClickedBlock());
            }
        }
    }

    @EventHandler
    public void onInteractWithEntity(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand() == this.itemStack) {
            if (event.getRightClicked() instanceof Player) {
                this.onRightClickPlayer(event.getPlayer(), (Player) event.getRightClicked());
            }
        }
    }

    public abstract void offCooldown(Player player);

    public abstract void onLeftClickAir(Player player);

    public abstract void onRightClickAir(Player player);

    public abstract void onLeftClickBlock(Player player, Block block);

    public abstract void onRightClickBlock(Player player, Block block);

    public abstract void onRightClickPlayer(Player player, Player target);

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void putOnCooldown(Player player) {
        this.cooldowns.put(player.getUniqueId(), this.cooldown);
    }

    public boolean isOffCooldown(Player player) {
        if (this.cooldowns.containsValue(player.getUniqueId())) {
            return false;
        }
        return true;
    }

    public int getCooldown(Player player) {
        if (!this.isOffCooldown(player)) {
           return this.cooldowns.get(player.getUniqueId());
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
        Bukkit.getServer().getScheduler().cancelTask(this.runnableID);

        this.extraUnload();
    }

    public abstract void extraUnload();
}
