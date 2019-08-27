package com.tatemylove.COD2.Arenas;

import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GetArena {
    private static String currentArena;

    private  String chooseNextArena(){
        ArrayList<String> arena = new ArrayList<>();
        arena.addAll(Main.arenas);
        Collections.shuffle(arena);

        if(Main.onGoingArenas.contains(arena.get(0))){
            chooseNextArena();
        }
        return arena.get(0);
    }

    public   String getNextArena()
    {
        currentArena = chooseNextArena();
        return currentArena;
    }

    public  String getCurrentArena()
    {
        return currentArena;
    }

    public  Location getBlueSpawn(Player p, String arena){
        final double x;
        final double y;
        final double z;
        final World world;
        final float yaw;
        final float pitch;
        world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + arena + ".Spawns.Blue.World"));
        x = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.X");
        y = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Y");
        z = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Z");
        yaw = (float) ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Yaw");
        pitch = (float) ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Pitch");

        Location blueSpawn = new org.bukkit.Location(world, x,y,z);

        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        return blueSpawn;
    }

    public  Location getRedSpawn(Player p, String arena){
        final double x;
        final double y;
        final double z;
        final World world;
        final float yaw;
        final float pitch;
        world = Bukkit.getWorld(ArenasFile.getData().getString("Arenas." + arena + ".Spawns.Red.World"));
        x = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Red.X");
        y = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Red.Y");
        z = ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Red.Z");
        yaw = (float) ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Yaw");
        pitch = (float) ArenasFile.getData().getDouble("Arenas." + arena + ".Spawns.Blue.Pitch");

        Location redSpawn = new Location(world, x, y, z);

        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        return redSpawn;
    }
}
