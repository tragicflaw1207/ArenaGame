package me.ruende.arenagame.game.controller;

import me.ruende.arenagame.game.difficulty.DefaultDifficultyAdjuster;
import me.ruende.arenagame.game.difficulty.Difficulty;
import me.ruende.arenagame.game.difficulty.DifficultyAdjuster;
import me.ruende.arenagame.game.difficulty.DifficultyController;
import me.ruende.arenagame.game.player.PlayerData;
import me.ruende.arenagame.game.reward.DefaultReward;
import me.ruende.arenagame.game.reward.Reward;
import me.ruende.arenagame.game.round.RoundCoordinator;
import me.ruende.arenagame.monster.MonsterSpawner;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameCoordinator {
    private static final Map<UUID, GameController> controllers = new HashMap<>();
    private static final DifficultyAdjuster difficultyAdjuster = new DefaultDifficultyAdjuster();

    public static void setDefaultDifficulty(Difficulty difficulty) {
        for (GameController controller : controllers.values()) {
            if (controller.isGameActive()) {
                return;
            }
        }
        DifficultyController.setDefaultDifficulty(difficulty);
        difficultyAdjuster.setDifficulty(difficulty);
    }

    public static @NotNull GameController getOrCreateGameController(@NotNull Player player) {
        return controllers.computeIfAbsent(player.getUniqueId(), uuid -> createGameController(player));
    }

    public static boolean hasGameController(@NotNull Player player) {
        return controllers.containsKey(player.getUniqueId());
    }

    public static @NotNull Set<GameController> getGameControllers() {
        return Set.copyOf(controllers.values());
    }

    private static @NotNull GameController createGameController(@NotNull Player player) {
        Difficulty difficulty = difficultyAdjuster.getDifficulty();
        MonsterSpawner monsterSpawner = new MonsterSpawner(difficulty);
        RoundCoordinator roundCoordinator = new RoundCoordinator(monsterSpawner);
        Reward reward = new DefaultReward();
        PlayerData playerData = new PlayerData(player);

        return new GameControllerImpl(player, roundCoordinator, reward, playerData, monsterSpawner, difficultyAdjuster);
    }
}
