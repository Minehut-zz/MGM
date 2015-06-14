package com.minehut.mgm.rotation;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by luke on 5/30/15.
 */
public class LoadedMap {

    private final String name, objective;
    private final List<UUID> authors;
    private final List<String> rules;
    private final int maxPlayers;
    private final File folder;

    public LoadedMap(String name, String objective, List<UUID> authors, List<String> rules, int maxPlayers, File folder) {
        this.name = name;
        this.objective = objective;
        this.authors = authors;
        this.rules = rules;
        this.maxPlayers = maxPlayers;
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }

    public List<UUID> getAuthors() {
        return authors;
    }

    public List<String> getRules() {
        return rules;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public File getFolder() {
        return folder;
    }
}
