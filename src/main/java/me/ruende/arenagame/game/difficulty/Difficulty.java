package me.ruende.arenagame.game.difficulty;

public enum Difficulty {
    EASY(30, 1, "쉬움"),
    NORMAL(40, 2, "보통"),
    HARD(50, 3, "어려움");

    private final int arenaSize;
    private final int spawnPoints;
    private final String koreanName;

    Difficulty(int arenaSize, int spawnPoints, String koreanName) {
        this.arenaSize = arenaSize;
        this.spawnPoints = spawnPoints;
        this.koreanName = koreanName;
    }

    public int getArenaSize() {
        return arenaSize;
    }

    public int getSpawnPoints() {
        return spawnPoints;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Difficulty fromString(String name) {
        return switch (name.toLowerCase()) {
            case "easy" -> EASY;
            case "normal" -> NORMAL;
            case "hard" -> HARD;
            default -> null;
        };
    }
}
