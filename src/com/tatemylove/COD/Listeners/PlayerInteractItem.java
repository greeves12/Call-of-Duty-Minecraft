package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.KillStreaks.GetPlayersOnOtherTeam;
import com.tatemylove.COD.KillStreaks.Moab;
import com.tatemylove.COD.KillStreaks.Napalm;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerInteractItem implements Listener {

    Main main;
    private PlayerInteractItem interactEvent = null;

    public PlayerInteractItem(Main m) {
        main = m;
        interactEvent = PlayerInteractItem.this;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        AttackDogs dogs = new AttackDogs(main);
        dogs.onInteract(e);

        Napalm napalm = new Napalm(main);
        napalm.onInteract(e);

        Moab moab = new Moab(main);
        moab.onPlayerIneteract(e);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(main.PlayingPlayers.contains(e.getPlayer())){
            if(main.getConfig().getBoolean("no-place")){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void noBreak(BlockBreakEvent e){
        if(main.PlayingPlayers.contains(e.getPlayer())){
            if(main.getConfig().getBoolean("no-break")){
                e.setCancelled(true);
            }
        }
    }
}

