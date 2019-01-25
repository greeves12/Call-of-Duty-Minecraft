package com.tatemylove.COD.Lobby;

import com.tatemylove.COD.Files.LobbyFile;
import com.tatemylove.COD.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GetLobby {
    Main main;
    private static GetLobby getLobbys = null;

    public GetLobby(Main m){
        main = m;
        getLobbys = GetLobby.this;
    }

    public void setLobby(Player p){
        String world = p.getLocation().getWorld().getName();
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();

        LobbyFile.getData().set("Lobby.World", world);
        LobbyFile.getData().set("Lobby.X", x);
        LobbyFile.getData().set("Lobby.Y", y);
        LobbyFile.getData().set("Lobby.Z", z);
        LobbyFile.getData().set("Lobby.Yaw", yaw);
        LobbyFile.getData().set("Lobby.Pitch", pitch);
        LobbyFile.saveData();
        LobbyFile.reloadData();

        p.sendMessage(main.prefix + "§b§lLobby Set\n §3§l§nInformation");
        p.sendMessage("§e§lWorld: §2" + world);
        p.sendMessage("§e§lX: §2" + x);
        p.sendMessage("§e§lY: §2" + y);
        p.sendMessage("§e§lZ: §2" + z);
        p.sendMessage("§e§lYaw: §2" + yaw);
        p.sendMessage("§e§lPitch: §2" + pitch);
    }
    public Location getLobby(Player p){
        final double x;
        final double y;
        final double z;
        final World world;
        final float pitch;
        final float yaw;

        world = Bukkit.getServer().getWorld(LobbyFile.getData().getString("Lobby.World"));
        x = LobbyFile.getData().getDouble("Lobby.X");
        y = LobbyFile.getData().getDouble("Lobby.Y");
        z = LobbyFile.getData().getDouble("Lobby.Z");
        pitch = (float) LobbyFile.getData().getDouble("Lobby.Pitch");
        yaw = (float) LobbyFile.getData().getDouble("Lobby.Yaw");

        Location getLobby = new Location(world, x, y, z);

        p.getLocation().setPitch(pitch);
        p.getLocation().setYaw(yaw);

        return getLobby;
    }
}
