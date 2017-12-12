package com.tatemylove.COD;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Listeners.MoveListener;
import com.tatemylove.COD.Listeners.PlayerJoinListener;
import com.tatemylove.COD.Runnables.GracePeriod;
import com.tatemylove.COD.Runnables.MainRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {
    public String prefix = "§7§l[COD] ";
    public ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public int min_players = getConfig().getInt("min-players");
    public int max_players = getConfig().getInt("max-players");
    private ProtocolManager manager;

    MainRunnable runnable;
    GracePeriod gracePeriod;

    public Main(MainRunnable run, GracePeriod g){
        gracePeriod = g;
        runnable = run;
    }

    public void onEnable(){
        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        ArenaFile.setup(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        BaseArena.states = BaseArena.ArenaStates.Countdown;
        runnable.startCountDown();

        manager = ProtocolLibrary.getProtocolManager();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MoveListener(gracePeriod, this), this);
    }
}
