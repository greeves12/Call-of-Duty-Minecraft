package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {
    public int timeuntilstart = 90;

    Main ma;
    private static CountDown countDown = null;
    public CountDown(Main main){
        ma = main;
        countDown = CountDown.this;
    }


    @Override
    public void run() {

        TDM tdm = new TDM(ma);
        GetArena getArena = new GetArena();
        if(BaseArena.states == BaseArena.ArenaStates.Countdown){
            if(timeuntilstart == 0){
                if(ma.WaitingPlayers.size() < ma.min_players){
                    ma.stopCountDown();
                    ma.startCountDown();

                    return;
                }
                BaseArena.states = BaseArena.ArenaStates.Started;
                tdm.assignTeams(Integer.toString(getArena.getCurrentArena()));
                tdm.startTDM(Integer.toString(getArena.getCurrentArena()));
                ma.stopCountDown();
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
