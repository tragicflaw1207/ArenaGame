package me.ruende.arenagame.game.reward;

import org.bukkit.entity.Player;

public interface RewardStrategy {
    void giveReward(Player player, int round);
}