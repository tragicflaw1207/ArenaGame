package me.ruende.arenagame.game.round;

import me.ruende.arenagame.ArenaGame;
import me.ruende.arenagame.game.arena.Arena;
import me.ruende.arenagame.game.difficulty.Difficulty;
import me.ruende.arenagame.game.player.PlayerData;
import me.ruende.arenagame.game.reward.Reward;
import me.ruende.arenagame.monster.MonsterSpawner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RoundController {
    private final Player player;
    private final Arena arena;
    private final Reward reward;
    private final PlayerData playerData;
    private final RoundCoordinator roundCoordinator;
    private final MonsterSpawner monsterSpawner;
    private final Difficulty currentDifficulty;
    private int maxRoundsThisSession;

    public RoundController(Player player, Arena arena, Reward reward, PlayerData playerData, RoundCoordinator roundCoordinator, MonsterSpawner monsterSpawner, Difficulty currentDifficulty) {
        this.player = player;
        this.arena = arena;
        this.reward = reward;
        this.playerData = playerData;
        this.roundCoordinator = roundCoordinator;
        this.monsterSpawner = monsterSpawner;
        this.currentDifficulty = currentDifficulty;
        this.maxRoundsThisSession = 0;
    }

    public void handleRoundClear() {
        int currentRound = roundCoordinator.getCurrentRound();
        reward.giveRoundClearReward(player, currentRound);
        playerData.incrementRoundsCleared(currentDifficulty);
        roundCoordinator.clearRound();
        roundCoordinator.increaseDifficulty();
        player.sendMessage(currentRound + " 라운드를 클리어 했습니다.");
        maxRoundsThisSession = Math.max(maxRoundsThisSession, currentRound);
        Bukkit.getScheduler().runTaskLater(ArenaGame.getInstance(), () -> roundCoordinator.startRound(arena.getMonsterSpawns()), 200L);
    }

    public void checkRoundClear() {
        if (monsterSpawner.areAllMonstersCleared()) {
            handleRoundClear();
        }
    }
}
