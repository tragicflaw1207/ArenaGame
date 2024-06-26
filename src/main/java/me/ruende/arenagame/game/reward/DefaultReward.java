package me.ruende.arenagame.game.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DefaultReward implements Reward {
    private final List<RewardStrategy> rewardStrategies;

    public DefaultReward() {
        this.rewardStrategies = new ArrayList<>();
        rewardStrategies.add(new WeaponRewardStrategy());
        rewardStrategies.add(new ArmorRewardStrategy());
        rewardStrategies.add(new HealingItemRewardStrategy());
        rewardStrategies.add(new BuffRewardStrategy());
    }

    @Override
    public void giveStartItems(Player player) {
        player.getInventory().addItem(RewardUtils.createUnbreakableItem(new ItemStack(Material.IRON_SWORD)));
    }

    @Override
    public void giveRoundClearReward(Player player, int round) {
        for (RewardStrategy strategy : rewardStrategies) {
            strategy.giveReward(player, round);
        }
    }

    @Override
    public void clearPlayerInventory(Player player) {
        player.getInventory().clear();
    }
}