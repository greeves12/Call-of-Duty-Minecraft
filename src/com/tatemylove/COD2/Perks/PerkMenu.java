package com.tatemylove.COD2.Perks;

import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PerkMenu {
    public static Inventory inventory = Bukkit.createInventory(null, 27, "§3§lPerk Menu");

    public void createMenu(Player p){
        inventory = Bukkit.createInventory(p, 27, "§b§lPerk Menu");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§3§lDesc: §4Get ammo back on kills");
        lore.add("");
        lore.add("§3§lLevel: §25");
        lore.add("");
        ArrayList<String> owned = Main.ownedPerks.get(p.getUniqueId().toString());
        if(owned.contains("§7§nScavenger")){
            lore.add("§d§lPurchased: §aTrue");
        }else{
            lore.add("§d§lPurchased: §cFalse");
        }
        inventory.setItem(0, getMaterial(Material.ENDER_EYE, "§7§nScavenger",lore));

        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("");
        lore2.add("§3§lDesc: §4Reduced fall damage and faster running speed");
        lore2.add("");
        lore2.add("§3§lLevel: §210");
        lore2.add("");
        if(owned.contains("§6§nFeatherWeight")) {
            lore2.add("§d§lPurchased: §aTrue");

        }else{
            lore2.add("§d§lPurchased: §cFalse");
        }


        inventory.setItem(1, getMaterial(Material.ELYTRA, "§6§nFeatherWeight", lore2));

        p.openInventory(inventory);
    }

    private static ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta me = s.getItemMeta();
        me.setDisplayName(name);
        me.setLore(lore);
        me.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        s.setItemMeta(me);
        return s;
    }
}
