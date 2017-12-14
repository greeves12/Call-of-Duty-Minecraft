package com.tatemylove.COD;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Files.LanguageFile;
import com.tatemylove.COD.Files.LobbyFile;
import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.Listeners.PlayerDeathListener;
import com.tatemylove.COD.Listeners.PlayerInteractListener;
import com.tatemylove.COD.Listeners.PlayerJoinListener;
import com.tatemylove.COD.MySQL.MySQL;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.Tasks.ActivePinger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public String prefix = "§7§l[COD] ";
    public ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public HashMap<String, Integer> kills = new HashMap<>();
    public int min_players = getConfig().getInt("min-players");
    public int max_players = 2;
    private ProtocolManager manager;
    public int RedTeamScore;
    public int BlueTeamScore;
    private MySQL mySQL;

    private static Main instance;

    public static Main getInstance(){
        return instance;
    }


    @Override
    public void onEnable(){

        instance = this;

        MainRunnable runnable = new MainRunnable(this);


        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        ArenaFile.setup(this);
        LanguageFile.setup(this);
        LobbyFile.setup(this);

        File file = new File("plugins/COD/arenas.yml");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine() != null){
                runnable.startCountDown();
            }else{
                Bukkit.getConsoleSender().sendMessage(prefix + "§2COD is disabled because you don't have any Arenas!!");
                Bukkit.getConsoleSender().sendMessage(prefix + "§4Create an arena and then type §c/cod enable");
            }
        }catch(Exception e){

        }
        saveDefaultConfig();
        reloadConfig();


        BaseArena.states = BaseArena.ArenaStates.Countdown;


        manager = ProtocolLibrary.getProtocolManager();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        ActivePinger pinger = new ActivePinger(this);
        pinger.runTaskTimerAsynchronously(this, 0, 20);

        if(Bukkit.getServer().getPluginManager().getPlugin("SwiftEconomy") != null){
            Bukkit.getConsoleSender().sendMessage( prefix + "§eSwift-Economy found! Hooking in");
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("CrackShot") != null){
            Bukkit.getConsoleSender().sendMessage(prefix + "§eCrackShot found! Hooking in");
        }else{
            Bukkit.getConsoleSender().sendMessage(prefix + "§cDisabling, please install CrackShot");
            getPluginLoader().disablePlugin(this);
        }

        if(getConfig().getBoolean("MySQL.Enabled")){
            try {
                String username = getConfig().getString("MySQL.Username");
                String passworld = getConfig().getString("MySQL.Password");
                String ip = getConfig().getString("MySQL.Ip");
                String database = getConfig().getString("MySQL.Database");
                mySQL = new MySQL(username, passworld, ip, database);
                Bukkit.getConsoleSender().sendMessage("§eHooking into MySQL was a success");
            }catch (Exception e){
                Bukkit.getConsoleSender().sendMessage("§cHooking into MySQL failed! Check your settings.");
            }
        }

        AttackDogs dogs = new AttackDogs(this);
        dogs.settUp();
    }
}
