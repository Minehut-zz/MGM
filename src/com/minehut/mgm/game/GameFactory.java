package com.minehut.mgm.game;

import com.minehut.mgm.util.F;
import com.minehut.mgm.game.games.DTW;
import com.minehut.mgm.match.Match;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Created by luke on 6/2/15.
 */
public class GameFactory {
    public static Game createGame(Document document, Match match) {
        Element gameNode = document.getRootElement().getChild("game");
        String gameName = gameNode.getText();
        return getGame(gameName, match);
    }

    public static Game getGame(String s, Match match) {
        if (s.equalsIgnoreCase("dtw")) {
            return new DTW(match);
        }

        F.log("Couldn't find game: " + s);
        return null;
    }
}
