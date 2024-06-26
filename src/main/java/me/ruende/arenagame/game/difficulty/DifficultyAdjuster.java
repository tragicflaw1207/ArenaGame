package me.ruende.arenagame.game.difficulty;

public interface DifficultyAdjuster {
    Difficulty getDifficulty();
    void setDifficulty(Difficulty difficulty);
}