package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.InfectArena;
import com.tatemylove.COD.Arenas.KillArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Leveling.PlayerLevels;
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
            KillArena killArena = new KillArena(main);
            TDM tdm = new TDM(main);
            if(BaseArena.type == BaseArena.ArenaType.TDM) {
                if (main.RedTeamScore > 30) {

                    tdm.endTDM();
                    runnable.stopGameTime();
                } else if (main.BlueTeamScore > 30) {

                   tdm.endTDM();
                    runnable.stopGameTime();
                }
                if(main.PlayingPlayers.size() < 2){
                    tdm.endTDM();
                }
            }else if(BaseArena.type == BaseArena.ArenaType.KC){
                if(main.RedTeamScore > 30){
                    killArena.endKill();
                    runnable.stopGameTime();
                }else if(main.BlueTeamScore > 30){
                   killArena.endKill();
                    runnable.stopGameTime();
                }
                if(main.PlayingPlayers.size() < 2){
                    killArena.endKill();
                }
            }else if(BaseArena.type == BaseArena.ArenaType.INFECT){
                if(InfectArena.humans.size() < 1){
                    for(Player pp : main.PlayingPlayers) {
                        InfectArena infectArena = new InfectArena(main);
                        infectArena.endInfect(pp);
                        pp.sendMessage(main.prefix + "§aGame has ended! Teleporting you back to the lobby!");
                    }
                }
            }

            for(Player pp : main.PlayingPlayers){
                if(BaseArena.type == BaseArena.ArenaType.TDM) {
                    if (TDM.redTeam.contains(pp)) {
                       SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), pp);
                    } else if (TDM.blueTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + tdm.getBetterTeam(), pp);
                    }
                }else if(BaseArena.type == BaseArena.ArenaType.KC){
                    if (KillArena.redTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + killArena.getBetterTeam(), pp);
                    } else if (KillArena.blueTeam.contains(pp)) {
                        SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + killArena.getBetterTeam(), pp);
                    }
                }
            }
        }
    }
}
