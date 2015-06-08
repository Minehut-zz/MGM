package com.minehut.mgm.module.mapperModules.region;

import com.minehut.mgm.module.Module;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Created by Luke on 3/19/15.
 */
public class Region implements Module {

	public enum RegionType {
		cube,
		cyl;
	}

	private RegionType regionType;
	private String name;

	/* Cuboid */
	private Location loc1;
	private Location loc2;

	/* Cylinder */
	private Location center;
	private int radius;
	private int height;

	public Region(String name, Location loc1, Location loc2) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;

		this.regionType = RegionType.cube;
	}

	public Region(String name, Location center, int radius, int height) {
		this.name = name;
		this.center = center;
		this.radius = radius;
		this.height = height;

		this.regionType = RegionType.cyl;
	}

	public boolean insideRegion(Location loc) {
		/* Cuboid */
		if(this.regionType == RegionType.cube) {
			if (loc.getWorld() == loc1.getWorld()) {

				double minX = Math.min(loc1.getX(), loc2.getX());
				double maxX = Math.max(loc1.getX(), loc2.getX());

				double minY = Math.min(loc1.getY(), loc2.getY());
				double maxY = Math.max(loc1.getY(), loc2.getY());

				double minZ = Math.min(loc1.getZ(), loc2.getZ());
				double maxZ = Math.max(loc1.getZ(), loc2.getZ());

				if (loc.getX() >= minX && loc.getX() <= maxX &&
						loc.getY() >= minY && loc.getY() <= maxY &&
						loc.getZ() >= minZ && loc.getZ() <= maxZ) {
					return true;
				}
			} else {
				return false;
			}
		} else if (this.regionType == RegionType.cyl) {
			if (distanceHorizontal(center, loc) <= this.radius
					&& loc.getY() >= this.center.getY()
					&& loc.getY() <= this.center.getY() + this.height) {
				return true;
			} else {
				return false;
			}
		}

		/* Unknown type */
		return false;
	}

	public boolean insideRegionAllowBelow(Location loc) {
		if (loc.getWorld() == loc1.getWorld()) {

			double minX = Math.min(loc1.getX(), loc2.getX());
			double maxX = Math.max(loc1.getX(), loc2.getX());

			double maxY = Math.max(loc1.getY(), loc2.getY());

			double minZ = Math.min(loc1.getZ(), loc2.getZ());
			double maxZ = Math.max(loc1.getZ(), loc2.getZ());

			if (loc.getX() >= minX && loc.getX() <= maxX &&
					loc.getY() <= maxY &&
					loc.getZ() >= minZ && loc.getZ() <= maxZ ) {
				return true;
			}
		}
		return false;
	}

	public void remove(Material material) {
		double minX = Math.min(loc1.getX(), loc2.getX());
		double maxX = Math.max(loc1.getX(), loc2.getX());

		double minY = Math.min(loc1.getY(), loc2.getY());
		double maxY = Math.max(loc1.getY(), loc2.getY());

		double minZ = Math.min(loc1.getZ(), loc2.getZ());
		double maxZ = Math.max(loc1.getZ(), loc2.getZ());

		World world = loc1.getWorld();

		for (int x = (int) minX; x <= maxX; x++) {
			for (int y = (int) minY; y <= maxY; y++) {
				for (int z = (int) minZ; z <= maxZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					if (block.getType() == material) {
						block.setType(Material.AIR);
					}
				}
			}
		}
	}

	public static double distanceHorizontal(Location p1, Location p2) {
		double x1 = Math.max(p1.getX(), p2.getX());
		double x2 = Math.min(p1.getX(), p2.getX());

		double z1 = Math.max(p1.getZ(), p2.getZ());
		double z2 = Math.min(p1.getZ(), p2.getZ());

		double distanceX = Math.abs(x1 - x2);
		double distanceZ = Math.abs(z1 - z2);

		return Math.max(distanceX, distanceZ);
	}

	public RegionType getRegionType() {
		return regionType;
	}

	public String getName() {
		return name;
	}

	@Override
	public void unload() {

	}
}
