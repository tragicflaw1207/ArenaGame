package me.ruende.arenagame.game.reward;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HealingItemRewardStrategy implements RewardStrategy {
    @Override
    public void giveReward(Player player, int round) {
        ItemStack healing = null;
        switch (round) {
            case 1, 2, 3, 4, 5 -> healing = new ItemStack(Material.COOKED_BEEF, 5);
            case 16 -> {
                MythicItem mythicItem = MythicBukkit.inst().getItemManager().getItem("healing_item").orElse(null);
                if (mythicItem != null) {
                    healing = BukkitAdapter.adapt(mythicItem.generateItemStack(1));
                }
            }
        }
        if (healing != null) {
            player.getInventory().addItem(healing);
        }
    }
}