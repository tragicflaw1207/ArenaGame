package me.ruende.arenagame.game.reward;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RewardUtils {
    public static ItemStack createUnbreakableItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static void replaceFullArmor(Player player) {
        player.getInventory().setHelmet(createUnbreakableItem(new ItemStack(Material.NETHERITE_HELMET)));
        player.getInventory().setChestplate(createUnbreakableItem(new ItemStack(Material.NETHERITE_CHESTPLATE)));
        player.getInventory().setLeggings(createUnbreakableItem(new ItemStack(Material.NETHERITE_LEGGINGS)));
        player.getInventory().setBoots(createUnbreakableItem(new ItemStack(Material.NETHERITE_BOOTS)));
    }

    public static void giveSkillChestplate(Player player) {
        MythicItem mythicItem = MythicBukkit.inst().getItemManager().getItem("skill_chestplate").orElse(null);
        if (mythicItem != null) {
            ItemStack skillChestplate = BukkitAdapter.adapt(mythicItem.generateItemStack(1));
            ItemMeta meta = skillChestplate.getItemMeta();
            if (meta != null) {
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                meta.addEnchant(Enchantment.THORNS, 3, true);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                skillChestplate.setItemMeta(meta);
            }
            player.getInventory().setChestplate(skillChestplate);
        }
    }

    public static void giveWeaponBuff(Player player, Enchantment enchantment, int level) {
        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (weapon.getType() != Material.AIR) {
            ItemMeta meta = weapon.getItemMeta();
            if (meta != null) {
                meta.addEnchant(enchantment, level, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                weapon.setItemMeta(meta);
            }
        }
    }

    public static void giveArmorBuff(Player player, Enchantment enchantment, int level) {
        ItemStack[] armorItems = player.getInventory().getArmorContents();
        for (ItemStack armorItem : armorItems) {
            if (armorItem != null && armorItem.getType() != Material.AIR) {
                ItemMeta meta = armorItem.getItemMeta();
                if (meta != null) {
                    meta.addEnchant(enchantment, level, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    armorItem.setItemMeta(meta);
                }
            }
        }
    }

    public static void givePotionEffect(Player player, PotionEffectType effectType, int amplifier) {
        PotionEffect effect = new PotionEffect(effectType, Integer.MAX_VALUE, amplifier);
        player.addPotionEffect(effect);
    }
}