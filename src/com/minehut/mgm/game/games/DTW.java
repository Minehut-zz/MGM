package com.minehut.mgm.game.games;

import com.minehut.mgm.game.Game;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.game.coreModules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.module.modules.destroyable.DestroyableBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModuleBuilder;
import com.minehut.mgm.module.modules.kit.KitBuilder;
import com.minehut.mgm.module.modules.region.RegionBuilder;
import com.minehut.mgm.module.modules.spawn.SpawnBuilder;
import com.minehut.mgm.module.modules.team.TeamModuleBuilder;

/**
 * Created by luke on 6/5/15.
 */
public class DTW extends Game {

    public DTW(Match match) {
        super(match, "DTW");
    }
}
