package com.tatemylove.COD.Leveling;

import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;

public class PlayerLevels {
    Main main;
    public PlayerLevels(Main m){
        main = m;
    }
    public int levelTwo = 3000;
    public int levelThree = 6200;
    public int levelFour  = 8000;
    public int levelFive = 10000;
    public int levelSix = 12000;
    public int levelSeven = 15000;
    public int levelEight = 25000;
    public int levelNine = 30000;
    public int levelTen = 35000;
    public int levelEleven = 45000;
    public int levelTwelve = 52000;
    public int levelThirteen = 58000;
    public int levelFourteen = 60000;
    public int levelFifteen = 72800;

    public void addExp (Player p, int amount){
        int exp = StatsFile.getData().getInt(p.getUniqueId().toString() + ".EXP");
        StatsFile.getData().set(p.getUniqueId().toString() + ".EXP", exp+amount);
        StatsFile.saveData();
        StatsFile.reloadData();
    }
    public void resetExp (Player p, int amount){
        StatsFile.getData().set(p.getUniqueId().toString() + ".EXP", amount);
        StatsFile.saveData();
        StatsFile.reloadData();
    }
    public void addLevel (Player p, int amount){
        int level = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Level");
        int flevel = level+amount;

        StatsFile.getData().set(p.getUniqueId().toString() + ".Level", flevel);
        StatsFile.saveData();
        StatsFile.reloadData();

        p.sendMessage(main.prefix + "§b§lYou've been promoted to level §e§l" + flevel);
    }
    public int getLevel(Player p){
        return StatsFile.getData().getInt(p.getUniqueId().toString() + ".Level");
    }
    public int getEXP(Player p ){
        return StatsFile.getData().getInt(p.getUniqueId().toString() + ".EXP");
    }
}
