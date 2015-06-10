package com.minehut.mgm.module;

import com.minehut.mgm.match.Match;
import com.minehut.mgm.game.coreModules.damage.DamageManagerModuleBuilder;
import com.minehut.mgm.module.modules.kit.KitBuilder;
import com.minehut.mgm.module.modules.team.TeamModuleBuilder;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by luke on 5/30/15.
 */
public class ModuleFactory {

    @SuppressWarnings("unchecked")
    public static void build(Match match) {
        ArrayList<Module> modules = new ArrayList<>();

        for (Class clazz : builderClasses) {
            try {
                ModuleBuilder builder = (ModuleBuilder) clazz.getConstructor().newInstance();
                for (Module module : builder.load(match)) {
                    modules.add(module);
                }
            } catch (NoSuchMethodException e) {
                Bukkit.getLogger().log(Level.SEVERE, clazz.getName() + " is an invalid ModuleBuilder.");
                e.printStackTrace();
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

//        for (ModuleBuilder moduleBuilder : builderClasses) {
//            ArrayList<Module> modules = moduleBuilder.load(match);
//            for (Module module : modules) {
//                if (module != null) {
//                    match.addModule(module);
//                }
//            }
//        }
    }

    private static Class[] builderClasses = {
            TeamModuleBuilder.class,
            DamageManagerModuleBuilder.class,
            KitBuilder.class
    };
}
