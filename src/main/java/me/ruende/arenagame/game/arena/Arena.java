package me.ruende.arenagame.game.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

import java.util.List;

public class Arena implements Listener {
    private final int size;
    private final Location center;
    private final Location playerSpawn;
    private final List<Location> monsterSpawns;

    public Arena(int size, Location center, Location playerSpawn, List<Location> monsterSpawns) {
        this.size = size;
        this.center = center;
        this.playerSpawn = playerSpawn;
        this.monsterSpawns = monsterSpawns;
    }

    public void createWorldBorder() {
        WorldBorder border = center.getWorld().getWorldBorder();
        border.setCenter(center);
        border.setSize(size);
    }

    public void resetWorldBorder() {
        WorldBorder border = center.getWorld().getWorldBorder();
        border.reset();
    }

    public void createArena() {
        fillArena(Material.POLISHED_DIORITE_SLAB);
    }

    public void clearArena() {
        fillArena(Material.AIR);
    }

    public Location getPlayerSpawn() {
        return playerSpawn;
    }

    public List<Location> getMonsterSpawns() {
        return monsterSpawns;
    }

    private void fillArena(Material material) {
        int halfSize = size / 2;
        for (int x = -halfSize; x < halfSize; x++) {
            for (int z = -halfSize; z < halfSize; z++) {
                Block block = center.getWorld().getBlockAt(center.getBlockX() + x, center.getBlockY() - 1, center.getBlockZ() + z);
                block.setType(material);
            }
        }
    }
}