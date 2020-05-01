package com.tatemylove.COD2.Achievement;

import org.bukkit.Material;

public class BaseAchievements {

    public static void createAchievements(){
        AchievementAPI.createAchievementToYML("FirstBlood", "§4§nFirst Blood", Material.WOODEN_SWORD, "§dGet your first kill");
        AchievementAPI.createAchievementToYML("10Kill", "§c§nDirty Work", Material.STONE_SWORD, "§dKill 10 enemies");
        AchievementAPI.createAchievementToYML("50Kills", "§6§nHeaven and Beyond", Material.IRON_SWORD, "§dKill 50 enemies");
        AchievementAPI.createAchievementToYML("200Kills", "§5§nProblem Solving", Material.DIAMOND_SWORD, "§dKill 200 enemies");

        AchievementAPI.createAchievementToYML("Victory", "§3§nA little taste of Victory", Material.FIREWORK_ROCKET, "§dGet your first win");

        AchievementAPI.createAchievementToYML("GettingUpgrade", "§e§nGettin' Upgrades", Material.DIAMOND_CHESTPLATE, "§dUnlock your first weapon");
        AchievementAPI.createAchievementToYML("PerkUp", "§2§nPerk Up", Material.POTION, "§dGet your first perk");

        AchievementAPI.createAchievementToYML("Prestige", "§7§nThrough the ranks", Material.ANVIL, "§dPrestige for the first time");
    }
}
