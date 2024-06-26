package me.ruende.arenagame.game.reward;

import org.bukkit.entity.Player;

public interface Reward {
    void giveStartItems(Player player);
    void giveRoundClearReward(Player player, int round);
    void clearPlayerInventory(Player player);
}