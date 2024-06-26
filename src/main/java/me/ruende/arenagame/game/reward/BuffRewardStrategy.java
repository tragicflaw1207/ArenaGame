package me.ruende.arenagame.game.reward;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BuffRewardStrategy implements RewardStrategy {
    @Override
    public void giveReward(Player player, int round) {
        switch (round) {
            case 11 -> RewardUtils.giveWeaponBuff(player, Enchantment.DAMAGE_ALL, 5);
            case 16 -> {
                RewardUtils.giveWeaponBuff(player, Enchantment.SWEEPING_EDGE, 3);
                RewardUtils.giveArmorBuff(player, Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                RewardUtils.giveArmorBuff(player, Enchantment.THORNS, 3);
                RewardUtils.givePotionEffect(player, PotionEffectType.HEALTH_BOOST, 1);
            }
            case 21 -> {
                RewardUtils.givePotionEffect(player, PotionEffectType.REGENERATION, 3);
                RewardUtils.giveWeaponBuff(player, Enchantment.SWEEPING_EDGE, 5);
                RewardUtils.giveArmorBuff(player, Enchantment.THORNS, 7);
                RewardUtils.givePotionEffect(player, PotionEffectType.HEALTH_BOOST, 2);
            }
            case 26 -> {
                RewardUtils.givePotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE, 1);
                RewardUtils.giveWeaponBuff(player, Enchantment.SWEEPING_EDGE, 10);
                RewardUtils.giveArmorBuff(player, Enchantment.THORNS, 14);
                RewardUtils.givePotionEffect(player, PotionEffectType.HEALTH_BOOST, 3);
            }
            case 31 -> {
                RewardUtils.givePotionEffect(player, PotionEffectType.HEALTH_BOOST, 4);
                RewardUtils.givePotionEffect(player, PotionEffectType.DAMAGE_RESISTANCE, 2);
            }
            case 36 -> {
                RewardUtils.givePotionEffect(player, PotionEffectType.REGENERATION, 6);
                RewardUtils.givePotionEffect(player, PotionEffectType.SATURATION, 1);
                RewardUtils.giveArmorBuff(player, Enchantment.THORNS, 35);
            }
        }
    }
}