package com.tatemylove.COD2;

import com.tatemylove.COD2.Achievement.AchievementAPI;
import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Commands.MainCommand;
import com.tatemylove.COD2.Files.*;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Listeners.InventoryInteract;
import com.tatemylove.COD2.Listeners.PlayerDeath;
import com.tatemylove.COD2.Listeners.PlayerJoin;
import com.tatemylove.COD2.Tasks.CountDown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static String prefix = "§8§l[COD] ";
    public static int time;
    public static int gametime;
    public static int minplayers;
    public static int redscore = 0;
    public static int bluescore = 0;
    public static ArrayList<String> arenas = new ArrayList<>();
    public static ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public static ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public static ArrayList<Player> RedTeam = new ArrayList<>();
    public static ArrayList<Player> BlueTeam = new ArrayList<>();
    public static ArrayList<String> guns = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> ownedGuns = new HashMap<>();
    public static HashMap<String, ArrayList<String>> ownedSecondary = new HashMap<>();
    public static HashMap<String, ArrayList<String>> ownedPerks = new HashMap<>();
    public static HashMap<String, ArrayList<String>> unlockedAchievements = new HashMap<>();
    public static ArrayList<String> achievements = new ArrayList<>();
    public boolean enabled = false;

    public void onEnable(){
        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        BaseArena.states = BaseArena.ArenaStates.Countdown;

        ArenasFile.setup(this);
        LobbyFile.setup(this);
        GunsFile.setup(this);
        PlayerData.setup(this);
        AchievementFile.setup(this);

        AchievementAPI.createAchievement("hi");
        GameInventory.settUp();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);


        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        time=getConfig().getInt("time-to-start");
        minplayers =getConfig().getInt("min-players");
        gametime=getConfig().getInt("game-time");

        if(ArenasFile.getData().contains("Arenas.")) {
            for (String s : ArenasFile.getData().getConfigurationSection("Arenas.").getKeys(false)) {
                arenas.add(s);
            }
        }
        if(ArenasFile.getData().contains("Arenas.")) {
            CountDown c = new CountDown();
            c.runTaskTimer(this, 0, 20L);
            enabled=true;
        }else{
            Logger log = getLogger();
            log.severe("COD is disabled, you need to create an arena first, then type /cod enable");
        }
        if(GunsFile.getData().contains("Guns.")){
            for(String s : GunsFile.getData().getConfigurationSection("Guns.").getKeys(false)){
                guns.add(s);
            }
        }
        if(PlayerData.getData().contains("Players.")){
            for(String s: PlayerData.getData().getConfigurationSection("Players.").getKeys(false)){
                ownedGuns.put(s, new ArrayList<>(PlayerData.getData().getStringList("Players." + s + ".Guns")));
                ownedSecondary.put(s, new ArrayList<>(PlayerData.getData().getStringList("Players." + s + ".Secondary")));
                ownedPerks.put(s, new ArrayList<>(PlayerData.getData().getStringList("Players." +s+ ".Perks")));
            }
        }

    }
}
