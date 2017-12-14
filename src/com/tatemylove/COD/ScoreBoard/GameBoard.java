package com.tatemylove.COD.ScoreBoard;

import com.tatemylove.COD.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;

public class GameBoard {
    Main main;
    private GameBoard gameBoard = null;
    private HashMap<String, Scoreboard> gameboard = new HashMap<>();

    public GameBoard(Main m){
        main = m;
        gameBoard = GameBoard.this;
    }

    public void setGameBoard(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("GameBoard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("gameboard-name")));

        if(gameboard.get(p.getName()) == null) gameboard.put(p.getName(), board);

        createBoard(p);
    }

    private void createBoard(Player p){
        p.setScoreboard(gameboard.get(p.getName()));
    }

}
