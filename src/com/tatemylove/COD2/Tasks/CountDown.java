package com.tatemylove.COD2.Tasks;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Arenas.GetArena;
import com.tatemylove.COD2.Arenas.TDM;
import com.tatemylove.COD2.Events.CODStartEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {
    int time = Main.time;
    int minPlayers = Main.minplayers;
    private String nextArena = ArenasFile.getData().getString("Arenas." + GetArena.getNextArena());
    private String type = ArenasFile.getData().getString("Arenas." + GetArena.getNextArena() + ".Type");

    @Override
    public void run() {
        if(time == 0){
            if(Main.WaitingPlayers.size() < minPlayers){
                time=Main.time;
            }else{
                BaseArena.states = BaseArena.ArenaStates.Started;
                if(type.equalsIgnoreCase("KC")){
                    BaseArena.type= BaseArena.ArenaType.KC;
                    //start killconfirmed
                }else if(type.equalsIgnoreCase("TDM")){
                    BaseArena.type = BaseArena.ArenaType.TDM;
                    TDM.assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("INF")){
                    BaseArena.type = BaseArena.ArenaType.INFECT;
                }
                Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.PlayingPlayers, nextArena, type));
                new GameTime().runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
                cancel();
            }
        }
        if((time % 10 == 0) || (time < 0)){
            if(Main.WaitingPlayers.size() >= minPlayers) {
                for (Player p : Main.WaitingPlayers) {
                    //SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                    //SendCoolMessages.sendSubTitle(p, "§e§lGame starting in §a§l" + timeuntilstart + " seconds", 10, 30, 10);


                    if (type.equalsIgnoreCase("TDM")) {
                        p.sendMessage(Main.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a" + type);
                    }else if(type.equalsIgnoreCase("KC")){
                        p.sendMessage(Main.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a Kill Confirmed");
                    }else if(type.equalsIgnoreCase("INF")){
                        p.sendMessage(Main.prefix + "§6§l§nUpcoming Arena:§a " + nextArena + " §4§l§nGameMode:§a Infected");
                    }
                }
            }
        }
        if(Main.WaitingPlayers.size() < minPlayers) {
            time -= 1;
        }
    }
}
