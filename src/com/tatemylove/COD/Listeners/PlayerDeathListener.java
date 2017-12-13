package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    Main main;
    public PlayerDeathListener(Main m){
        main = m;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player pp = e.getEntity().getKiller();

        TDM tdm = new TDM(main);
        if((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
            if (((tdm.blueTeam.contains(p) && (tdm.redTeam.contains(pp))) || (tdm.redTeam.contains(p)) && (tdm.blueTeam.contains(pp)))) {
                int kills = main.kills.get(pp.getName());
                kills++;
                main.kills.put(p.getName(), kills);
            }
        }
    }
}
