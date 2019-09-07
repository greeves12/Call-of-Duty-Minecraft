package com.tatemylove.COD2.Tasks;

import com.tatemylove.COD2.Arenas.*;
import com.tatemylove.COD2.Events.CODStartEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Listeners.PlayerJoin;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.tatemylove.COD2.Listeners.PlayerJoin.isEnabled;

public class CountDown extends BukkitRunnable {
    GetArena getArena = new GetArena();

    int time = Main.time;
    int minPlayers = Main.minplayers;
    private String nextArena = getArena.getNextArena();
    private String type = ArenasFile.getData().getString("Arenas." + getArena.getCurrentArena() + ".Type");


    @Override
    public void run() {
        if(time == 0){
            if(Main.WaitingPlayers.size() < minPlayers){
                time=Main.time;
            }else{
                for(Player p : Main.WaitingPlayers){

                    if(isEnabled(p)){
                        PlayerJoin.clazz.put(p.getUniqueId(), PlayerJoin.getEnabled(p));
                    }else{
                        PlayerJoin.clazz.put(p.getUniqueId(), "");
                    }

                }

                if(!ArenasFile.getData().contains("Arenas." + nextArena)){
                    time=Main.time;
                    GetArena getArenas = new GetArena();
                    nextArena = getArenas.getNextArena();
                    type = ArenasFile.getData().getString("Arenas." + getArenas.getCurrentArena() + ".Type");
                }

                if(type.equalsIgnoreCase("KC")){

                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                    new KillConfirmed().assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("TDM")){

                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                     new TDM().assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("INF")){
                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                    new Infected().assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("FFA")){
                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                    new FFA().assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("CTF")){
                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                    new CTF().assignTeams(nextArena);
                }else if(type.equalsIgnoreCase("GUN")){
                    Bukkit.getServer().getPluginManager().callEvent(new CODStartEvent(Main.WaitingPlayers, nextArena, type));
                    new GunGame().assignTeams(nextArena);
                }
                cancel();
                Main.onGoingArenas.add(nextArena);
                Main.arenas.remove(nextArena);

                    if(Main.arenas.size() >= Main.onGoingArenas.size()) {
                        new CountDown().runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
                    }

            }
        }
        if((time % 10 == 0) && time != 0){
            if(Main.WaitingPlayers.size() >= minPlayers) {
                for (Player p : Main.WaitingPlayers) {
                    //SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                    //SendCoolMessages.sendSubTitle(p, "§e§lGame starting in §a§l" + timeuntilstart + " seconds", 10, 30, 10);

                    if (type.equalsIgnoreCase("TDM")) {

                        p.sendMessage("§4§m |   »* |   >»  §r  §6[ §dOperation: §fWhiteout §6]  §4§m  «<   | *«   | §r");
                        p.sendMessage("§7Upcoming Arena:§a " + nextArena);
                        p.sendMessage(" ");
                        p.sendMessage("§7GameMode:§a Team Deathmatch");
                        p.sendMessage(" ");
                        p.sendMessage("§7Time until insertion: §a" + time + " seconds");
                        p.sendMessage("§4§m |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | §r");

                    }else if(type.equalsIgnoreCase("KC")){
                        p.sendMessage("§4§m |   »* |   >»  §r  §6[ §dOperation: §fWhiteout §6]  §4§m  «<   | *«   | §r");
                        p.sendMessage("§7Upcoming Arena:§a " + nextArena);
                        p.sendMessage(" ");
                        p.sendMessage("§7GameMode:§a Kill Confirmed" );
                        p.sendMessage(" ");
                        p.sendMessage("§7Time until insertion: §a" + time + " seconds");
                        p.sendMessage("§4§m |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | §r");
                    }else if(type.equalsIgnoreCase("INF")){
                        p.sendMessage("§4§m |   »* |   >»  §r  §6[ §dOperation: §fWhiteout §6]  §4§m  «<   | *«   | §r");
                        p.sendMessage("§7Upcoming Arena:§a " + nextArena);
                        p.sendMessage(" ");
                        p.sendMessage("§7GameMode:§a Infected");
                        p.sendMessage(" ");
                        p.sendMessage("§7Time until insertion: §a" + time + " seconds");
                        p.sendMessage("§4§m |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | §r");
                    }else if(type.equalsIgnoreCase("FFA")){
                        p.sendMessage("§4§m |   »* |   >»  §r  §6[ §dOperation: §fWhiteout §6]  §4§m  «<   | *«   | §r");
                        p.sendMessage("§7Upcoming Arena:§a " + nextArena);
                        p.sendMessage(" ");
                        p.sendMessage("§7GameMode:§a Free For All");
                        p.sendMessage(" ");
                        p.sendMessage("§7Time until insertion: §a" + time + " seconds");
                        p.sendMessage("§4§m |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | §r");

                    }else if(type.equalsIgnoreCase("GUN")){
                        p.sendMessage("§4§m |   »* |   >»  §r  §6[ §dOperation: §fWhiteout §6]  §4§m  «<   | *«   | §r");
                        p.sendMessage("§7Upcoming Arena:§a " + nextArena);
                        p.sendMessage(" ");
                        p.sendMessage("§7GameMode:§a Gun Game");
                        p.sendMessage(" ");
                        p.sendMessage("§7Time until insertion: §a" + time + " seconds");
                        p.sendMessage("§4§m |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   | §r");

                    }
                }
            }
        }
        if(Main.WaitingPlayers.size() >= minPlayers) {
            time -= 1;
        }
    }
}
