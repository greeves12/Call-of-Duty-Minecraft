package com.tatemylove.COD.Arenas;

import com.tatemylove.COD.Files.ArenaFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GetArena {
    public GetArena(){
    }
    private static int CurrentArena;

    private static int chooseNextMap()
    {
        ArrayList<Integer> numbers = new ArrayList();
        for (int k = 0; ArenaFile.getData().contains("Arenas." + k + ".Name"); k++) {
            numbers.add(k);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }

    public static  int getNextArena()
    {
        CurrentArena = chooseNextMap();
        return CurrentArena;
    }

    public static int getCurrentArena()
    {
        return CurrentArena;
    }

    public static Location getBlueSpawn(Player p){
        final double x;
        final double y;
        final double z;
        final World world;
        final float yaw;
        final float pitch;
        world = Bukkit.getServer().getWorld(ArenaFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Blue.World"));
        x = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.X");
        y = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Y");
        z = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Z");
        yaw = (float) ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Yaw");
        pitch = (float) ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Pitch");

        Location blueSpawn = new Location(world, x,y,z);

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
        world = Bukkit.getWorld(ArenaFile.getData().getString("Arenas." + getNextArena() + ".Spawns.Red.World"));
        x = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.X");
        y = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Y");
        z = ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Red.Z");
        yaw = (float) ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Yaw");
        pitch = (float) ArenaFile.getData().getDouble("Arenas." + getNextArena() + ".Spawns.Blue.Pitch");

        Location redSpawn = new Location(world, x, y, z);

        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        return redSpawn;
    }
}
