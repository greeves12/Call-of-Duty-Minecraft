package com.tatemylove.COD2.Inventories;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SelectKit {
    public static Inventory mainKit = Bukkit.createInventory(null, 54, "§b§lSelect Loadout");
    public static Inventory primary = Bukkit.createInventory(null, 54, "§a§lPrimary");
    public static Inventory secondary = Bukkit.createInventory(null, 54, "§6§lSecondary");
    public static Inventory perks = Bukkit.createInventory(null, 54, "§6§lPerks");

    public void createKit(Player p){
        mainKit = Bukkit.createInventory(p, 54, "§b§lSelect Loadout");


        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6§lClick to Enter");

        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("§4§lClick to Exit");
        mainKit.setItem(1, getMaterial(Material.WOODEN_HOE, "§c§lPrimary Weapons", lore));
        mainKit.setItem(4, getMaterial(Material.ENCHANTED_GOLDEN_APPLE, "§7§lPerks", lore));
        mainKit.setItem(7, getMaterial(Material.WOODEN_SHOVEL, "§7§lSecondary Weapons", lore));
        mainKit.setItem(49, getMaterial(Material.EMERALD, "§2§lExit",lore2 ));

        p.openInventory(mainKit);

    }

   /* public void createPrimary(Player p){
        primary = Bukkit.createInventory(p, 54, "§a§lPrimary");

        ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());
        p.sendMessage(String.valueOf(guns));
        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Primary").equals(s)){
                a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
            }

            primary.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
        }
        p.openInventory(primary);

    }*/

    public void createSecondary(Player p){
        secondary = Bukkit.createInventory(null, 54, "§6§lSecondary");

        ArrayList<String> guns = Main.ownedSecondary.get(p.getUniqueId().toString());
        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Secondary").equals(s)){
             a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
            }
            secondary.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
        }
        p.openInventory(secondary);

    }

    public void createPErks(Player p){
        perks = Bukkit.createInventory(null, 54, "§6§lPerks");

        ArrayList<String> guns = Main.ownedPerks.get(p.getUniqueId().toString());
        for(String s : guns){
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Perk").equals(s)){
                a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
            }
            if(s.equals("§7§nScavenger")){
                perks.addItem(getMaterial(Material.ENDER_EYE, "§7§nScavenger", a));
            }else if(s.equals("§6§nFeatherWeight")){
                perks.addItem(getMaterial(Material.ELYTRA, "§6§nFeatherWeight", a));
            }

        }
        p.openInventory(perks);

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
