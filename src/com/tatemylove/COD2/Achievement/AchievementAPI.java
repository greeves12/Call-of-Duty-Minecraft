package com.tatemylove.COD2.Achievement;

import com.sun.istack.internal.NotNull;
import com.tatemylove.COD2.Files.AchievementFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AchievementAPI {

    public static String prefix ="§8[§eAchievement§8] ";

    public static void createAchievementToYML(@NotNull String AchievementName,@NotNull String DisplayName,@NotNull Material material,@NotNull String description){
        if(!AchievementFile.getData().contains("Achievements." + AchievementName)){
            AchievementFile.getData().set("Achievements." + AchievementName + ".Name", DisplayName);
            AchievementFile.getData().set("Achievements." + AchievementName + ".Material", material.toString());
            AchievementFile.getData().set("Achievements." + AchievementName + ".Desc", description);

            AchievementFile.saveData();
        }
    }

    public static boolean hasAchievement(Player player, String AchievementName){
        if(!Main.achievements.contains(AchievementName)){
            return false;
        }else{
            return AchievementFile.getData().contains("Players." + player.getUniqueId().toString() + "."+ AchievementName + ".Unlocked");
        }
    }

    public static void grantAchievement(Player player, String AchievementName){
        if(Main.achievements.contains(AchievementName)){
            if(!hasAchievement(player, AchievementName)){
                    player.sendMessage(prefix + "§aCongratulations! Achievement §c" + AchievementName + " §aunlocked!");

                    Calendar date = Calendar.getInstance();


                    AchievementFile.getData().set("Players." + player.getUniqueId().toString() + "."  + AchievementName + ".Unlocked", true);
                    AchievementFile.getData().set("Players." + player.getUniqueId().toString() + "."  + AchievementName + ".Date", date);
                    AchievementFile.saveData();
                }
            }
        }

    }

