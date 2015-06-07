package com.minehut.mgm.game;

import com.minehut.commons.common.chat.F;
import com.minehut.mgm.MGM;
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
        /* Build Modules */
        for (ModuleBuilder moduleBuilder : builders) {
            for (Module module : moduleBuilder.load(match)) {
                if (module != null) {
                    this.modules.add(module);
                    F.log("Added module");
                }
            }
        }

        /* Register Module Listeners */
        for (Module module : this.modules) {
            MGM.getInstance().getServer().getPluginManager().registerEvents(module, MGM.getInstance());
            F.log("registered listener");
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
