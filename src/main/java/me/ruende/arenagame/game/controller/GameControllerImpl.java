package me.ruende.arenagame.game.controller;

import me.ruende.arenagame.game.arena.Arena;
import me.ruende.arenagame.game.arena.ArenaFactory;
import me.ruende.arenagame.game.difficulty.Difficulty;
import me.ruende.arenagame.game.difficulty.DifficultyAdjuster;
import me.ruende.arenagame.game.player.PlayerData;
import me.ruende.arenagame.game.reward.Reward;
import me.ruende.arenagame.game.round.RoundCoordinator;
import me.ruende.arenagame.game.round.RoundController;
import me.ruende.arenagame.monster.MonsterSpawner;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameControllerImpl implements GameController {
    private final Player player;
    private final Reward reward;
    private final PlayerData playerData;
    private final MonsterSpawner monsterSpawner;
    private final DifficultyAdjuster difficultyAdjuster;
    private final RoundCoordinator roundCoordinator;
    private Arena arena;
    private boolean isStarted;
    private Difficulty currentDifficulty;
    private Location originalLocation;
    private RoundController roundController;

    public GameControllerImpl(Player player, RoundCoordinator roundCoordinator, Reward reward, PlayerData playerData, MonsterSpawner monsterSpawner, DifficultyAdjuster difficultyAdjuster) {
        this.player = player;
        this.roundCoordinator = roundCoordinator;
        this.reward = reward;
        this.playerData = playerData;
        this.monsterSpawner = monsterSpawner;
        this.difficultyAdjuster = difficultyAdjuster;
        this.isStarted = false;
        this.currentDifficulty = difficultyAdjuster.getDifficulty();
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void startGame() {
        currentDifficulty = difficultyAdjuster.getDifficulty();
        isStarted = true;
        reward.giveStartItems(player);

        arena = ArenaFactory.createArena(player, currentDifficulty);
        originalLocation = player.getLocation();
        player.teleport(arena.getPlayerSpawn());
        arena.createWorldBorder();
        arena.createArena();

        roundController = new RoundController(player, arena, reward, playerData, roundCoordinator, monsterSpawner, currentDifficulty);
        roundCoordinator.startRound(arena.getMonsterSpawns());
    }

    @Override
    public void stopGame() {
        if (!isStarted) return;

        if (arena != null) {
            arena.clearArena();
            arena.resetWorldBorder();
            if (originalLocation != null) {
                player.teleport(originalLocation);
            }
        }

        roundCoordinator.resetRound();
        playerData.savePlayerData();
        isStarted = false;
        reward.clearPlayerInventory(player);
        player.sendMessage("게임 종료!");
    }

    @Override
    public boolean isGameActive() {
        return isStarted;
    }

    public RoundController getRoundManager() {
        return roundController;
    }
}
