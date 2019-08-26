package com.tatemylove.COD2.Achievement;

import com.tatemylove.COD2.Files.AchievementFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AchievementAPI {

    public static String prefix ="§8[§eAchievement§8] ";

    public static void createAchievement(String AchievementName){
        if(!AchievementFile.getData().contains("Achievements." + AchievementName)){
            AchievementFile.getData().set("Achievements." + AchievementName, AchievementName);
            AchievementFile.saveData();
        }
    }

    public static boolean hasAchievement(Player player, String AchievementName){
        if(!Main.achievements.contains(AchievementName)){
            return false;
        }else{
            ArrayList<String> achievements = Main.unlockedAchievements.get(player.getUniqueId().toString());
            return achievements.contains(AchievementName);
        }
    }

    public static void grantAchievement(Player player, String AchievementName){
        if(Main.achievements.contains(AchievementName)){
            ArrayList<String> achi = Main.unlockedAchievements.get(player.getUniqueId().toString());
            if(!achi.contains(AchievementName)){
                achi.add(AchievementName);
                PlayerData.getData().set("Players." + player.getUniqueId().toString() + ".Achievements." + AchievementName + ".Unlocked", true);
                Calendar cal = Calendar.getInstance();

                PlayerData.getData().set("Players." + player.getUniqueId().toString() + ".Achievements." + AchievementName + ".Date", cal.getTime());
                PlayerData.saveData();

                player.sendMessage(prefix + "§aCongratulations! Achievement §c" + AchievementName + " §aunlocked!");
            }
        }

    }
}
