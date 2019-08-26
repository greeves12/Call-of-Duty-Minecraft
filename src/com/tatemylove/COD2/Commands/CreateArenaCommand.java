package com.tatemylove.COD2.Commands;

import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;

public class CreateArenaCommand {

    public static void createArena(Player p, String name, String type){
        ArenasFile.getData().set("Arenas." + name + ".Type", type);
        ArenasFile.saveData();
        ArenasFile.reloadData();
        Main.arenas.add(name);

        p.sendMessage(Main.prefix + "§bArena: §a" + name  + " §bcreated with §6GameMode: §e" + type);
    }

    public static void setSpawns(Player p, String[] args, String name){
        if(args[2].equalsIgnoreCase("blue")){
            String world = p.getLocation().getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();


            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.World", world);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.X", x);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.Y", y);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.Z", z);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.Pitch", pitch);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Blue.Yaw", yaw);

            ArenasFile.saveData();
            ArenasFile.reloadData();

            p.sendMessage(Main.prefix + "§9Blue spawn set for §3Arena: " + name);
        }else if(args[2].equalsIgnoreCase("red")){
            String world = p.getLocation().getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            float yaw = p.getLocation().getYaw();
            float pitch = p.getLocation().getPitch();


            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.World", world);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.X", x);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.Y", y);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.Z", z);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.Pitch", pitch);
            ArenasFile.getData().set("Arenas." + name + ".Spawns.Red.Yaw", yaw);

            ArenasFile.saveData();
            ArenasFile.reloadData();

            p.sendMessage(Main.prefix + "§4Red spawn set for §3Arena: " + name);
        }
    }
    public static void deleteArena(Player p, String name){
        ArenasFile.getData().set("Arenas." + name, null);
        ArenasFile.saveData();
        ArenasFile.reloadData();
        Main.arenas.remove(name);

        p.sendMessage(Main.prefix + "§cArena deleted");
    }
}
