package me.ruende.arenagame.game.difficulty;

public class DefaultDifficultyAdjuster implements DifficultyAdjuster {

    @Override
    public Difficulty getDifficulty() {
        return DifficultyController.getDefaultDifficulty();
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        DifficultyController.setDefaultDifficulty(difficulty);
    }
}