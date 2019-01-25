package com.tatemylove.COD.Citizens;

import com.tatemylove.COD.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TryGuns {
    Main main;
    private static TryGuns tryg= null;

    public TryGuns (Main m){
        main = m;
        tryg = TryGuns.this;
    }

    public void createNPC(Player p){
       NPCRegistry registry = CitizensAPI.getNPCRegistry();
       NPC npc = registry.createNPC(EntityType.valueOf(main.getConfig().getString("GunRange.npc-type").toUpperCase()), ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("GunRange.npc-name")));
       npc.spawn(p.getLocation());
    }
}
