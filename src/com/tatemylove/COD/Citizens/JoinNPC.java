package com.tatemylove.COD.Citizens;

import com.tatemylove.COD.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class JoinNPC {
    Main main;
    public JoinNPC(Main m){
        main = m;
    }

    public void createJoin(Player p){
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC npc = registry.createNPC(EntityType.valueOf(main.getConfig().getString("JoinNPC.npc-type").toUpperCase()), ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("JoinNPC.npc-name")));
        npc.spawn(p.getLocation());
        p.sendMessage(main.prefix + "§bNPC spawned");
    }
}
