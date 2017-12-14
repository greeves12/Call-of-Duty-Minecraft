package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import org.bukkit.scheduler.BukkitRunnable;

public class ActivePinger extends BukkitRunnable{
    Main main;
    private static ActivePinger pinger = null;
    public ActivePinger(Main m){
        main = m;
        pinger = ActivePinger.this;
    }

    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            MainRunnable runnable = new MainRunnable(main);
            if(main.RedTeamScore > 99){
                TDM tdm = new TDM(main);
                tdm.endTDM();
                runnable.stopGameTime();
            }else if(main.BlueTeamScore > 99){
                TDM tdm = new TDM(main);
                tdm.endTDM();
                runnable.stopGameTime();
            }
        }
    }
}
