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
        CountDown.timeuntilstart = main.getConfig().getInt("countdown-time");
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(main), 0L, 20L);
    }
    public void stopCountDown(){
        Bukkit.getServer().getScheduler().cancelTask(countdown);
    }

    public void startGameTime(){
        GameTime.timeleft = main.getConfig().getInt("game-time");
        gametime = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GameTime(main), 0L, 20L);
    }
    public void stopGameTime(){
        Bukkit.getServer().getScheduler().cancelTask(gametime);
    }
}
