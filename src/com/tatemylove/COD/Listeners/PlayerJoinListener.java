package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    Main main;
    public PlayerJoinListener (Main m){
        main = m;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(ThisPlugin.getPlugin().getConfig().getBoolean("auto-join")){
            main.WaitingPlayers.add(e.getPlayer());
            e.getPlayer().sendMessage(main.prefix);
        }
    }

}
