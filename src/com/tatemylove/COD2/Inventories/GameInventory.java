package com.tatemylove.COD2.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GameInventory {
    public static final ItemStack knife = new ItemStack(Material.IRON_SWORD);

    public static void settUp(){
        ItemMeta meta = knife.getItemMeta();
        meta.setDisplayName("§c§lKnife");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6The 'Get Rekt Scrub'");
        meta.setLore(lore);
        knife.setItemMeta(meta);
    }

}
