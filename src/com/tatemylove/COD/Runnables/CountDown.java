package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CountDown extends BukkitRunnable {
    public static int timeuntilstart;

    Main ma;
    private static CountDown countDown = null;
    public CountDown(Main main){
        ma = main;
        countDown = CountDown.this;
    }


    @Override
    public void run() {
        GetArena getArena = new GetArena();
        MainRunnable runnable = new MainRunnable(ma);

        if(BaseArena.states == BaseArena.ArenaStates.Countdown){
            if(timeuntilstart == 0){
                TDM tdm = new TDM(ma);
                if(ma.WaitingPlayers.size() < ma.min_players){
                    runnable.stopCountDown();
                    runnable.startCountDown();
                    return;
                }
                BaseArena.states = BaseArena.ArenaStates.Started;
                String type = ArenaFile.getData().getString("Arenas." + getArena.getNextArena() + ".Type");
                if(type.equalsIgnoreCase("TDM")) {
                    BaseArena.type = BaseArena.ArenaType.TDM;
                    tdm.assignTeams(Integer.toString(getArena.getCurrentArena()));
                    tdm.startTDM(Integer.toString(getArena.getCurrentArena()));
                }
                runnable.stopCountDown();
            }
            if((timeuntilstart % 10 == 0) || (timeuntilstart < 0)){
                if(ma.WaitingPlayers.size() >= ma.min_players) {
                    for (Player p : ma.WaitingPlayers) {
                        SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                        SendCoolMessages.sendSubTitle(p, "§e§lGame starting in §a§l" + timeuntilstart + " seconds", 10, 30, 10);

                        String nextArena = ArenaFile.getData().getString("Arenas." + getArena.getNextArena() + ".Name");

                        p.sendMessage(ma.prefix + "§6§l§nUpcoming Arena:§e " + nextArena);
                    }
                }
            }
        }
        if(ma.WaitingPlayers.size() >= ma.min_players){
            timeuntilstart -= 1;
        }
    }
}
