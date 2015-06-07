package com.minehut.mgm.match;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.Game;
import com.minehut.mgm.game.GameFactory;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleFactory;
import com.minehut.mgm.rotation.LoadedMap;
import com.minehut.mgm.util.DomUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by luke on 5/30/15.
 */
public class Match {
    private final UUID uuid;
    private final LoadedMap loadedMap;
    private Game game;

    private int number;
    private MatchState state;
    private Document document;

    public Match(UUID id, LoadedMap map) {
        this.uuid = id;
        try {
            this.document = DomUtils.parse(new File(map.getFolder() + "/map.xml"));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        this.game = GameFactory.createGame(this.document, this);
        this.state = MatchState.WAITING;
        this.loadedMap = map;
    }

    public Game getGame() {
        return game;
    }

    public Document getDocument() {
        return document;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LoadedMap getLoadedMap() {
        return loadedMap;
    }

    public int getNumber() {
        return number;
    }

    public MatchState getState() {
        return state;
    }

    public boolean isRunning() {
        return getState() == MatchState.PLAYING;
    }
}
