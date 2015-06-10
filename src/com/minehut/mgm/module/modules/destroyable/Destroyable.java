package com.minehut.mgm.module.modules.destroyable;

import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.region.Region;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/7/15.
 */
public class Destroyable implements Module {
    private TeamModule team;
    private String name;
    private int amount;
    private int left;
    private Region region;
    private Material material;

    public Destroyable(TeamModule team, String name, int amount, Region region, Material material) {
        this.team = team;
        this.name = name;
        this.amount = amount;
        this.left = amount;
        this.region = region;
        this.material = material;
    }

    public enum DestroyableBreakAttempt {
        SUCCESSFUL,
        OWNS,
        OUTSIDE
    }

    public DestroyableBreakAttempt getBreakAttempt(Player player, Block block) {
        if (this.region.insideRegion(block.getLocation())) {
            if(block.getType() == this.material) {
                if (TeamUtils.getTeamByPlayer(player) != team) {
                    return DestroyableBreakAttempt.SUCCESSFUL;
                } else {
                    return DestroyableBreakAttempt.OWNS;
                }
            }
        }
        return DestroyableBreakAttempt.OUTSIDE;
    }

    public boolean decrementAndCheckCompleted() {
        this.amount--;
        if (this.amount > 0) {
            return false;
        }
        return true;
    }

    public TeamModule getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getLeft() {
        return left;
    }

    public Region getRegion() {
        return region;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void unload() {

    }
}
