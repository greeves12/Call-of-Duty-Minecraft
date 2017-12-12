package com.tatemylove.COD.Arenas;

import com.tatemylove.COD.Files.ArenaFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;

public class GetArena {
    public static int CurrentArena;

    public int chooseNextMap()
    {
        ArrayList<Integer> numbers = new ArrayList();
        for (int k = 0; ArenaFile.getData().contains("Arenas." + k + ".Name"); k++) {
            numbers.add(k);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }

    public  int getNextArena()
    {
        CurrentArena = chooseNextMap();
        return CurrentArena;
    }

    public  int getCurrentArena()
    {
        return CurrentArena;
    }

    public  Location getBlueSpawn(){
        final double x;
        final double y;
        final double z;
        final World world;
        world = Bukkit.getServer().getWorld(ArenaFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Blue.World"));
        x = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.X");
        y = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Y");
        z = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Z");

        Location blueSpawn = new Location(world, x,y,z);

        return blueSpawn;
    }

    public  Location getRedSpawn(){
        final double x;
        final double y;
        final double z;
        final World world;
        world = Bukkit.getWorld(ArenaFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Red.World"));
        x = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.X");
        y = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Y");
        z = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Z");

        Location redSpawn = new Location(world, x, y, z);
        return redSpawn;
    }
}
