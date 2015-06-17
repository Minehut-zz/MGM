package com.minehut.mgm.util;

import com.minehut.core.Core;
import com.minehut.core.player.Rank;
import org.bukkit.entity.Player;

/**
 * Created by luke on 6/16/15.
 */
public class CreditUtils {

    public static long getCreditsWithBonuses(Player player, long amount) {
        Rank rank = Core.getInstance().getPlayerInfo(player).getRank();

        if (rank.has(null, Rank.Champ, false)) {
            return (long) (3.0 * amount);
        } else if (rank.has(null, Rank.Legend, false)) {
            return (long) (2.5 * amount);
        } else if (rank.has(null, Rank.Super, false)) {
            return (long) (2.0 * amount);
        } else if (rank.has(null, Rank.Mega, false)) {
            return (long) (1.5 * amount);
        } else {
            return (long) (1.0 * amount);
        }
    }


}
