package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Events.CODJoinEvent;
import com.tatemylove.COD2.Events.CODLeaveEvent;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

public class PlayerJoin implements Listener {
    private HashMap<UUID, ItemStack[]> inv = new HashMap<>();
    private HashMap<UUID, Location> loc = new HashMap<>();

    Main main;
    public PlayerJoin(Main main){
        this.main=main;
    }


    @EventHandler
    public void playerLeave(PlayerQuitEvent e){
        if(Main.PlayingPlayers.contains(e.getPlayer())){
            Main.PlayingPlayers.remove(e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(e.getPlayer()));

        }else if(Main.WaitingPlayers.contains(e.getPlayer())){
            Main.WaitingPlayers.remove(e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(e.getPlayer()));
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void codJoin(CODJoinEvent e){
        inv.put(e.getPlayer().getUniqueId(), e.getPlayer().getInventory().getContents());
        e.getPlayer().getInventory().clear();
        GameInventory.lobbyInv(e.getPlayer());
        loc.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
    }

    @EventHandler (priority = EventPriority.LOW)
    public void codLeave(CODLeaveEvent e){
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setContents(inv.get(e.getPlayer().getUniqueId()));
        e.getPlayer().teleport(loc.get(e.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void noBuild(BlockPlaceEvent e){
        if(Main.PlayingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void noBreak(BlockBreakEvent e){
        if(Main.PlayingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!PlayerData.getData().contains("Players." + e.getPlayer().getUniqueId().toString())){

            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Guns", new ArrayList<String>());
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Level", 1);
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Perks", new ArrayList<String>());

            PlayerData.saveData();
            PlayerData.reloadData();

            Main.ownedGuns.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
            Main.ownedSecondary.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
            Main.ownedPerks.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
            Main.unlockedAchievements.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
        }

        if(main.getConfig().getBoolean("BungeeCord.Enabled")){
            Main.WaitingPlayers.add(e.getPlayer());
            e.getPlayer().teleport(GetLocations.getLobby());
            Bukkit.getServer().getPluginManager().callEvent(new CODJoinEvent(e.getPlayer()));
            e.getPlayer().sendMessage("§eYou joined the lobby");
        }
    }



}
