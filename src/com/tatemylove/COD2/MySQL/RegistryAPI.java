package com.tatemylove.COD2.MySQL;

import com.google.common.math.Stats;
import com.tatemylove.COD2.Files.StatsFile;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class RegistryAPI {
    public static HashMap<UUID, Integer> kills = new HashMap<>();
    public static HashMap<UUID, Integer> deaths = new HashMap<>();

    public static void registerWin(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){

        }else{
            int wins = StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Wins");
            StatsFile.getData().set("Players." + p.getUniqueId().toString() + ".Wins", wins+1);
            StatsFile.saveData();

        }
    }

    public static void registerKill(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){

        }else{
            int wins = StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Kills");
            StatsFile.getData().set("Players." + p.getUniqueId().toString() + ".Kills", wins+1);
            StatsFile.saveData();
            RegistryAPI.kills.put(p.getUniqueId(), RegistryAPI.kills.get(p.getUniqueId()));
        }
    }

    public static void registerDeath(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){

        }else{
            int wins = StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Deaths");
            StatsFile.getData().set("Players." + p.getUniqueId().toString() + ".Deaths", wins+1);
            StatsFile.saveData();
            RegistryAPI.deaths.put(p.getUniqueId(), RegistryAPI.deaths.get(p.getUniqueId()));
        }
    }
    public static int getKills(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){

            return 0;
        }else{
            return StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Kills");
        }
    }
    public static int getWins(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){
            return 0;
        }else{
            return StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Wins");
        }
    }

    public static int getDeaths(Player p){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")){
            return 0;
        }else{
            return StatsFile.getData().getInt("Players." + p.getUniqueId().toString() + ".Deaths");
        }
    }

}
