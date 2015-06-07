package com.minehut.mgm.game.games;

import com.minehut.mgm.game.Game;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.modules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModuleBuilder;
import com.minehut.mgm.module.modules.kit.KitBuilder;
import com.minehut.mgm.module.modules.team.TeamModuleBuilder;
import com.minehut.mgm.module.modules.teamManager.TeamManagerModuleBuilder;

/**
 * Created by luke on 6/5/15.
 */
public class DTW extends Game {

    public DTW(Match match) {
        super(match);
    }

    @Override
    public void defineUsableModules() {
        super.addBuilder(new TeamModuleBuilder());

        super.addBuilder(new DamageManagerModuleBuilder());
        super.addBuilder(new KitBuilder());
        super.addBuilder(new TeamManagerModuleBuilder());
        super.addBuilder(new HungerModuleBuilder());
    }
}
