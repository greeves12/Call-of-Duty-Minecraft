package com.tatemylove.COD.Citizens;

import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Main;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {
    Main main;

    private static NPCListener npcListener = null;

    public NPCListener (Main m){
        main = m;
        npcListener = NPCListener.this;
    }
    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
        Guns guns = new Guns(main);
        if(e.getNPC().getName().equals(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("npc-name")))){
            guns.createMainMenu(e.getClicker());
        }
    }
}
