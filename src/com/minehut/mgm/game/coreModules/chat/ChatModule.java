package com.minehut.mgm.game.coreModules.chat;

import com.minehut.commons.common.level.Level;
import com.minehut.commons.common.player.ChatUtil;
import com.minehut.core.Core;
import com.minehut.core.player.PlayerInfo;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.kit.kitPlayer.GamePlayer;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.modules.team.TeamModule;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by luke on 6/8/15.
 */
public class ChatModule implements Module {

    public ChatModule() {
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        PlayerInfo playerInfo = Core.getInstance().getPlayerInfo(player);
        GamePlayer gamePlayer = GameHandler.getGameHandler().getKitManager().getGamePlayerManager().getGamePlayer(player);
        TeamModule team = TeamUtils.getTeamByPlayer(event.getPlayer());
        int level = (int) gamePlayer.getLevel();

        event.setFormat(
                playerInfo.getRank().getTag()
                + Level.getLevelColor(level) + level + " "
                + C.white + event.getPlayer().getName()
                + team.getColor() + " » "
                + C.white + "%2$s");
    }
}
