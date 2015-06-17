package com.minehut.mgm.game;

import com.minehut.commons.common.chat.C;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.game.coreModules.chat.ChatModule;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.game.coreModules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.game.coreModules.respawn.RespawnModule;
import com.minehut.mgm.game.coreModules.spectator.SpectatorModule;
import com.minehut.mgm.game.coreModules.tab.TabModule;
import com.minehut.mgm.game.coreModules.teamPicker.TeamPickerModule;
import com.minehut.mgm.game.coreModules.timer.GameTimer;
import com.minehut.mgm.game.coreModules.tntTracker.TntTracker;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.module.modules.build.BuildModuleBuilder;
import com.minehut.mgm.module.modules.destroyable.DestroyableBuilder;
import com.minehut.mgm.module.modules.destroyable.DestroyableBreakManagerModule;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.peace.PeaceModule;
import com.minehut.mgm.game.coreModules.postgame.PostgameModule;
import com.minehut.mgm.game.coreModules.pregame.PregameModule;
import com.minehut.mgm.game.coreModules.teamManager.TeamManagerModule;
import com.minehut.mgm.game.coreModules.wrapper.WrapperModule;
import com.minehut.mgm.match.Match;
import com.minehut.mgm.module.Module;
import com.minehut.mgm.module.ModuleBuilder;
import com.minehut.mgm.module.modules.grenade.GrenadeModuleBuilder;
import com.minehut.mgm.module.modules.hunger.HungerModuleBuilder;
import com.minehut.mgm.module.modules.instakill.InstakillModuleBuilder;
import com.minehut.mgm.module.modules.itemdrop.ItemDropModuleBuilder;
import com.minehut.mgm.module.modules.noLeave.NoLeaveModuleBuilder;
import com.minehut.mgm.module.modules.noModify.NoModifyModuleBuilder;
import com.minehut.mgm.module.modules.region.RegionBuilder;
import com.minehut.mgm.module.modules.safezone.SafezoneModuleBuilder;
import com.minehut.mgm.module.modules.score.ScoreModuleBuilder;
import com.minehut.mgm.module.modules.spawn.SpawnBuilder;
import com.minehut.mgm.module.modules.spawnProtection.SpawnProtectionModuleBuilder;
import com.minehut.mgm.module.modules.team.TeamModuleBuilder;
import com.minehut.mgm.module.modules.tntProtection.TntProtectionModuleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 6/2/15.
 */
public abstract class Game implements Listener {
    private Match match;
    public ArrayList<Module> modules;
    public ArrayList<ModuleBuilder> builders;
    public String name;
    public List<Kit> kits;

    public Game(Match match, String name, List<Kit> kits) {
        this.match = match;
        this.name = name;
        this.kits = kits;
        this.modules = new ArrayList<>();
        this.builders = new ArrayList<>();
    }

    public abstract void unload();

    public abstract void postModulesLoaded();

    public void addBuilder(ModuleBuilder builder) {
        this.builders.add(builder);
    }

    public void loadModules() {

        this.defineOptionalModules();
        this.defineRequiredModules();

        /* Register Module Listeners */
        for (Module module : this.modules) {
            MGM.getInstance().getServer().getPluginManager().registerEvents(module, MGM.getInstance());
        }

        /* Register Kit Listeners */
        for (Kit kit : this.kits) {
            MGM.getInstance().getServer().getPluginManager().registerEvents(kit, MGM.getInstance());
        }

        /* Register Game Listener */
        MGM.getInstance().getServer().getPluginManager().registerEvents(this, MGM.getInstance());

        this.postModulesLoaded();
    }

    public void defineOptionalModules() {
        this.addBuilder(new RegionBuilder());
        this.addBuilder(new TeamModuleBuilder());
        this.addBuilder(new DestroyableBuilder());
        this.addBuilder(new DamageManagerModuleBuilder());
        this.addBuilder(new HungerModuleBuilder());
        this.addBuilder(new SpawnBuilder());
        this.addBuilder(new InstakillModuleBuilder());
        this.addBuilder(new SafezoneModuleBuilder());
        this.addBuilder(new ItemDropModuleBuilder());
        this.addBuilder(new BuildModuleBuilder());
        this.addBuilder(new GrenadeModuleBuilder());
        this.addBuilder(new TntProtectionModuleBuilder());
        this.addBuilder(new ScoreModuleBuilder());
        this.addBuilder(new NoLeaveModuleBuilder());
        this.addBuilder(new NoModifyModuleBuilder());
        this.addBuilder(new SpawnProtectionModuleBuilder());

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
    }

    public void defineRequiredModules() {
        /* Required Modules */
        this.modules.add(new PregameModule());
        this.modules.add(new TeamManagerModule());
        this.modules.add(new PeaceModule());
        this.modules.add(new PostgameModule());
        this.modules.add(new WrapperModule());
        this.modules.add(new GameTimer());
        this.modules.add(new TabModule());
        this.modules.add(new RespawnModule());
        this.modules.add(new ChatModule());
        this.modules.add(new SpectatorModule());
        this.modules.add(new TntTracker());
        this.modules.add(new TeamPickerModule());
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
        for (Kit kit : this.kits) {
            kit.unload();
        }
        this.unload();
    }

    @EventHandler
    public void onVoidDamage(CustomDamageEvent event) {
        if(!GameHandler.getHandler().getMatch().isRunning()) return;

        if (event.getEventCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setDamage(999);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public List<Kit> getKits() {
        return kits;
    }
}
