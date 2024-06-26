package me.ruende.arenagame.game.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorRewardStrategy implements RewardStrategy {
    @Override
    public void giveReward(Player player, int round) {
        ItemStack armor = null;
        switch (round) {
            case 2 -> armor = RewardUtils.createUnbreakableItem(new ItemStack(Material.DIAMOND_HELMET));
            case 3 -> armor = RewardUtils.createUnbreakableItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
            case 4 -> armor = RewardUtils.createUnbreakableItem(new ItemStack(Material.DIAMOND_LEGGINGS));
            case 5 -> armor = RewardUtils.createUnbreakableItem(new ItemStack(Material.DIAMOND_BOOTS));
            case 11 -> RewardUtils.replaceFullArmor(player);
            case 21 -> RewardUtils.giveSkillChestplate(player);
        }
        if (armor != null) {
            player.getInventory().addItem(armor);
        }
    }
}