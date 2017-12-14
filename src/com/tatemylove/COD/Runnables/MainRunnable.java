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
    private int graceperiod;
    public ArrayList<String> arena = new ArrayList<>();
    public void startCountDown(){
        GetArena getArena = new GetArena();
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(main), 0L, 20L);
        arena.add(ArenaFile.getData().getString("Arenas." + getArena.getNextArena() + ".Name"));
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
    public void startGracePeriod(){
         graceperiod = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GracePeriod(main), 0L, 20L);
    }
    public void stopGracePeriod(){
        Bukkit.getServer().getScheduler().cancelTask(graceperiod);
    }
}
