package me.ruende.arenagame.game.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.ruende.arenagame.game.difficulty.Difficulty;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerData {
    private static final Logger logger = Logger.getLogger(PlayerData.class.getName());
    private final Player player;
    private final Map<Difficulty, PlayerDifficultyData> difficultyDataMap;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATA_FOLDER = "plugins/ArenaGame/playerdata";

    public PlayerData(Player player) {
        this.player = player;
        this.difficultyDataMap = new EnumMap<>(Difficulty.class);
        loadPlayerData();
    }

    public void loadPlayerData() {
        File file = new File(DATA_FOLDER, player.getUniqueId() + ".json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Data loadedData = gson.fromJson(reader, Data.class);
                if (loadedData != null && loadedData.difficultyDataMap != null) {
                    this.difficultyDataMap.putAll(loadedData.difficultyDataMap);
                } else {
                    initializePlayerData();
                }
            } catch (IOException e) {
                handleLoadException(e);
            }
        } else {
            initializePlayerData();
        }
    }

    public void savePlayerData() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists() && !folder.mkdirs()) {
            logger.log(Level.SEVERE, "디텍토리를 만들지 못했습니다. : ", DATA_FOLDER);
            return;
        }
        File file = new File(DATA_FOLDER, player.getUniqueId() + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            Data data = new Data(this.difficultyDataMap);
            gson.toJson(data, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "플레이어 데이터를 저장하지 못했습니다. : " + player.getUniqueId(), e);
        }
    }

    public void incrementRoundsCleared(Difficulty difficulty) {
        PlayerDifficultyData data = difficultyDataMap.get(difficulty);
        data.roundsCleared++;
        updateHighestRound(difficulty, data.roundsCleared);
        updateAverageRound(difficulty);
    }

    private void updateHighestRound(Difficulty difficulty, int round) {
        PlayerDifficultyData data = difficultyDataMap.get(difficulty);
        if (round > data.highestRound) {
            data.highestRound = round;
            savePlayerData();
        }
    }

    private void updateAverageRound(Difficulty difficulty) {
        PlayerDifficultyData data = difficultyDataMap.get(difficulty);
        data.averageRound = (data.averageRound * (data.roundsCleared - 1) + data.roundsCleared) / data.roundsCleared;
        savePlayerData();
    }

    private void initializePlayerData() {
        for (Difficulty difficulty : Difficulty.values()) {
            this.difficultyDataMap.put(difficulty, new PlayerDifficultyData());
        }
        savePlayerData();
    }

    private void handleLoadException(Exception e) {
        initializePlayerData();
        logger.log(Level.SEVERE, "플레이어 데이터를 불러오지 못했습니다. : " + player.getUniqueId(), e);
    }

    private record Data(Map<Difficulty, PlayerDifficultyData> difficultyDataMap) {}

    private static class PlayerDifficultyData {
        private transient int roundsCleared;

        @SerializedName("최고 라운드")
        private int highestRound;

        @SerializedName("평균 라운드")
        private float averageRound;

        public PlayerDifficultyData() {
            this.roundsCleared = 0;
            this.highestRound = 0;
            this.averageRound = 0.0f;
        }
    }
}
