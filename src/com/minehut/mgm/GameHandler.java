package com.minehut.mgm;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.cycle.Cycle;
import com.minehut.mgm.event.CycleCompleteEvent;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.rotation.Rotation;
import com.minehut.mgm.rotation.exception.RotationLoadException;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by luke on 5/30/15.
 */
public class GameHandler {
    private static GameHandler handler;
//    private final ModuleFactory moduleFactory;
    private Rotation rotation;
    private WeakReference<World> matchWorld;
    private Match match;
    private Cycle cycle;
    private File matchFile;
    private boolean globalMute;

    public GameHandler() throws RotationLoadException {
        handler = this;
//        this.moduleFactory = new ModuleFactory();
        rotation = new Rotation();
        cycle = new Cycle(this, rotation.getNext(), UUID.randomUUID());
        Bukkit.getScheduler().scheduleSyncDelayedTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                cycleAndMakeMatch();
            }
        });

    }

    public void cycleAndMakeMatch() {
        rotation.move();
        World oldMatchWorld = matchWorld == null ? null: matchWorld.get();
        cycle.run();
        if (match != null) match.getGame().unregisterModules();

        this.match = new Match(cycle.getUuid(), cycle.getMap());
        this.match.getGame().defineUsableModules();
        this.match.getGame().loadModules();

        MGM.getInstance().getLogger().info(this.match.getGame().getModules().size() + " Modules loaded.");
        Bukkit.getServer().getPluginManager().callEvent(new CycleCompleteEvent(match));

        /* Prep for next cycle */
        cycle = new Cycle(this, rotation.getNext(), UUID.randomUUID());

        /* Discard old map */
        if(oldMatchWorld != null) {
            Bukkit.getServer().unloadWorld(oldMatchWorld, false);
            try {
                if(oldMatchWorld.getWorldFolder().exists()) {
                    FileUtils.deleteDirectory(oldMatchWorld.getWorldFolder());
                }
            } catch (Exception e) {
                F.log("failed to delete old world file");
                e.printStackTrace();
            }
        }


    }

    public static GameHandler getHandler() {
        return handler;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public boolean isGlobalMute() {
        return globalMute;
    }

    public Match getMatch() {
        return match;
    }

    public World getMatchWorld() {
        return matchWorld.get();
    }

    public void setMatchWorld(World world) {
        matchWorld = new WeakReference<>(world);
    }

    public File getMatchFile() {
        return matchFile;
    }

    public void setMatchFile(File file) {
        matchFile = file;
    }

    public static GameHandler getGameHandler() {
        return handler;
    }
}
