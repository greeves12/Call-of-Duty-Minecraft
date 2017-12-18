package com.tatemylove.COD.Inventories;

import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Kits {
    public HashMap<Player, Inventory> kit = new HashMap<>();

    Main main;
    private static Kits kitsSS= null;

    public Kits (Main m){
        main = m;
        kitsSS = Kits.this;
    }

    public void loadKits(final Player p){
        if(kit.get(p) == null){
            String primary = KitFile.getData().getString(p.getUniqueId().toString() + ".Primary");
            String primaryName = KitFile.getData().getString(p.getUniqueId().toString() + ".PrimName");
            String secondary = KitFile.getData().getString(p.getUniqueId().toString() + ".Secondary");
            String secondName = KitFile.getData().getString(p.getUniqueId().toString() + ".SecondName");


            ItemStack prim = new ItemStack(Material.getMaterial(primary.toUpperCase()));
            ItemMeta primM = prim.getItemMeta();
            primM.setDisplayName(ChatColor.translateAlternateColorCodes('&',primaryName));
            prim.setItemMeta(primM);

            kit.get(p).setItem(3, prim);

            ItemStack second = new ItemStack(Material.getMaterial(secondary.toUpperCase()));
            ItemMeta meta = second.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', secondName));
            second.setItemMeta(meta);

            kit.get(p).setItem(6, second);

        }
    }
}
