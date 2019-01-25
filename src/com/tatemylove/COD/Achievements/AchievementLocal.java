package com.tatemylove.COD.Achievements;

import com.tatemylove.COD.Files.AchievementFile;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AchievementLocal {
    Main main;

    public AchievementLocal (Main main){
        this.main = main;
    }
    public void setAchievement(Player p, BaseAchievement type){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        if(main.getConfig().getBoolean("MySQL.Enabled")){

        }else{
            if(!AchievementFile.getData().contains(p.getUniqueId().toString() + "."+type.toString())){
                AchievementFile.getData().set(p.getUniqueId().toString() + ".Type", type.toString());
                AchievementFile.getData().set(p.getUniqueId().toString() + ".Time", df.format(dateobj));

                AchievementFile.saveData();
                AchievementFile.reloadData();
            }
        }
    }
}
