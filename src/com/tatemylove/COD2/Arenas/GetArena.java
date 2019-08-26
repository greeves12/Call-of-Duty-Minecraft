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

    private static String chooseNextArena(){
        ArrayList<String> arena = new ArrayList<>();
        arena.addAll(Main.arenas);
        Collections.shuffle(arena);

        return arena.get(0);

    }

    public static  String getNextArena()
    {
        currentArena = chooseNextArena();
        return currentArena;
    }

    public static String getCurrentArena()
    {
        return currentArena;
    }

    public static Location getBlueSpawn(Player p){
        final double x;
        final double y;
        final double z;
        final World world;
        final float yaw;
        final float pitch;
        world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Blue.World"));
        x = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.X");
        y = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Y");
        z = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Z");
        yaw = (float) ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Yaw");
        pitch = (float) ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Pitch");

        Location blueSpawn = new org.bukkit.Location(world, x,y,z);

        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        return blueSpawn;
    }

    public static Location getRedSpawn(Player p){
        final double x;
        final double y;
        final double z;
        final World world;
        final float yaw;
        final float pitch;
        world = Bukkit.getWorld(ArenasFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Red.World"));
        x = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.X");
        y = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Y");
        z = ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Z");
        yaw = (float) ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Yaw");
        pitch = (float) ArenasFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Pitch");

        Location redSpawn = new Location(world, x, y, z);

        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        return redSpawn;
    }
}
