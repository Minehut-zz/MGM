package com.minehut.mgm.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

/**
 * Created by luke on 6/1/15.
 */
public class PlayerUtils {
    public static void resetPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        for (PotionEffect effect : player.getActivePotionEffects()) {
            try {
                player.removePotionEffect(effect.getType());
            } catch (NullPointerException e) {
            }
        }
        player.setTotalExperience(0);
        player.setExp(0);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
        player.setFireTicks(0);
        player.setVelocity(new Vector());
        player.setFlying(false);
        player.setAllowFlight(false);

        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            if(!player1.canSee(player)) player1.showPlayer(player);
        }
    }

    public static void setTab(Player player, String header, String footer) {
        PacketContainer pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        pc.getChatComponents().write(0, WrappedChatComponent.fromText(header))
                .write(1, WrappedChatComponent.fromText(footer));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, pc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
