package me.ruende.arenagame.game.round;

import me.ruende.arenagame.monster.MonsterSpawner;
import org.bukkit.Location;

import java.util.List;

public class RoundCoordinator {
    private final Round round;
    private final MonsterSpawner monsterSpawner;

    public RoundCoordinator(MonsterSpawner monsterSpawner) {
        this.round = new Round();
        this.monsterSpawner = monsterSpawner;
    }

    public void startRound(List<Location> spawnLocations) {
        monsterSpawner.spawnMonsters(round.getCurrentRound(), spawnLocations);
    }

    public void increaseDifficulty() {
        round.nextRound();
        monsterSpawner.adjustMonsterDifficulty(round.getCurrentRound());
    }

    public void clearRound() {
        monsterSpawner.removeMonsters();
    }

    public int getCurrentRound() {
        return round.getCurrentRound();
    }

    public void resetRound() {
        this.round.reset();
        monsterSpawner.removeMonsters();
    }
}
