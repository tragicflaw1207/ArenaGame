package me.ruende.arenagame.monster;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.ruende.arenagame.game.difficulty.Difficulty;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterSpawner {
    private final List<ActiveMob> spawnedMobs = new ArrayList<>();
    private final Random random = new Random();
    private final Difficulty currentDifficulty;

    public MonsterSpawner(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
    }

    public void spawnMonsters(int round, List<Location> spawnLocations) {
        int spawnCount = getSpawnCountForRound(round);

        for (int i = 0; i < spawnLocations.size(); i++) {
            Location spawnLocation = spawnLocations.get(i);
            for (int j = 0; j < spawnCount; j++) {
                String mobType = getMobTypeForDifficulty(round, i);
                MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobType).orElse(null);

                if (mob != null) {
                    ActiveMob activeMob = mob.spawn(BukkitAdapter.adapt(spawnLocation), 1);
                    if (activeMob != null) {
                        setMobAttributes(activeMob, round, mobType);
                        spawnedMobs.add(activeMob);
                    }
                }
            }
        }
    }

    public void removeMonsters() {
        for (ActiveMob mob : spawnedMobs) {
            Entity entity = mob.getEntity().getBukkitEntity();
            if (entity != null && entity.isValid()) {
                entity.remove();
            }
        }
        spawnedMobs.clear();
    }

    public void adjustMonsterDifficulty(int round) {
        for (ActiveMob mob : spawnedMobs) {
            setMobAttributes(mob, round, mob.getMobType());
        }
    }

    public boolean areAllMonstersCleared() {
        spawnedMobs.removeIf(mob -> mob.getEntity().isDead());
        return spawnedMobs.isEmpty();
    }

    private void setMobAttributes(ActiveMob mob, int round, String mobType) {
        LivingEntity entity = (LivingEntity) mob.getEntity().getBukkitEntity();

        AttributeInstance healthAttribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthAttribute != null) {
            double baseHealth = healthAttribute.getBaseValue();
            double additionalHealth = getAdditionalHealthForRound(round, mobType);
            double newHealth = baseHealth + additionalHealth;
            healthAttribute.setBaseValue(newHealth);
            entity.setHealth(newHealth);
        }

        AttributeInstance speedAttribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speedAttribute != null) {
            double baseSpeed = speedAttribute.getBaseValue();
            double additionalSpeed = getAdditionalSpeedForRound(round);
            double newSpeed = baseSpeed + additionalSpeed;
            speedAttribute.setBaseValue(newSpeed);
        }

        AttributeInstance damageAttribute = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (damageAttribute != null) {
            double baseDamage = damageAttribute.getBaseValue();
            double newDamage = getAdjustedDamageForRound(baseDamage, round);
            damageAttribute.setBaseValue(newDamage);
        }
    }

    private String getMobTypeForDifficulty(int round, int spawnPointIndex) {
        return switch (currentDifficulty) {
            case EASY, NORMAL -> getMobTypeForEasyOrNormal(round);
            case HARD -> getMobTypeForHard(spawnPointIndex);
        };
    }

    private String getMobTypeForEasyOrNormal(int round) {
        if (round <= 10) {
            return "spadesoldier";
        } else if (round <= 20) {
            return random.nextInt(3) < 2 ? "spadesoldier" : "heartsoldier";
        } else if (round <= 30) {
            return random.nextBoolean() ? "spadesoldier" : "heartsoldier";
        } else {
            return random.nextInt(3) < 2 ? "heartsoldier" : "spadesoldier";
        }
    }

    private String getMobTypeForHard(int spawnPointIndex) {
        if (spawnPointIndex == 2) {
            return "heartsoldier";
        }
        return random.nextBoolean() ? "spadesoldier" : "heartsoldier";
    }

    private int getSpawnCountForRound(int round) {
        return 1 + Math.floorDiv(round - 1, 5);
    }

    private double getAdditionalHealthForRound(int round, String mobType) {
        return switch (mobType) {
            case "spadesoldier" -> round * 5;
            case "heartsoldier" -> round * 10;
            default -> 0.0;
        };
    }

    private double getAdditionalSpeedForRound(int round) {
        return ((double) (round - 1) / 10) * 0.05;
    }

    private double getAdjustedDamageForRound(double baseDamage, int round) {
        int multiplier = Math.floorDiv(round - 1, 5);
        return baseDamage * Math.pow(2, multiplier);
    }
}
