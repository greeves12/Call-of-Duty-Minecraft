package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Files.LobbyFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainCommand implements CommandExecutor {
    Main main;
    public MainCommand(Main m){
        main = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        CreateArenaCommand createArenaCommand = new CreateArenaCommand(main);
        GetLobby getLobby = new GetLobby(main);
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                    p.sendMessage("§8§l§nCOD-Warfare");
                    p.sendMessage("");
                    p.sendMessage("§7Author: §etatemylove");
                    p.sendMessage("§7Commands: §e/cod help");
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                if(p.hasPermission("cod.help")){
                    HelpCommand helpCommand = new HelpCommand();
                    helpCommand.helpMe(p, args);
                }
            }
            if(args[0].equalsIgnoreCase("enable")){
                if(p.hasPermission("cod.enable")) {
                    if (!StatsFile.getData().getBoolean("plugin-enabled")) {
                        File file = new File("plugins/COD/arenas.yml");
                        File lobby = new File("plugins/COD/lobby.yml");
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            BufferedReader lobbyready = new BufferedReader(new FileReader(lobby));
                            if (reader.readLine() != null) {
                                if (lobbyready.readLine() != null) {
                                    MainRunnable runnable = new MainRunnable(main);
                                    runnable.startCountDown();
                                    p.sendMessage(main.prefix + "§bCOD is setup and ready to play. Have fun!");
                                    StatsFile.getData().set("plugin-enabled", true);
                                    StatsFile.saveData();
                                    StatsFile.reloadData();
                                } else {
                                    p.sendMessage(main.prefix + "§8Lobby: §6Not Set");
                                }
                            } else {
                                p.sendMessage(main.prefix + "§8Arenas: §6Not Set");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        p.sendMessage(main.prefix + "§aPlugin Already Enabled");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("join")) {
                if (p.hasPermission("cod.join")) {
                    if (StatsFile.getData().getBoolean("plugin-enabled")) {
                        if(!main.WaitingPlayers.contains(p)) {
                            LobbyBoard lobbyBoard = new LobbyBoard(main);
                            main.WaitingPlayers.add(p);
                            p.teleport(getLobby.getLobby(p));
                            int kills = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Kills");
                            int wins = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Wins");
                            int deaths = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Deaths");

                            lobbyBoard.killsH.put(p.getName(), kills);
                            lobbyBoard.deathsH.put(p.getName(), deaths);
                            lobbyBoard.winsH.put(p.getName(), wins);
                            lobbyBoard.setLobbyBoard(p);

                            SendCoolMessages.sendTitle(p, "§a", 10, 30, 10);
                            SendCoolMessages.sendSubTitle(p, "§e§lYou joined the Queue", 10, 30, 10);
                            for (Player pp : main.WaitingPlayers) {
                                pp.sendMessage(main.prefix + "§6§l" + p.getName() + " §e§ljoined the Queue");
                            }
                        }else{
                            p.sendMessage(main.prefix + "§3§lAlready in the Queue");
                        }
                    }else{
                        p.sendMessage(main.prefix + "§7COD is not setup. Ask an Administrator to set it up.");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("create")){
                if(p.hasPermission("cod.create")) {
                    if(args.length == 3) {
                        String name = args[1];
                        if (args[2].equalsIgnoreCase("tdm")) {
                            createArenaCommand.createArena(p, name, args[2].toUpperCase());
                        }
                    }else{
                        p.sendMessage(main.prefix + "§8/cod create <name> <type>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("delete")){
                if(p.hasPermission("cod.delete")) {
                    Integer id = Integer.valueOf(args[1]);

                    if (args.length == 2) {
                        createArenaCommand.deleteArena(p, id);
                    }else{
                        p.sendMessage(main.prefix + "§7/cod delete <ID>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("set")){
                if(p.hasPermission("cod.setspawn")){
                    int id = Integer.parseInt(args[1]);
                    createArenaCommand.setSpawns(p, args, id);
                }
            }
            if(args[0].equalsIgnoreCase("setlobby")){
                if(p.hasPermission("cod.setlobby")){
                    getLobby.setLobby(p);
                }
            }
            if(args[0].equalsIgnoreCase("lobby")){
                if(p.hasPermission("cod.lobby")){
                    p.teleport(getLobby.getLobby(p));
                }
            }
            if(args[0].equalsIgnoreCase("leave")){
                if(p.hasPermission("cod.leave")) {
                    if (main.WaitingPlayers.contains(p)) {
                        main.WaitingPlayers.remove(p);
                        SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                        SendCoolMessages.sendSubTitle(p, "§8§lLeft COD lobby", 10, 30, 10);
                        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                        p.sendMessage(main.prefix + "§8§lLeft COD lobby");

                        for (Player pp : main.WaitingPlayers) {
                            pp.sendMessage(main.prefix + "§7§l" + p.getName() + " §8§lhas left the Queue");
                        }
                    }else{
                        p.sendMessage(main.prefix + "§4§lYou are not the in the Queue");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("reload")){
                if(p.hasPermission("cod.reload")){
                    main.saveDefaultConfig();
                    main.reloadConfig();

                    LobbyFile.saveData();
                    LobbyFile.reloadData();
                    ArenaFile.saveData();
                    ArenaFile.reloadData();

                    p.sendMessage(main.prefix + "§8§lConfigs reloaded!");
                }
            }
        }
        return true;
    }
}
