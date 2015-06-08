package com.minehut.mgm.game;

import com.minehut.mgm.module.mapperModules.destroyableBreakManager.DestroyableBreakManagerModule;
import com.minehut.mgm.module.mapperModules.region.RegionBuilder;
import com.minehut.mgm.util.F;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.peace.PeaceModule;
import com.minehut.mgm.game.coreModules.postgame.PostgameModule;
import com.minehut.mgm.game.coreModules.pregame.PregameModule;
import com.minehut.mgm.game.coreModules.teamManager.TeamManagerModule;
import com.minehut.mgm.game.coreModules.wrapper.WrapperModule;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import org.bukkit.event.Listener;

import java.util.ArrayList;

/**
 * Created by luke on 6/2/15.
 */
public abstract class Game implements Listener {
    private Match match;
    public ArrayList<Module> modules;
    public ArrayList<ModuleBuilder> builders;

    public Game(Match match) {
        this.match = match;
        this.modules = new ArrayList<>();
        this.builders = new ArrayList<>();
    }

    public abstract void defineUsableModules();

    public void addBuilder(ModuleBuilder builder) {
        this.builders.add(builder);
    }

    public void loadModules() {

        /* Build Mapper Modules */
        for (ModuleBuilder moduleBuilder : builders) {

            ArrayList<Module> result = moduleBuilder.load(match);
            if(result != null) {
                for (Module module : result) {
                    if (module != null) {
                        this.modules.add(module);
                    }
                }
            }
        }

        /* Build Core Modules */
        this.modules.add(new PregameModule());
        this.modules.add(new TeamManagerModule());
        this.modules.add(new PeaceModule());
        this.modules.add(new PostgameModule());
        this.modules.add(new WrapperModule());
        this.modules.add(new DestroyableBreakManagerModule());

        /* Register Module Listeners */
        for (Module module : this.modules) {
            MGM.getInstance().getServer().getPluginManager().registerEvents(module, MGM.getInstance());
        }

    }

    public <T extends Module> ArrayList<T> getModules(Class<T> clazz) {
        ArrayList<T> results = new ArrayList<T>();
        for (Module module : this.modules) {
            if (clazz.isInstance(module)) results.add((T) module);
        }

        return results;
    }

    public <T extends Module> Module getModule(Class<T> clazz) {
        for (Module module : this.modules) {
            if (clazz.isInstance(module)) return (T) module;
        }
        return null;
    }

    public void unregisterModules() {
        for (Module module : this.modules) {
            module.unload();
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}
