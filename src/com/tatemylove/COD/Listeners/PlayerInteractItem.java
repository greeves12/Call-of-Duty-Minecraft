package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.KillStreaks.Moab;
import com.tatemylove.COD.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractItem implements Listener {

    Main main;
    private PlayerInteractItem interactEvent = null;

    public PlayerInteractItem(Main m){
        main = m;
        interactEvent = PlayerInteractItem.this;
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        AttackDogs dogs = new AttackDogs(main);
        dogs.onInteract(e);

        Moab moab = new Moab(main);
        moab.onPlayerIneteract(e);
    }
}
