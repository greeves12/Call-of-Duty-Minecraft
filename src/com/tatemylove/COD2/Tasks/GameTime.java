package com.tatemylove.COD2.Tasks;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Arenas.TDM;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTime extends BukkitRunnable {
    int time = Main.gametime;

    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(Main.PlayingPlayers.size() < Main.minplayers){
                if(BaseArena.type == BaseArena.ArenaType.TDM){
                    TDM.endTDM();

                }else if(BaseArena.type == BaseArena.ArenaType.KC){

                }else if(BaseArena.type == BaseArena.ArenaType.INFECT){

                }
                cancel();
            }
            if(Main.redscore == 20 || Main.bluescore == 20){
                if(BaseArena.type == BaseArena.ArenaType.TDM){
                    TDM.endTDM();

                }else if(BaseArena.type == BaseArena.ArenaType.KC){

                }else if(BaseArena.type == BaseArena.ArenaType.INFECT){

                }
                cancel();
            }

            if(time == 0){
                if(BaseArena.type == BaseArena.ArenaType.TDM){
                    TDM.endTDM();

                    CountDown c = new CountDown();
                    c.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
                }else if(BaseArena.type == BaseArena.ArenaType.KC){
                    CountDown c = new CountDown();
                    c.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
                }else if(BaseArena.type == BaseArena.ArenaType.INFECT){
                    CountDown c = new CountDown();
                    c.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
                }
                cancel();
            }
            time-=1;
        }
    }
}
