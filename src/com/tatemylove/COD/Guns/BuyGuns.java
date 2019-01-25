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

public class BuyGuns {
    Main main;
    public Inventory buyPrimary = Bukkit.createInventory(null, 54, "§c§lPrimary Weapons");
    public Inventory buySecondary = Bukkit.createInventory(null, 54, "§7§lSecondary Weapons");
    public Inventory mainStore = Bukkit.createInventory(null, 54, "§5§lPurchase Weapons");
    private HashMap<String, Inventory> invs = new HashMap<>();

    private static BuyGuns buyGuns = null;

    public BuyGuns (Main m){
        main = m;
        buyGuns = BuyGuns.this;
    }

    public void loadMenu(Player p){
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6§lClick to Enter");

        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("§6§lClick to Exit");
        mainStore.setItem(1, getMaterial(Material.WOODEN_HOE, "§c§lPrimary Weapons", lore));
        mainStore.setItem(7, getMaterial(Material.WOODEN_SHOVEL, "§7§lSecondary Weapons", lore));
        mainStore.setItem(49, getMaterial(Material.EMERALD, "§2§lExit",lore2 ));

        p.openInventory(mainStore);
    }

    public void loadPrimary(Player p){
        if(!invs.containsKey(p.getName())){
            //buyPrimary = Bukkit.createInventory(p, 54, "§c§lPrimary Weapons");

            for(int i = 0; GunFile.getData().contains("Guns." + i); i++){
                String type = GunFile.getData().getString("Guns." + i + ".POS");

                if(type.equalsIgnoreCase("PRIMARY")){
                    String ammoAmount = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoAmount");
                    String gunName = GunFile.getData().getString("Guns." + i + ".Gun.GunName");
                    String ammoName = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoName");
                    String gunData = GunFile.getData().getString("Guns." + i + ".Gun.GunData");
                    String ammoData = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoData");


                    String cost = GunFile.getData().getString("Guns." + i + ".Cost");
                    String levelUnlock = GunFile.getData().getString("Guns." + i + ".Level");

                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("§3§lLevel: §2" + levelUnlock);
                    lore.add("§3§lClip Size: §2" + ammoAmount);
                    if(main.getConfig().getBoolean("SwiftEconomy.Enabled")){
                        lore.add("§6§lCost: §4$" + cost);
                    }
                    buyPrimary.setItem(i, getMaterial(Material.getMaterial(gunData.toUpperCase()),gunName + " §5(PRIMARY)", lore));
                }
            }
            p.openInventory(buyPrimary);
        }
    }

    public void loadSecondary(Player p){
        for(int i = 0; GunFile.getData().contains("Guns." + i); i++){
            String type = GunFile.getData().getString("Guns." + i + ".POS");

            if(type.equalsIgnoreCase("SECONDARY")){
                String ammoAmount = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoAmount");
                String gunName = GunFile.getData().getString("Guns." + i + ".Gun.GunName");
                String ammoName = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoName");
                String gunData = GunFile.getData().getString("Guns." + i + ".Gun.GunData");
                String ammoData = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoData");


                String cost = GunFile.getData().getString("Guns." + i + ".Cost");
                String levelUnlock = GunFile.getData().getString("Guns." + i + ".Level");

                ArrayList<String> lore = new ArrayList<>();
                lore.add("§3§lLevel: §2" + levelUnlock);
                lore.add("§3§lClip Size: §2" + ammoAmount);
                if(main.getConfig().getBoolean("SwiftEconomy.Enabled")){
                    lore.add("§6§lCost: §4$" + cost);
                }
                buySecondary.setItem(i, getMaterial(Material.getMaterial(gunData.toUpperCase()),gunName + " §5(SECONDARY)", lore));
            }
        }
        p.openInventory(buySecondary);
    }
    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta me = s.getItemMeta();
        me.setDisplayName(name);
        me.setLore(lore);
        me.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        s.setItemMeta(me);
        return s;
    }
}
