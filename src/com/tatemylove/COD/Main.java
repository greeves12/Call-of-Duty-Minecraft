package com.tatemylove.COD;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Runnables.CountDown;
import com.tatemylove.COD.Runnables.GameTime;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {



    public String prefix = "§7§l[COD] ";
    public ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public int min_players = 2;
    public int max_players = 2;
    private ProtocolManager manager;
    public int RedTeamScore;
    public int BlueTeamScore;
    private int countdown;
    private int gametime;
    private int graceperiod;

    private static Main instance;
    public static Main getInstance(){
        return instance;
    }


    @Override
    public void onEnable(){

        instance = this;
        //main = this;

        //MainRunnable runnable = new MainRunnable(this);
       // runnable.startCountDown();



        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        ArenaFile.setup(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();


        BaseArena.states = BaseArena.ArenaStates.Countdown;


        manager = ProtocolLibrary.getProtocolManager();

       // Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        //Bukkit.getServer().getPluginManager().registerEvents(new MoveListener(gracePeriod, this), this);

        //ActivePinger pinger = new ActivePinger(this, tdm);
       // pinger.runTaskTimerAsynchronously(this, 0, 20);
    }
    public void startCountDown(){
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(this), 0L, 20L);
    }
    public void stopCountDown(){
        Bukkit.getServer().getScheduler().cancelTask(countdown);
    }

    public void startGameTime(){
        gametime = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GameTime(), 0L, 20L);
    }
    public void stopGameTime(){
        Bukkit.getServer().getScheduler().cancelTask(gametime);
    }
    public void startGracePeriod(){
        // graceperiod = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GracePeriod(main)), 0L, 20L);
    }
    public void stopGracePeriod(){
        Bukkit.getServer().getScheduler().cancelTask(graceperiod);
    }
}
