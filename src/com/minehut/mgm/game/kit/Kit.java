package com.minehut.mgm.game.kit;

import com.minehut.commons.common.items.ArmorType;
import com.minehut.core.player.Rank;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by luke on 6/12/15.
 */
public abstract class Kit implements Module {
    public HashMap<ItemStack, Integer> items;
    public ArrayList<Ability> abilities;
    public String name;
    public Material icon;
    public long price;
    public Rank rank;

    public double maxHealth;

    public Kit(String name, Material icon, long price) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.maxHealth = 20;
        this.rank = Rank.regular;

        this.items = new HashMap<>();
        this.abilities = new ArrayList<>();
    }

    public Kit(String name, Material icon, long price, Rank rank) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.maxHealth = 20;
        this.rank = rank;

        this.items = new HashMap<>();
        this.abilities = new ArrayList<>();
    }

    public void addAbility(Ability ability) {
        this.addItem(ability.getItemStack());
        this.abilities.add(ability);
    }

    public void setAbility(Ability ability, int slot) {
        this.setItem(ability.getItemStack(), slot);
        this.abilities.add(ability);
    }

    public void addItem(ItemStack itemStack) {
        if (this.items.isEmpty()) {
            this.items.put(itemStack, 0);
            return;
        }

        for (int i = 0; i < 35; i++) {
            if (!this.items.containsValue(i)) {
                this.items.put(itemStack, i);
                return;
            }
        }
    }

    public void addItem(Material material) {
        ItemStack itemStack = new ItemStack(material);
        this.addItem(itemStack);
    }

    public void addItem(Material material, int amount) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(amount);
        this.addItem(itemStack);
    }

    public void setItem(ItemStack itemStack, int slot) {
        this.items.put(itemStack, slot);
    }

    public void apply(Player player, Color color) {
        player.getInventory().clear();
        player.setMaxHealth(this.maxHealth);
        player.setHealth(player.getMaxHealth());
        for (ItemStack item : this.items.keySet()) {
            if (ArmorType.getArmorType(item) == ArmorType.helmet) {

                if (item.getType() == Material.LEATHER_HELMET) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }

                player.getInventory().setHelmet(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.chestplate) {

                if (item.getType() == Material.LEATHER_CHESTPLATE) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }

                player.getInventory().setChestplate(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.leggings) {

                if (item.getType() == Material.LEATHER_LEGGINGS) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }

                player.getInventory().setLeggings(item);
            } else if (ArmorType.getArmorType(item) == ArmorType.boots) {

                if (item.getType() == Material.LEATHER_BOOTS) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }

                player.getInventory().setBoots(item);
            } else {
                player.getInventory().setItem(items.get(item), item);
            }
        }

        this.onApply(player);
    }

    public abstract void onApply(Player player);

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);

        if(!this.abilities.isEmpty()) {
            for (Ability ability : this.abilities) {
                ability.unload();
            }
        }

        this.extraUnload();
    }

    public boolean usingThisKit(Player player) {
        return GameHandler.getGameHandler().getKitManager().getGamePlayerManager().getGamePlayer(player).getSelectedKit() == this;
    }

    public abstract void extraUnload();

    public HashMap<ItemStack, Integer> getItems() {
        return items;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public long getPrice() {
        return price;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Rank getRank() {
        return rank;
    }
}
