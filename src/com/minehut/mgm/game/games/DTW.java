package com.minehut.mgm.game.games;

import com.minehut.mgm.game.Game;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.game.coreModules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.module.mapperModules.destroyable.DestroyableBuilder;
import com.minehut.mgm.module.mapperModules.hunger.HungerModuleBuilder;
import com.minehut.mgm.module.mapperModules.kit.KitBuilder;
import com.minehut.mgm.module.mapperModules.region.RegionBuilder;
import com.minehut.mgm.module.mapperModules.spawn.SpawnBuilder;
import com.minehut.mgm.module.mapperModules.team.TeamModuleBuilder;

/**
 * Created by luke on 6/5/15.
 */
public class DTW extends Game {

    public DTW(Match match) {
        super(match);
    }

    @Override
    public void defineUsableModules() {
        super.addBuilder(new RegionBuilder());

        super.addBuilder(new TeamModuleBuilder());

        super.addBuilder(new DestroyableBuilder());

        super.addBuilder(new DamageManagerModuleBuilder());
        super.addBuilder(new KitBuilder());
        super.addBuilder(new HungerModuleBuilder());

        super.addBuilder(new SpawnBuilder());
    }
}
