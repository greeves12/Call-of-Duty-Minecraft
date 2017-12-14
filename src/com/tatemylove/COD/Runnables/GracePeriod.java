package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GracePeriod extends BukkitRunnable {
    public int timeuntilstart = 10;

    Main main;

    private static GracePeriod gracePeriod = null;
    public GracePeriod(Main m){
        gracePeriod = GracePeriod.this;

        main = m;
    }


    @Override
    public void run() {

        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(timeuntilstart == 0){
                MainRunnable runnable = new MainRunnable(main);

                for(Player p : main.PlayingPlayers){
                    SendCoolMessages.resetTitleAndSubtitle(p);
                    SendCoolMessages.sendTitle(p, "§3§lGo! Go! Go!", 10, 30 , 10);
                }
                runnable.stopGracePeriod();
                runnable.startGameTime();
            }
            if(timeuntilstart % 10 == 0){
                for(Player p :main.PlayingPlayers){
                    SendCoolMessages.resetTitleAndSubtitle(p);
                    SendCoolMessages.sendTitle(p, "§8", 10, 30, 10);
                    SendCoolMessages.sendSubTitle(p, "§8§lReady To Roll In " + timeuntilstart, 10, 30, 10);
                }
            }
        }
        timeuntilstart -=1;
    }
}
