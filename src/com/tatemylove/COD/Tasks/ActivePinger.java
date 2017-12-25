package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.GameTime;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
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
            if(main.RedTeamScore > 20){
                TDM tdm = new TDM(main);
                tdm.endTDM();
                runnable.stopGameTime();
            }else if(main.BlueTeamScore > 20){
                TDM tdm = new TDM(main);
                tdm.endTDM();
                runnable.stopGameTime();
            }

            for(Player all : main.PlayingPlayers){
                TDM tdm = new TDM(main);
                if(TDM.redTeam.contains(all)){
                    SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), all);
                }else if(TDM.blueTeam.contains(all)){
                    SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), all);
                }
            }
        }
    }
}
