package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    p.sendMessage("§8§lCOD-Warfare");
                    p.sendMessage("§7Author: §etatemylove");
                    p.sendMessage("§7Commands: §e/cod help");
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){

            }
            if(args[0].equalsIgnoreCase("join")) {
                if (p.hasPermission("cod.join")) {
                    main.WaitingPlayers.add(p);
                    p.sendMessage(main.prefix + "§e§lYou joined the Queue");
                }
            }
            if(args[0].equalsIgnoreCase("create")){
                if(p.hasPermission("cod.create")) {
                    String name = args[1];
                    if (args[2].equalsIgnoreCase("tdm")) {
                        createArenaCommand.createArena(p, name, args[2].toUpperCase());
                    }
                }
            }
            if(args[0].equalsIgnoreCase("delete")){
                if(p.hasPermission("cod.delete")){
                    Integer id = Integer.valueOf(args[1]);

                    createArenaCommand.deleteArena(p, id);
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
        }
        return true;
    }
}
