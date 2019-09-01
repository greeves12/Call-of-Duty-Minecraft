package com.tatemylove.COD2.Arenas;

import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GetArena {
    private String currentArena;

    private  String chooseNextArena(){

        Random rand = new Random();
        ArrayList<String> arena = new ArrayList<>();
        arena.addAll(Main.arenas);

        return arena.get(rand.nextInt(arena.size()));
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

    public static  Location getBlueSpawn(Player p, String arena){
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

    public static Location getRedSpawn(Player p, String arena){
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
