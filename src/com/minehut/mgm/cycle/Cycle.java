package com.minehut.mgm.cycle;

import com.minehut.mgm.GameHandler;
import com.minehut.mgm.rotation.LoadedMap;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.UUID;

/**
 * Created by luke on 5/30/15.
 */
public class Cycle implements Runnable {
    private final GameHandler handler;
    private LoadedMap map;
    private final UUID uuid;

    public Cycle(GameHandler gameHandler, LoadedMap map, UUID uuid) {
        this.handler = gameHandler;
        this.map = map;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        GenerateMap.copyWorldFromRepository(map.getFolder(), uuid);
        World world = new WorldCreator("matches/" + uuid.toString()).generator(new NullChunkGenerator()).createWorld();
        world.setPVP(true);
        handler.setMatchWorld(world);
        handler.setMatchFile(new File("matches/" + uuid.toString() + "/"));
    }

    public LoadedMap getMap() {
        return map;
    }

    public UUID getUuid() {
        return uuid;
    }
}
