package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTime extends BukkitRunnable {

    private static GameTime gameTime = null;
    Main main;
    public GameTime(Main m){
        main = m;
        gameTime = GameTime.this;
    }

    public static int timeleft;

    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(timeleft == 0){
                if(BaseArena.type == BaseArena.ArenaType.TDM) {
                    TDM tdm = new TDM(new Main());
                    MainRunnable mainRunnable = new MainRunnable(main);
                    tdm.endTDM();
                    mainRunnable.stopGameTime();
                }
            }
        }
        timeleft -=1;
    }
}
