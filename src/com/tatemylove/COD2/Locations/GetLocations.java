package com.tatemylove.COD2.Locations;

import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Files.LobbyFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GetLocations {
    public static Location getLobby(){
        World world = Bukkit.getWorld(LobbyFile.getData().getString("Lobby.World"));
        double x = LobbyFile.getData().getDouble("Lobby.X");
        double y = LobbyFile.getData().getDouble("Lobby.Y");
        double z = LobbyFile.getData().getDouble("Lobby.Z");


        return new Location(world, x, y, z);

    }

    public static Location getBlueFlag(String name){
        String world = ArenasFile.getData().getString("Arenas." + name + ".Flag.Blue.World");
        double x = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Blue.X");
        double y = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Blue.Y");
        double z = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Blue.Z");

        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public static Location getRedFlag(String name){
        String world = ArenasFile.getData().getString("Arenas." + name + ".Flag.Red.World");
        double x = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Red.X");
        double y = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Red.Y");
        double z = ArenasFile.getData().getDouble("Arenas." + name + ".Flag.Red.Z");

        return new Location(Bukkit.getWorld(world), x, y, z);
    }

}
