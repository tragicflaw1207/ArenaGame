package me.ruende.arenagame.game.difficulty;

public class DifficultyController {
    private static Difficulty defaultDifficulty = Difficulty.EASY;

    public static Difficulty getDefaultDifficulty() {
        return defaultDifficulty;
    }

    public static void setDefaultDifficulty(Difficulty difficulty) {
        defaultDifficulty = difficulty;
    }
}