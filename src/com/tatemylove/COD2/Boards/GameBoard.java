package com.tatemylove.COD2.Boards;

import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class GameBoard {

    private GameBoard gameBoard = null;
    private HashMap<String, Scoreboard> gameboard = new HashMap<>();

    public void setBoard(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("Gameboard", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§lYour Scores");

      /*  Score kills = objective.getScore("");
        kills.setScore(12);*/

        Score blank2 = objective.getScore("  ");
        blank2.setScore(10);

       /* Score deaths = objective.getScore("");
        deaths.setScore(9);*/

        Score blank3 = objective.getScore("   ");
        blank3.setScore(7);
        Score blank4 = objective.getScore("    ");
        blank4.setScore(13);

       /* Score killstreak = objective.getScore("");
        killstreak.setScore(6);*/


        Team kill = board.registerNewTeam("kill");
        kill.addEntry(ChatColor.AQUA.toString());
        kill.setPrefix(ChatColor.GREEN.toString() + "§a");
        kill.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.AQUA.toString()).setScore(12);

        Team death = board.registerNewTeam("death");
        death.addEntry(ChatColor.RED.toString());
        death.setPrefix(ChatColor.GREEN.toString() + "§a");
        death.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.RED.toString()).setScore(9);

        Team killstreaks = board.registerNewTeam("killstreak");
        killstreaks.addEntry(ChatColor.DARK_GREEN.toString());
        killstreaks.setPrefix(ChatColor.GREEN.toString() + "§a");
        killstreaks.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(6);

        gameboard.put(p.getName(), board);
        createBoard(p);

        new BukkitRunnable(){

            @Override
            public void run() {

                int kill = RegistryAPI.kills.get(p.getUniqueId());
                int death = RegistryAPI.deaths.get(p.getUniqueId());
                int killstreak = RegistryAPI.killstreak.get(p.getUniqueId());

                board.getTeam("kill").setSuffix("§aKills: §6" + kill);
                board.getTeam("death").setSuffix("§aDeaths: §6" + death);
                board.getTeam("killstreak").setSuffix("§aKill Streak: §6"  + killstreak);
            }
        }.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
    }

    private void createBoard(Player p){
        p.setScoreboard(gameboard.get(p.getName()));
    }
}
