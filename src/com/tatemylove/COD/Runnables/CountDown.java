package com.tatemylove.COD.Runnables;

import com.tatemylove.COD.Arenas.*;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CountDown extends BukkitRunnable {
    public static int timeuntilstart;
    private final String nextArena = ArenaFile.getData().getString("Arenas." + GetArena.getNextArena() + ".Name");
    private final String type = ArenaFile.getData().getString("Arenas." + GetArena.getNextArena() + ".Type");
    public static final int id = GetArena.getCurrentArena();

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
                String type = ArenaFile.getData().getString("Arenas." + id + ".Type");
                if(type.equalsIgnoreCase("TDM")) {
                    BaseArena.type = BaseArena.ArenaType.TDM;
                    tdm.assignTeams(Integer.toString(id));
                    tdm.startTDM(Integer.toString(id));
                }else if(type.equalsIgnoreCase("KC")){
                    BaseArena.type = BaseArena.ArenaType.KC;
                    KillArena killArena = new KillArena(ma);
                    killArena.assignTeam(Integer.toString(id));
                    killArena.startKC(Integer.toString(id));
                }else if(type.equalsIgnoreCase("INF")){
                    BaseArena.type = BaseArena.ArenaType.INFECT;
                    InfectArena infectArena = new InfectArena(ma);
                    infectArena.assignTeams(Integer.toString(id));
                    infectArena.startInfect(Integer.toString(id));
                }
                runnable.stopCountDown();
            }
            if((timeuntilstart % 10 == 0) || (timeuntilstart < 0)){
                if(ma.WaitingPlayers.size() >= ma.min_players) {
                    for (Player p : ma.WaitingPlayers) {
                        SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                        SendCoolMessages.sendSubTitle(p, "§e§lGame starting in §a§l" + timeuntilstart + " seconds", 10, 30, 10);


                        if (type.equalsIgnoreCase("TDM")) {
                            p.sendMessage(ma.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a" + type);
                        }else if(type.equalsIgnoreCase("KC")){
                            p.sendMessage(ma.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a Kill Confirmed");
                        }else if(type.equalsIgnoreCase("INF")){
                            p.sendMessage(ma.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a Infected");
                        }
                    }
                }
            }
        }
        if(ma.WaitingPlayers.size() >= ma.min_players){
            timeuntilstart -= 1;
        }
    }
}
