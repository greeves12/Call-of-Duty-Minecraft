package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
        File file = new File("plugins/COD/arenas.yml");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine() == null){
                e.getPlayer().sendMessage(main.prefix + "§2COD is disabled because you don't have any Arenas!! "+"\n" + "         §4Create an arena and then type §c/cod enable");
            }

        }catch(Exception ei){

        }
    }

}
