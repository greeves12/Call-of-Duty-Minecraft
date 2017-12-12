package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.GracePeriod;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    GracePeriod gracePeriod;
    Main main;

    public MoveListener(GracePeriod g, Main m){
        gracePeriod = g;
        main  = m;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location f = e.getFrom();
        Location t = e.getTo();

        if(main.PlayingPlayers.contains(p)) {
            if (gracePeriod.timeuntilstart > 1) {
                if((f.getBlockX() != t.getBlockX()) || (f.getBlockY() != t.getBlockY()) || f.getBlockZ() != t.getBlockZ()){
                    e.setTo(f);
                }
            }
        }
    }
}
