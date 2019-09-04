package com.tatemylove.COD2.Commands;

import org.bukkit.command.CommandSender;

public class HelpCommand {

    public void helpMe(CommandSender p, String[] args ){
        if(args.length == 1){
            p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD Help §d· §e1/6§d· §6]  §4§m  «<   | *«   | §r");
            p.sendMessage(" §8- §3/cod help §7- §bShows the help menu");
            p.sendMessage(" §8- §3/cod create §7- §bCreates an arena");
            p.sendMessage(" §8- §3/cod delete §7- §bDeletes an arena");
            p.sendMessage(" §8- §3/cod setspawn §7- §bSet spawns for an arena");
            p.sendMessage(" §8- §3/cod lobby §7- §bTeleport to the lobby");
        }else if(args.length >= 2){
            if(args[1].equalsIgnoreCase("1")){
                p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e2/6§d· §6]  §4§m  «<   | *«   | §r");
                p.sendMessage(" §8- §3/cod enable §7- §bEnable the plugin");
                p.sendMessage(" §8- §3/cod join §7- §bJoin the lobby");
                p.sendMessage(" §8- §3/cod leave §7- §bLeave the lobby");
                p.sendMessage(" §8- §3/cod setlobby §7- §bSet the lobby");
                p.sendMessage(" §8- §3/cod reload §7- §bReloads the plugin");
            }else if(args[1].equalsIgnoreCase("2")){
                p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e2/6§d· §6]  §4§m  «<   | *«   | §r");
                p.sendMessage(" §8- §3/cod make §7- §bMake a gun");
                p.sendMessage(" §8- §3/cod buy §7- §bBuy a Guns/Perks/Explosives");
                p.sendMessage(" §8- §3/cod class §7- §bSelect or create a custom class");
                p.sendMessage(" §8- §3/cod deletegun §7- §bDelete a gun");
              //  p.sendMessage(" §8- §3/cod npc §7- §bCreates an NPC (Citizens 2)");
            }else if(args[1].equalsIgnoreCase("3")){
                p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e3/6§d· §6]  §4§m  «<   | *«   | §r");
                p.sendMessage(" §8- §3/cod setflag §7- §bSet Flags for CTF arenas");
                //p.sendMessage(" §8- §3/cod buy §7- §bBuy a gun");
                //p.sendMessage(" §8- §3/cod class §7- §bSelect a kit");
               // p.sendMessage(" §8- §3/cod deletegun §7- §bDelete a gun");
               // p.sendMessage(" §8- §3/cod npc §7- §bCreates an NPC (Citizens 2)");*/
            }
        }
    }
}
