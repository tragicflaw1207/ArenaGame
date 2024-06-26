package me.ruende.arenagame.game.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WeaponRewardStrategy implements RewardStrategy {
    @Override
    public void giveReward(Player player, int round) {
        ItemStack weapon = null;
        switch (round) {
            case 1 -> weapon = RewardUtils.createUnbreakableItem(new ItemStack(Material.DIAMOND_SWORD));
            case 6 -> weapon = RewardUtils.createUnbreakableItem(new ItemStack(Material.NETHERITE_SWORD));
        }
        if (weapon != null) {
            player.getInventory().setItemInMainHand(weapon);
        }
    }
}