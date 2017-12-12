package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;

public class MainRunnable {
    Main main;
    TDM tdm;
    GetArena getArena;

    private int countdown;
    private int gametime;
    private int graceperiod;

    public MainRunnable(Main m, TDM t, GetArena g){
        getArena = g;
        tdm = t;
        main = m;
    }

    public void startCountDown(){
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(this, main, tdm, getArena), 0L, 20L);
    }
    public void stopCountDown(){
        Bukkit.getServer().getScheduler().cancelTask(countdown);
    }

    public void startGameTime(){
        gametime = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GameTime(tdm, this), 0L, 20L);
    }
    public void stopGameTime(){
        Bukkit.getServer().getScheduler().cancelTask(gametime);
    }
    public void startGracePeriod(){
        graceperiod = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new GracePeriod(main, this), 0L, 20L);
    }
    public void stopGracePeriod(){
        Bukkit.getServer().getScheduler().cancelTask(graceperiod);
    }
}
