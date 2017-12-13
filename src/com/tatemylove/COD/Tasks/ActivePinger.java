package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class ActivePinger extends BukkitRunnable{
    Main main;
    TDM tdm;



    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(main.RedTeamScore > 99){
                tdm.endTDM();
            }else if(main.BlueTeamScore > 99){
                tdm.endTDM();
            }
        }
    }
}
