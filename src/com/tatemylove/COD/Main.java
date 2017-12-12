package com.tatemylove.COD;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Runnables.MainRunnable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {
    MainRunnable runnable;

    public Main(MainRunnable run){
        runnable = run;
    }


    public String prefix = "§7§l[COD§f§l-§8§lWarfare] ";
    public ArrayList<Player> WaitingPlayers = new ArrayList<>();
    public ArrayList<Player> PlayingPlayers = new ArrayList<>();
    public int min_players = getConfig().getInt("min-players");
    public int max_players = getConfig().getInt("max-players");

    public void onEnable(){
        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        ArenaFile.setup(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        BaseArena.states = BaseArena.ArenaStates.Countdown;
        runnable.startCountDown();
    }
}
