package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;

import java.util.TreeMap;

public class CreateArenaCommand {
    Main main;
    private static CreateArenaCommand createArenaCommand = null;
    public CreateArenaCommand(Main m){
        main = m;
        createArenaCommand = CreateArenaCommand.this;
    }

    public void createArena(Player p, String name, String type){
        TreeMap<Integer, Integer> numbers = new TreeMap<>();

        for (int k = 0; ArenaFile.getData().contains("Arenas." + k); k++) {
            numbers.put(k, k);
        }

        int newID;
        if (numbers.size() == 0) {
            newID = 0;
        } else {
            newID = numbers.lastEntry().getValue() + 1;
        }
        ArenaFile.getData().set("Arenas." + newID + ".Name", name);
        ArenaFile.getData().set("Arenas." + newID + ".Type", type);
        ArenaFile.saveData();
        ArenaFile.reloadData();
        p.sendMessage(main.prefix + "§bArena: §a" + name  + " §bcreated with the ID §a" + newID + " and §6GameMode: §e" + type);
    }

    public void deleteArena(Player p,  int id){
        if(ArenaFile.getData().contains("Arenas." + id)){
            ArenaFile.getData().set("Arenas." + id, null);
            ArenaFile.saveData();
            ArenaFile.reloadData();
            p.sendMessage(main.prefix + "§dArena deleted with the ID " + id);
        }else{
            p.sendMessage(main.prefix + "§cArena with the ID " +id + " does not exist");
        }
    }
    public void setSpawns(Player p, String[] args,int id){
        if(args.length == 3) {
            if (ArenaFile.getData().contains("Arenas." + id)) {
                if(args[2].equalsIgnoreCase("blue")){
                    String world = p.getLocation().getWorld().getName();
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float yaw = p.getLocation().getYaw();
                    float pitch = p.getLocation().getPitch();

                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.World", world);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.X", x);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.Y", y);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.Z", z);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.Pitch", pitch);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Blue.Yaw", yaw);

                    ArenaFile.saveData();
                    ArenaFile.reloadData();

                    p.sendMessage(main.prefix + "§9Blue spawn set §3Arena ID: " + id);
                }else if(args[2].equalsIgnoreCase("red")){
                    String world = p.getLocation().getWorld().getName();
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float yaw = p.getLocation().getYaw();
                    float pitch = p.getLocation().getPitch();

                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.World", world);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.X", x);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.Y", y);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.Z", z);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.Pitch", pitch);
                    ArenaFile.getData().set("Arenas." + id + ".Spawns.Red.Yaw", yaw);

                    ArenaFile.saveData();
                    ArenaFile.reloadData();

                    p.sendMessage(main.prefix + "§4Red spawn set §cArena ID: " + id);
                }
            }
        }else{
            p.sendMessage(main.prefix + "§7/cod set <ID> <blue/red>");
        }
    }
}
