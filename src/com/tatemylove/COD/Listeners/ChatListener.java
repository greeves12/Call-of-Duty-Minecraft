package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    Main main;

    public ChatListener(Main m){
        main = m;
    }
    @EventHandler
    public void filterChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        PlayerLevels levels = new PlayerLevels(main);
        int level = levels.getLevel(p);

        for(Player pp : Bukkit.getServer().getOnlinePlayers()){
            if((main.PlayingPlayers.contains(pp)) && (main.PlayingPlayers.contains(p))){
                pp.sendMessage( "§6[Lvl " + level + "]§d " + p.getName() +": §a" +  e.getMessage());
                e.setCancelled(true);
            }
            if((!main.PlayingPlayers.contains(pp)) && (!main.PlayingPlayers.contains(p))){
                pp.sendMessage("<" + p.getName() + "> " + e.getMessage());
                e.setCancelled(true);
            }
        }
    }
}
