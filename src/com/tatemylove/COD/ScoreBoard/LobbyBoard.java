package com.tatemylove.COD.ScoreBoard;

import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
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

        if(main.getConfig().getBoolean("SwiftEconomy.Enabled")) {
            Score moneys = objective.getScore("§eMoney:");
            moneys.setScore(13);

            Team money = board.registerNewTeam("money");

            money.addEntry(ChatColor.RED.toString());
            money.setPrefix(ChatColor.GREEN.toString() + "§a");
            money.setSuffix(ChatColor.GREEN.toString() + "0");

            objective.getScore(ChatColor.GREEN.toString()).setScore(12);
        }

        if(main.getConfig().getBoolean("MySQL.Enabled")){

        }else{
            Score kills = objective.getScore("§eKills:");
            kills.setScore(11);

            int getKills = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Kills");
            Score kill = objective.getScore("    §a" + getKills);
            kill.setScore(10);

            Score deaths = objective.getScore("§eDeaths:");
            deaths.setScore(9);

            int getDeaths = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Deaths");
            Score death = objective.getScore("    §a" + getDeaths);
            death.setScore(8);

            Score wins = objective.getScore("§eWins:");
            wins.setScore(7);

            int getWins = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Wins");
            Score win = objective.getScore("    §a" + getWins);
            win.setScore(6);
        }

        if(lobbyboard.get(p.getName()) == null) lobbyboard.put(p.getName(), board);

        createBoard(p);

        if(main.getConfig().getBoolean("SwiftEconomy.Enabled")) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    double money = SwiftEconomyAPI.playerMoney.get(p.getName());
                    board.getTeam("money").setSuffix("    §a" + money);
                }
            }.runTaskTimer(ThisPlugin.getPlugin(), 0, 40);
        }
    }
    private void createBoard(Player p){
        p.setScoreboard(lobbyboard.get(p.getName()));
    }
}
