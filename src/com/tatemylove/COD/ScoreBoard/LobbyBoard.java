package com.tatemylove.COD.ScoreBoard;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class LobbyBoard {
    Main main;
    private LobbyBoard lobbyBoard = null;
    private HashMap<String, Scoreboard> lobbyboard = new HashMap<>();
    public HashMap<String, Integer> winsH = new HashMap<>();
    public HashMap<String, Integer> killsH = new HashMap<>();
    public HashMap<String, Integer> deathsH = new HashMap<>();

    public LobbyBoard(Main m){
        main = m;
        lobbyBoard = LobbyBoard.this;
    }

    public void setLobbyBoard(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("LobbyBoard", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("lobbyboard-name")));

        Score userName = objective.getScore("§eUsername:");
        userName.setScore(15);

        Score username = objective.getScore("    §a" + p.getName());
        username.setScore(14);


            Score kills = objective.getScore("§eKills:");
            kills.setScore(12);

            Score blank1 = objective.getScore(" ");
            blank1.setScore(16);

            Score blank2 = objective.getScore("  ");
            blank2.setScore(13);

            Score blank3 = objective.getScore("   ");
            blank3.setScore(10);

            Score blank4 = objective.getScore("    ");
            blank4.setScore(7);


            Team kill = board.registerNewTeam("kills");
            kill.addEntry(ChatColor.RED.toString());
            kill.setPrefix(ChatColor.GREEN.toString() + "§a");
            kill.setSuffix(ChatColor.GREEN.toString() + "0");

            objective.getScore(ChatColor.RED.toString()).setScore(11);

            Score deaths = objective.getScore("§eDeaths:");
            deaths.setScore(9);


            Team death = board.registerNewTeam("deaths");
            death.addEntry(ChatColor.BLACK.toString());
            death.setSuffix(ChatColor.GREEN.toString() + "0");
            death.setPrefix(ChatColor.GREEN.toString() + "§a");
            objective.getScore(ChatColor.BLACK.toString()).setScore(8);

            Score wins = objective.getScore("§eWins:");
            wins.setScore(6);

            Team win = board.registerNewTeam("wins");
            win.addEntry(ChatColor.GRAY.toString());
            win.setSuffix(ChatColor.GREEN.toString() + "0");
            win.setPrefix(ChatColor.GREEN.toString() + "§a");
            objective.getScore(ChatColor.GRAY.toString()).setScore(5);



        if(lobbyboard.get(p.getName()) == null) lobbyboard.put(p.getName(), board);

        createBoard(p);

            new BukkitRunnable() {

                @Override
                public void run() {
                    int getKills = killsH.get(p.getName());
                    int getDeaths = deathsH.get(p.getName());
                    int getWins = winsH.get(p.getName());
                    board.getTeam("wins").setSuffix("    §a" + getWins);
                    board.getTeam("deaths").setSuffix("    §a" + getDeaths);
                    board.getTeam("kills").setSuffix("    §a" + getKills);
                }
            }.runTaskTimer(ThisPlugin.getPlugin(), 0, 40);

    }
    private void createBoard(Player p){
        p.setScoreboard(lobbyboard.get(p.getName()));
    }
}
