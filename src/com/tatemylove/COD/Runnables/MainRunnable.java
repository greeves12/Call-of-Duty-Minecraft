package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;

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
    public void startCountDown(){
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
    public void startGracePeriod(){
         graceperiod = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GracePeriod(main), 0L, 20L);
    }
    public void stopGracePeriod(){
        Bukkit.getServer().getScheduler().cancelTask(graceperiod);
    }
}
