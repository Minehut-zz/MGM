package com.minehut.mgm.util;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by luke on 6/7/15.
 */
public class LocationUtils {
    public static Location convertWithYaw(World world, String locString) {
        String[] coords = locString.split(",");
        try {
            return new Location(
                    world,
                    Double.parseDouble(Integer.valueOf(coords[0]).intValue() + ".5"),
                    Integer.valueOf(coords[1]).intValue(),
                    Double.parseDouble(Integer.valueOf(coords[2]).intValue() + ".5"),
                    Integer.valueOf(coords[3]).intValue(), 0);
        } catch (Exception e) {
            System.out.println("World Data Read Error: Invalid Location String [" + locString + "]");
        }

        return null;
    }

    public static Location convert(World world, String locString) {
        String[] coords = locString.split(",");
        try {
            return new Location(
                    world,
                    Integer.valueOf(coords[0]).intValue(),
                    Integer.valueOf(coords[1]).intValue(),
                    Integer.valueOf(coords[2]).intValue());
        } catch (Exception e) {
            System.out.println("World Data Read Error: Invalid Location String [" + locString + "]");
        }

        return null;
    }
}
