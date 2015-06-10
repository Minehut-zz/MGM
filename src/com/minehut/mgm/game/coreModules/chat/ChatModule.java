package com.minehut.mgm.game.coreModules.chat;

import com.minehut.core.Core;
import com.minehut.core.player.PlayerInfo;
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
        TeamModule team = TeamUtils.getTeamByPlayer(event.getPlayer());

        event.setFormat(playerInfo.getRank().getTag()
                + C.white + event.getPlayer().getName()
                + team.getColor() + " » "
                + C.white + "%2$s");
    }
}
