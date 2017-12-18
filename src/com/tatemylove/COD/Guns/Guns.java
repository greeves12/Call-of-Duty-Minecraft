package com.tatemylove.COD.Guns;

import com.tatemylove.COD.Files.GunFile;
import com.tatemylove.COD.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Guns {
    Main main;
    private Guns instance = null;
    public  HashMap<ItemStack, String> type = new HashMap<ItemStack, String>();
    public Inventory tryGuns = Bukkit.createInventory(null, 54, "§8§lTry Guns");
    public Inventory tryPrimary = Bukkit.createInventory(null, 54, "§b§lPrimary Guns");
    public Inventory trySecondary = Bukkit.createInventory(null, 54, "§d§lSecondary Guns");


    public Guns(Main m){
        main = m;
        instance = Guns.this;
    }

    public void createMainMenu(Player p){
        ItemStack stack = new ItemStack(Material.WOOD_HOE);
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setDisplayName("§3§lPrimary Guns");
        stackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6§lClick to Enter");
        stackMeta.setLore(lore);
        stack.setItemMeta(stackMeta);
        tryGuns.setItem(1, stack);

        ItemStack stack1 = new ItemStack(Material.WOOD_SPADE);
        ItemMeta meta = stack1.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        meta.setDisplayName("§4§lSecondary Guns");
        stack1.setItemMeta(meta);
        tryGuns.setItem(7, stack1);

        ItemStack exit = new ItemStack(Material.EMERALD);
        ItemMeta eMeta = exit.getItemMeta();
        eMeta.setDisplayName("§e§lExit");
        ArrayList<String> lorey = new ArrayList<>();
        lorey.add("§6§lClick to Exit");
        eMeta.setLore(lorey);
        exit.setItemMeta(eMeta);
        tryGuns.setItem(49, exit);

        p.openInventory(tryGuns);
    }
    public void loadGuns(){
        for (int i = 0;GunFile.getData().contains("Guns." + i); i++ ) {

            String type = GunFile.getData().getString("Guns." + i + ".POS");

            if(type.equalsIgnoreCase("PRIMARY")){
            String ammoAmount = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoAmount");
            String gunName = GunFile.getData().getString("Guns." + i + ".Gun.GunName");
            String ammoName = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoName");
            String gunData = GunFile.getData().getString("Guns." + i + ".Gun.GunData");
            String ammoData = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoData");


            String cost = GunFile.getData().getString("Guns." + i + ".Cost");
            String levelUnlock = GunFile.getData().getString("Guns." + i + ".Level");

            ItemStack gun = new ItemStack(Material.getMaterial(gunData), 1);
            ItemMeta meta = gun.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(gunName + " §e(" + type + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("§bAmmo: " + ammoAmount);
            lore.add("§2Level: " + levelUnlock);

            meta.setLore(lore);
            gun.setItemMeta(meta);

            tryPrimary.setItem(i, gun);
            }else if(type.equals("SECONDARY")){
                String ammoAmount = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoAmount");
                String gunName = GunFile.getData().getString("Guns." + i + ".Gun.GunName");
                String ammoName = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoName");
                String gunData =  GunFile.getData().getString("Guns." + i + ".Gun.GunData");

                String cost = GunFile.getData().getString("Guns." + i + ".Cost");
                String levelUnlock = GunFile.getData().getString("Guns." + i + ".Level");

                ItemStack gun = new ItemStack(Material.getMaterial(gunData.toUpperCase()));
                ItemMeta meta = gun.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.setDisplayName(gunName + " §e(" + type + ")");

                ArrayList<String> lore = new ArrayList<>();
                lore.add("§bAmmo: " + ammoName);
                lore.add("§bLevel: " + levelUnlock);

                meta.setLore(lore);
                gun.setItemMeta(meta);

                trySecondary.setItem(i, gun);
            }
        }
    }

    public void saveGun( ItemStack gun, ItemStack ammo, Integer cost, Integer level, Integer ammoAmount, String primosec){
        int ID = 0;

        while(!(GunFile.getData().get("Guns." + ID) == null)){
            ID++;
        }
        GunFile.getData().set("Guns." + ID + ".Gun.GunData", gun.getType().toString());
        GunFile.getData().set("Guns." + ID + ".Gun.GunName", gun.getItemMeta().getDisplayName());
        GunFile.getData().set("Guns." + ID + ".Ammo.AmmoAmount", ammoAmount);
        GunFile.getData().set("Guns." + ID + ".Ammo.AmmoData", ammo.getType().toString());
        GunFile.getData().set("Guns." + ID + ".Ammo.AmmoName", ammo.getItemMeta().getDisplayName());

        GunFile.getData().set("Guns." + ID + ".Cost", cost);
        GunFile.getData().set("Guns." + ID + ".Level", level);
        GunFile.getData().set("Guns." + ID + ".POS", primosec.toUpperCase());

        GunFile.saveData();
        GunFile.reloadData();

        loadGuns();
    }
}
