package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class MainRunnable {
    Main main;
    private static MainRunnable runnable = null;

    public MainRunnable(Main m){
        main = m;
        runnable = MainRunnable.this;
    }

    private int countdown;
    private int gametime;
    public void startCountDown(){
        GetArena getArena = new GetArena();
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(main), 0L, 20L);
    }
    public void stopCountDown(){
        Bukkit.getServer().getScheduler().cancelTask(countdown);
    }

    public void startGameTime(){
        gametime = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GameTime(main), 0L, 20L);
    }
    public void stopGameTime(){
        Bukkit.getServer().getScheduler().cancelTask(gametime);
    }
}
