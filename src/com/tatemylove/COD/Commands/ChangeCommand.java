package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){
            if(args.length == 0){
                Player p = (Player) commandSender;
                p.sendMessage( "§8§l§nCOD-Warfare Changelog §ev" + Main.version);
                p.sendMessage("§7-Admin panel has been released (Server diagnostics)");
                p.sendMessage("§7-Spigot won't let me into my account so updates will be on github only");

                return true;
            }
        }
        return true;
    }
}
