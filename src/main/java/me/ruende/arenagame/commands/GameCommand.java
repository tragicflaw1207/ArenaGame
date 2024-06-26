package me.ruende.arenagame.commands;

import com.google.common.collect.Lists;
import me.ruende.arenagame.game.controller.GameController;
import me.ruende.arenagame.game.controller.GameCoordinator;
import me.ruende.arenagame.game.difficulty.Difficulty;
import me.ruende.arenagame.game.difficulty.DifficultyController;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameCommand extends BukkitCommand {
    public GameCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("사용법: /game <start|stop|set>");
            return false;
        }

        String subCommand = args[0].toLowerCase();
        GameController controller = GameCoordinator.getOrCreateGameController(player);

        return switch (subCommand) {
            case "start" -> handleStartCommand(player, controller);
            case "stop" -> handleStopCommand(player, controller);
            case "set" -> handleSetCommand(player, args);
            default -> {
                player.sendMessage("알 수 없는 명령어입니다.");
                yield false;
            }
        };
    }

    private boolean handleStartCommand(Player player, GameController controller) {
        if (!controller.isStarted()) {
            controller.startGame();
            player.sendMessage("게임을 시작합니다.");
            return true;
        } else {
            player.sendMessage("게임이 이미 시작되었습니다.");
            return false;
        }
    }

    private boolean handleStopCommand(Player player, GameController controller) {
        if (controller.isStarted()) {
            controller.stopGame();
            player.sendMessage("게임이 종료되었습니다.");
            return true;
        } else {
            player.sendMessage("게임이 시작되지 않았습니다.");
            return false;
        }
    }

    private boolean handleSetCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("사용법: /game set <easy|normal|hard>");
            return false;
        }

        Difficulty difficulty = Difficulty.fromString(args[1]);
        if (difficulty == null) {
            player.sendMessage("잘못된 난이도입니다. 사용법: /game set <easy|normal|hard>");
            return false;
        }

        GameCoordinator.setDefaultDifficulty(difficulty);
        if (DifficultyController.getDefaultDifficulty() == difficulty) {
            player.sendMessage("난이도가 " + difficulty.getKoreanName() + "로 설정되었습니다.");
            return true;
        } else {
            player.sendMessage("게임의 진행 중에는 난이도를 변경할 수 없습니다.");
            return false;
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Lists.newArrayList("start", "stop", "set");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return Lists.newArrayList("easy", "normal", "hard");
        }
        return Lists.newArrayList();
    }
}
