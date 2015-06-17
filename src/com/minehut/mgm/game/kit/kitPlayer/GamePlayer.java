package com.minehut.mgm.game.kit.kitPlayer;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.util.C;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by luke on 6/13/15.
 */
public class GamePlayer {
    private String name;
    private UUID uuid;
    private ArrayList<Kit> ownedKits;
    private long xp;
    boolean loaded;

    private UUID lastDamagedFrom;

    private Kit selectedKit;

    public GamePlayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.ownedKits = new ArrayList<>();
        this.xp = 0;

        this.lastDamagedFrom = null;

        this.loaded = false;
    }

    public void resetKits() {
        this.ownedKits = new ArrayList<>();
    }

    public boolean hasKit(Kit kit) {
        if (this.ownedKits.isEmpty()) {
            return false;
        }

        if (this.ownedKits.contains(kit)) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ArrayList<Kit> getOwnedKits() {
        return ownedKits;
    }

    public Kit getSelectedKit() {
        if(this.selectedKit == null) {
            return GameHandler.getGameHandler().getKitManager().getDefaultKit();
        }
        return selectedKit;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    public void addOwnedKit(Kit kit) {
        if (!this.ownedKits.contains(kit)) {
            this.ownedKits.add(kit);
        }
    }

    public void addXP(long amount) {
        long oldLevel = this.getLevel();
        this.xp += amount;
        long newLevel = this.getLevel();

        if (newLevel > oldLevel) {
            F.broadcast("Levels", C.aqua + this.name + C.yellow + " has achieved level " + C.green + newLevel);

            Player player = Bukkit.getServer().getPlayer(this.name);
            S.playSound(player, Sound.LEVEL_UP);

            player.sendMessage(C.divider);
            player.sendMessage("");
            player.sendMessage(C.space + "You have leveled up to " + C.green + newLevel);
            player.sendMessage("");
            player.sendMessage(C.divider);
        }
    }

    public long getLevel() {
        if (xp <= 19) {
            return 1;
        } else if (20 <= xp && xp <= 49) {
            return 2;
        } else if (50 <= xp && xp <= 99) {
            return 3;
        } else if (100 <= xp && xp <= 199) {
            return 4;
        } else if (200 <= xp && xp <= 299) {
            return 5;
        } else if (300 <= xp && xp <= 399) {
            return 6;
        } else if (400 <= xp && xp <= 499) {
            return 7;
        } else if (500 <= xp && xp <= 599) {
            return 8;
        } else if (600 <= xp && xp <= 699) {
            return 9;
        } else if (700 <= xp && xp <= 799) {
            return 10;
        } else if (800 <= xp && xp <= 899) {
            return 11;
        } else if (900 <= xp && xp <= 999) {
            return 12;
        } else {
            long base = (xp / 1000) + 12;
            return base;
        }
    }

    public UUID getLastDamagedFrom() {
        return lastDamagedFrom;
    }

    public void setLastDamagedFrom(UUID lastDamagedFrom) {
        this.lastDamagedFrom = lastDamagedFrom;
    }
}
