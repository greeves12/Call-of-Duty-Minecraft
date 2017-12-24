package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Napalm {
    private ItemStack napalm = new ItemStack(Material.SUGAR);

    Main main;
    private static Napalm napalms = null;
    public Napalm(Main m){
        main = m;
        napalms = Napalm.this;
    }

    public void settUp(){
        ItemMeta meta = napalm.getItemMeta();
        meta.setDisplayName("§bAirStrike");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§dAirStrike waiting on your go");
        meta.setLore(lore);
        napalm.setItemMeta(meta);
    }

    public void onEntityKill(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player pp = e.getEntity().getKiller();

        TDM tdm = new TDM(main);

        if((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))){
            if(Main.killStreak.get(p.getName()) == 5){
                p.getInventory().addItem(napalm);
                p.sendMessage(main.prefix + "§6AirStrike waiting on your go!");
            }
        }
    }
}
