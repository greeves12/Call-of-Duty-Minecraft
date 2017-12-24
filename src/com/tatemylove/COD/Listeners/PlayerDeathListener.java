package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.KillStreaks.Moab;
import com.tatemylove.COD.KillStreaks.Napalm;
import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathListener implements Listener {
    Main main;
    public PlayerDeathListener(Main m){
        main = m;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player pp = e.getEntity().getKiller();
        final CraftPlayer craftPlayer = (CraftPlayer)p;

        if(BaseArena.type == BaseArena.ArenaType.TDM) {


            //if(Main.killStreak.containsKey(pp.getName())) {
                Main.killStreak.put(pp.getName(), Main.killStreak.get(pp.getName()) + 1);
            //}
            //if(Main.killStreak.containsKey(p.getName())) {
                Main.killStreak.put(p.getName(), 0);
            //}
            //if(Main.kills.containsKey(pp.getName())) {
                Main.kills.put(pp.getName(), Main.kills.get(pp.getName()) + 1);
            //}
           // if(Main.deaths.containsKey(p.getName())) {
                Main.deaths.put(p.getName(), Main.deaths.get(p.getName()) + 1);
            //}

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(p.isDead()) {
                            craftPlayer.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                        }
                    }
                }.runTaskLater(ThisPlugin.getPlugin(), 20L);

        }else if(BaseArena.type == BaseArena.ArenaType.KC){

        }
        AttackDogs dogs =new AttackDogs(main);
        dogs.onKill(e);

        Moab moab = new Moab(main);
        moab.onEntityKill(e);

        Napalm napalm = new Napalm(main);
        napalm.onEntityKill(e);

        PlayerLevels playerLevels = new PlayerLevels(main);
        int exp = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".EXP");
        int level = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Level");
        playerLevels.addExp(pp, main.getConfig().getInt("exp-per-kill"));

        if(level == 1){
            if(exp >= playerLevels.levelTwo){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 2){
            if(exp >= playerLevels.levelThree){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 3){
            if(exp >= playerLevels.levelFour){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 4){
            if(exp >= playerLevels.levelFive){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 5){
            if(exp >= playerLevels.levelSix){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 6){
            if(exp >= playerLevels.levelSeven){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 7){
            if(exp >= playerLevels.levelEight){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 8){
            if(exp >= playerLevels.levelNine){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }else if(level == 9){
            if(exp >= playerLevels.levelTen){
                playerLevels.addLevel(pp, 1);
                playerLevels.resetExp(pp);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        TDM tdm = new TDM(main);
        GetArena getArena = new GetArena();
        e.setRespawnLocation(tdm.respawnPlayer(p));
        if(TDM.blueTeam.contains(p)){
            main.RedTeamScore++;
        }else if(TDM.redTeam.contains(p)){
            main.BlueTeamScore++;
        }

    }
}
