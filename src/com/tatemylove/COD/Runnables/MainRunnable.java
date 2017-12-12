package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;

public class MainRunnable {
    public int countdown;

    public void startCountDown(){
        countdown = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new CountDown(this, new Main()), 0L, 20L);

    }
    public void stopCountDown(){
        Bukkit.getServer().getScheduler().cancelTask(countdown);
    }
}
