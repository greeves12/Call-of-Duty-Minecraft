package com.tatemylove.COD.Inventories;

import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.OwnedFile;
import com.tatemylove.COD.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Kits {
    private HashMap<String, Inventory> kit = new HashMap<>();

    Main main;
    private static Kits kitsSS= null;

    public Inventory inv = Bukkit.createInventory(null, 54, "§7§lWeapons Crate");

    public Kits (Main m){
        main = m;
        kitsSS = Kits.this;
    }

    public void loadKits(final Player p){
        if(kit.get(p.getName()) == null){
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
    public void loadInventory(Player p){
        if(!kit.containsKey(p.getName())) {
            inv = Bukkit.createInventory(p, 54, "§7§lWeapons Crate");

            for (int ID = 0; OwnedFile.getData().contains(p.getUniqueId().toString() +"."+ ID); ID++) {

                String ammoAmount = OwnedFile.getData().getString(p.getUniqueId().toString() +"."+ ID + ".Ammo.AmmoAmount");
                String gunName = OwnedFile.getData().getString(p.getUniqueId().toString()+"." + ID + ".Gun.GunName");
                String ammoName = OwnedFile.getData().getString(p.getUniqueId().toString()+"." + ID + ".Ammo.AmmoName");
                String gunData = OwnedFile.getData().getString(p.getUniqueId().toString()+"." + ID + ".Gun.GunData");
                String ammoData = OwnedFile.getData().getString(p.getUniqueId().toString()+"." + ID + ".Ammo.AmmoData");

                ArrayList<String> lore = new ArrayList<>();
                lore.add("§cAmmo Capacity: §2" + ammoAmount);
                lore.add("§6§l<< Click to Select >>");

                inv.setItem(ID, getMaterial(Material.getMaterial(gunData.toUpperCase()), gunName + "§b(PRIMARY)", lore));
            }
            p.openInventory(inv);
        }
    }
    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta me = s.getItemMeta();
        me.setDisplayName(name);
        me.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        me.setLore(lore);
        s.setItemMeta(me);
        return s;
    }
}
