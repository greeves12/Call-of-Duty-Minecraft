package com.tatemylove.COD2.Locations;

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

}
