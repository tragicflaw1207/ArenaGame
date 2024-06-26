package me.ruende.arenagame.game.arena;

import me.ruende.arenagame.game.difficulty.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaFactory {

    public static Arena createArena(Player player, Difficulty difficulty) {
        int size = difficulty.getArenaSize();
        World world = player.getWorld();
        Location arenaCenter = new Location(world, 0, 100.5, 0);
        Location playerSpawn = new Location(world, 0, 100.5, 0);
        List<Location> monsterSpawns = createMonsterSpawns(world, difficulty);

        return new Arena(size, arenaCenter, playerSpawn, monsterSpawns);
    }

    private static List<Location> createMonsterSpawns(World world, Difficulty difficulty) {
        List<Location> monsterSpawns = new ArrayList<>();
        int spawnPoints = difficulty.getSpawnPoints();

        switch (spawnPoints) {
            case 1 -> monsterSpawns.add(new Location(world, 0, 100.5, 5));
            case 2 -> {
                monsterSpawns.add(new Location(world, -5, 100.5, 5));
                monsterSpawns.add(new Location(world, 5, 100.5, 5));
            }
            case 3 -> {
                monsterSpawns.add(new Location(world, -5, 100.5, 5));
                monsterSpawns.add(new Location(world, 0, 100.5, 5));
                monsterSpawns.add(new Location(world, 5, 100.5, 5));
            }
        }

        return monsterSpawns;
    }
}
