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

            TDM tdm = new TDM(main);
            if ((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
                if (((tdm.blueTeam.contains(p) && (tdm.redTeam.contains(pp))) || (tdm.redTeam.contains(p)) && (tdm.blueTeam.contains(pp)))) {

                }
                if (tdm.redTeam.contains(p)) {
                    if (tdm.blueTeam.contains(pp)) {
                        main.BlueTeamScore++;
                    }
                } else if (tdm.blueTeam.contains(p)) {
                    if (tdm.redTeam.contains(pp)) {
                        main.RedTeamScore++;
                    }
                }
            }
            int ks = main.killStreak.get(p.getName());
            int kills = main.kills.get(p.getName());
            int deaths = main.deaths.get(p.getName());

            main.killStreak.put(pp.getName(), ks+1);
            main.kills.put(pp.getName(), kills+1);
            main.deaths.put(p.getName(), deaths+1);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if (p.isDead()) {
                        craftPlayer.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                    }
                }
            });
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
        playerLevels.addExp(p, main.getConfig().getInt("exp-per-kill"));

        if(level == 1){
            if(exp >= playerLevels.levelTwo){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 2){
            if(exp >= playerLevels.levelThree){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 3){
            if(exp >= playerLevels.levelFour){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 4){
            if(exp >= playerLevels.levelFive){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 5){
            if(exp >= playerLevels.levelSix){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 6){
            if(exp >= playerLevels.levelSeven){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 7){
            if(exp >= playerLevels.levelEight){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 8){
            if(exp >= playerLevels.levelNine){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }else if(level == 9){
            if(exp >= playerLevels.levelTen){
                playerLevels.addLevel(p, 1);
                playerLevels.resetExp(p);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        TDM tdm = new TDM(main);
        GetArena getArena = new GetArena();
        if(tdm.redTeam.contains(p)){
            e.setRespawnLocation(getArena.getRedSpawn(p));
        }else if(tdm.blueTeam.contains(p)){
            e.setRespawnLocation(getArena.getBlueSpawn(p));
        }
    }
}
