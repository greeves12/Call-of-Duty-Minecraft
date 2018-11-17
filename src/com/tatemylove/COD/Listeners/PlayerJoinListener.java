package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Achievements.AchievementSQL;
import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.LanguageFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.MySQL.DeathsSQL;
import com.tatemylove.COD.MySQL.KillsSQL;
import com.tatemylove.COD.MySQL.WinsSQL;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Updater.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PlayerJoinListener implements Listener {

    Main main;
    public PlayerJoinListener (Main m){
        main = m;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        AchievementSQL achievementSQL = new AchievementSQL();
        if(main.getConfig().getBoolean("MySQL.Enabled")){
            achievementSQL.addToDB(p);
        }

        Main.kills.put(p.getName(), 0);
       Main.deaths.put(p.getName(), 0);
        Main.killStreak.put(p.getName(), 0);

        main.nonPlayers.add(p);

        if(p.hasPermission("cod.getupdates")){
            Updater updater = new Updater();
            updater.update(p);
        }
        if(ThisPlugin.getPlugin().getConfig().getBoolean("auto-join")){
            main.WaitingPlayers.add(e.getPlayer());
            e.getPlayer().sendMessage(main.prefix);
            for(Player pp : main.WaitingPlayers){
                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageFile.getData().getString("join-message").replace("%player%", p.getName())));
            }
        }
        File file = new File("plugins/COD/arenas.yml");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine() == null){
                e.getPlayer().sendMessage(main.prefix + "§2COD is disabled because you don't have any Arenas!! "+"\n" + "         §4Create an arena and then type §c/cod enable");
            }

        }catch(Exception ei){
            ei.printStackTrace();
        }

        if(main.getConfig().getBoolean("MySQL.Enabled")){
            DeathsSQL deathsSQL = new DeathsSQL(main);
            WinsSQL winsSQL = new WinsSQL(main);
            KillsSQL killsSQL = new KillsSQL(main);

            deathsSQL.addToDB(p);
            winsSQL.addToDB(p);
            killsSQL.addToDB(p);

            WinsSQL.getWins(p);
            KillsSQL.getKills(p);
            DeathsSQL.getDeaths(p);


        }else{
            if(!StatsFile.getData().contains(p.getUniqueId().toString())) {
                StatsFile.getData().set(p.getUniqueId().toString() + ".Wins", 0);
                StatsFile.getData().set(p.getUniqueId().toString() + ".Kills", 0);
                StatsFile.getData().set(p.getUniqueId().toString() + ".Deaths", 0);
                StatsFile.getData().set(p.getUniqueId().toString() + ".Level", 1);
                StatsFile.getData().set(p.getUniqueId().toString() + ".EXP", 0);
                StatsFile.saveData();
                StatsFile.reloadData();
            }else{
                int kills = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Kills");
                int wins = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Wins");
                int deaths = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Deaths");
                int level = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Level");
                int exp = StatsFile.getData().getInt(p.getUniqueId().toString() + ".EXP");

                LobbyBoard.winsH.put(p.getName(), wins);
                LobbyBoard.killsH.put(p.getName(), kills);
                LobbyBoard.deathsH.put(p.getName(), deaths);
            }
        }

        LobbyBoard lobbyBoard = new LobbyBoard(main);

        lobbyBoard.setLobbyBoard(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        MainCommand cmd = new MainCommand(main);

        GetLobby lobby = new GetLobby(main);
        if(main.PlayingPlayers.contains(p)){
            main.PlayingPlayers.remove(p);
            p.teleport(lobby.getLobby(p));
            p.getInventory().clear();
            if(cmd.savedInventory.containsKey(p)) {
                p.getInventory().setContents(cmd.savedInventory.get(p));
            }
            if(cmd.armorSaved.containsKey(p)){
                p.getInventory().setArmorContents(cmd.armorSaved.get(p));
            }
        }else if(main.WaitingPlayers.contains(p)){
            main.WaitingPlayers.remove(p);
            p.getInventory().clear();
            if(cmd.savedInventory.containsKey(p)) {
                p.getInventory().setContents(cmd.savedInventory.get(p));
            }
            if(cmd.armorSaved.containsKey(p)){
                p.getInventory().setArmorContents(cmd.armorSaved.get(p));
            }
            p.teleport(lobby.getLobby(p));
        }
    }

}
