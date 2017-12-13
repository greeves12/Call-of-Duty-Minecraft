package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerInteractListener implements Listener {
    Main main;
    public PlayerInteractListener(Main m){
        main = m;
    }

    @EventHandler
    public void onTouch(EntityDamageByEntityEvent e){
        Entity entity = e.getEntity();
        Entity entity1 = e.getDamager();
        TDM tdm = new TDM(main);
        if((entity instanceof Player) && (entity1 instanceof Player)){
            Player p = (Player) e.getEntity();
            Player pp = (Player) e.getDamager();
            if(!ThisPlugin.getPlugin().getConfig().getBoolean("friend-fire")) {
                if ((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
                    if (tdm.blueTeam.contains(p) && (tdm.blueTeam.contains(pp))) {
                        e.setCancelled(true);
                    } else if (tdm.redTeam.contains(p) && (tdm.redTeam.contains(pp))){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
