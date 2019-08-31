package com.tatemylove.COD2.Perks;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tatemylove.COD2.Files.GunsFile;
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
import java.util.Arrays;

public class PerkMenu {
    public static Inventory inventory = Bukkit.createInventory(null, 27, "§3§lBuy Perks");

    public void createMenu(Player p){
        inventory = Bukkit.createInventory(p, 27, "§b§lBuy Perks");

        for(String s : Main.perks){
            String mat = GunsFile.getData().getString("Perks." + s + ".Material");
            ArrayList<String> arrayList = new ArrayList<>();
            for(String ss : new ArrayList<>(GunsFile.getData().getStringList("Perks." + s + ".Desc"))){
                arrayList.add(ChatColor.translateAlternateColorCodes('&', ss));
            }
            String name = GunsFile.getData().getString("Perks." + s + ".Name");
            if(Main.ownedPerks.get(p.getUniqueId().toString()).contains(GunsFile.getData().getString("Perks." + s + ".Name"))){
                arrayList.add("");
                arrayList.add("§a§lPurchased: §2True");
            }else{
                arrayList.add("");
                arrayList.add("§a§lPurchased: §cFalse");
            }

            inventory.addItem(getMaterial(Material.getMaterial(mat.toUpperCase()), name, arrayList));

        }

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
