package com.tatemylove.COD2.Leveling;

import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Files.StatsFile;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;

public class LevelRegistryAPI {

    public static void addExp (Player p, int amount){
        int exp = StatsFile.getData().getInt("Players."+p.getUniqueId().toString() + ".EXP");
        StatsFile.getData().set("Players."+p.getUniqueId().toString() + ".EXP", exp+amount);
        StatsFile.saveData();
        StatsFile.reloadData();

        p.sendMessage(Main.prefix + "§bYou gained §e" + amount + " §bexperience");
    }
    public static void resetExp (Player p, int amount){
        StatsFile.getData().set("Players."+p.getUniqueId().toString() + ".EXP", amount);
        StatsFile.saveData();
        StatsFile.reloadData();
    }
    public static void addLevel (Player p, int amount){
        int level = PlayerData.getData().getInt("Players."+p.getUniqueId().toString() + ".Level");
        int flevel = level+amount;

        PlayerData.getData().set("Players."+p.getUniqueId().toString() + ".Level", flevel);
        PlayerData.saveData();
        PlayerData.reloadData();

        p.sendMessage(Main.prefix + "§b§lYou are now level §e§l" + flevel);
    }
    public static int getLevel(Player p){
        return PlayerData.getData().getInt("Players."+p.getUniqueId().toString() + ".Level");
    }
    public static int getEXP(Player p ){
        return StatsFile.getData().getInt("Players."+p.getUniqueId().toString() + ".EXP");
    }
    public static int getPrestige(Player p){
        return PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Prestige");
    }
    public static void setPrestiege(Player p, int prestige){
        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Prestige", prestige);
        PlayerData.saveData();
    }
    public static void setLevel(Player p, int level){
        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Level", level);
        PlayerData.saveData();
    }

}
