package com.minehut.mgm.game.kit.kitPlayer;

import com.minehut.mgm.game.kit.Kit;

import java.util.ArrayList;

/**
 * Created by luke on 6/13/15.
 */
public class KitPlayer {
    private String name;
    private String uuid;
    private ArrayList<Kit> ownedKits;

    private Kit selectedKit;

    public KitPlayer(String name, String uuid, ArrayList<Kit> ownedKits) {
        this.name = name;
        this.uuid = uuid;
        this.ownedKits = ownedKits;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public ArrayList<Kit> getOwnedKits() {
        return ownedKits;
    }

    public Kit getSelectedKit() {
        return selectedKit;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;
    }

    public void addOwnedKit(Kit kit) {
        if (!this.ownedKits.contains(kit)) {
            this.ownedKits.add(kit);
        }
    }
}
