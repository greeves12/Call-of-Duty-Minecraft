package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {
    public int timeuntilstart = ThisPlugin.getPlugin().getConfig().getInt("countdown-time");

    MainRunnable main;
    Main ma;
    TDM tdm;
    GetArena getArena;

    public CountDown(MainRunnable r, Main m, TDM t, GetArena g){
        main = r;
        ma = m;
        tdm = t;
        getArena = g;
    }

    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Countdown){
            if(timeuntilstart == 0){
                if(ma.WaitingPlayers.size() < ma.min_players){
                    main.stopCountDown();
                    main.startCountDown();

                    return;
                }
                BaseArena.states = BaseArena.ArenaStates.Started;
                tdm.assignTeams(Integer.toString(getArena.getCurrentArena()));
                tdm.startTDM(Integer.toString(getArena.getCurrentArena()));
                main.stopCountDown();
            }
            if((timeuntilstart % 10 == 0) || (timeuntilstart < 0)){
                for(Player p : ma.WaitingPlayers){

                }
            }
        }
        if(ma.WaitingPlayers.size() >= ma.min_players){
            timeuntilstart -= 1;
        }
    }
}
