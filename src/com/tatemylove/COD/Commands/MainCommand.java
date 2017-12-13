package com.tatemylove.COD.Commands;

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
            if(args[0].equalsIgnoreCase("join")){
                    main.WaitingPlayers.add(p);
                    p.sendMessage(main.prefix + "§e§lYou joined the Queue");
            }
        }
        return true;
    }
}
