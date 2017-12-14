package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.Utilities.SendCoolMessages;
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
                    p.sendMessage("§8§l§nCOD-Warfare");
                    p.sendMessage("§7§lAuthor: §etatemylove");
                    p.sendMessage("§7§lCommands: §e/cod help");
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){

            }
            if(args[0].equalsIgnoreCase("join")) {
                if (p.hasPermission("cod.join")) {
                    main.WaitingPlayers.add(p);
                    p.sendMessage(main.prefix + "§e§lYou joined the Queue");
                    p.teleport(getLobby.getLobby(p));
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
            if(args[0].equalsIgnoreCase("leave")){
                if(p.hasPermission("cod.leave")){
                    main.WaitingPlayers.remove(p);
                    SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                    SendCoolMessages.sendSubTitle(p, "§8§lLeft COD lobby", 10, 30, 10);
                }
            }
        }
        return true;
    }
}
