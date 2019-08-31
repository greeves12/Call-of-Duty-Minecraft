package com.tatemylove.COD2.Guns;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.print.DocFlavor;
import java.util.ArrayList;

public class BuyGuns {

    public static Inventory buyPrimary = Bukkit.createInventory(null, 54, "§a§lPrimary Weapons");
    public static Inventory buySecondary = Bukkit.createInventory(null, 54, "§d§lSecondary Weapons");
    public static Inventory buySplodes = Bukkit.createInventory(null, 54, "§d§lSExplosives");
    public static Inventory mainStore = Bukkit.createInventory(null, 54, "§3§lPurchase Weapons");

    public  void loadMenu(Player p){
        mainStore = Bukkit.createInventory(null, 54, "§3§lPurchase Weapons");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6§lClick to Enter");

        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("§4§lClick to Exit");
        mainStore.setItem(1, getMaterial(Material.WOODEN_HOE, "§c§lPrimary Weapons", lore));
        mainStore.setItem(7, getMaterial(Material.WOODEN_SHOVEL, "§7§lSecondary Weapons", lore));
        mainStore.setItem(4, getMaterial(Material.ENCHANTED_GOLDEN_APPLE, "§3§lPerks", lore));
        mainStore.setItem(49, getMaterial(Material.EMERALD, "§2§lExit",lore2 ));

        p.openInventory(mainStore);
    }

    public  void loadPrimary(Player p){
        buyPrimary = Bukkit.createInventory(p, 54, "§a§lPrimary Weapons");

        for(String s : Main.guns){
            if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("PRIMARY")){
               String gunMaterial =  GunsFile.getData().getString("Guns." + s + ".GunMaterial");
             //  String ammoMaterial=  GunsFile.getData().getString("Guns." + s+".AmmoMaterial");
             //  int amount = GunsFile.getData().getInt("Guns." + s + ".AmmoAmount");
             //  String ammoName =  GunsFile.getData().getString("Guns." + s + ".AmmoName");
               int level = GunsFile.getData().getInt("Guns." + s + ".Level");
               double cost = GunsFile.getData().getDouble("Guns." + s + ".Cost");
             //  String type = GunsFile.getData().getString("Guns." + s + ".Type");

               ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

               ArrayList<String> lore = new ArrayList<>();
               lore.add("§3§lLevel: §2" + level);
               //lore.add("§3§lClip Size: §2" + amount);

               if(ThisPlugin.getPlugin().getConfig().getBoolean("SwiftEconomy.Enabled")){
                   lore.add("§3§lCost: §2" + cost);
               }
                if(guns.contains(s)){
                    lore.add("§d§lPurchased: §aTrue");
                }else{
                    lore.add("§d§lPurchased: §cFalse");
                }
               buyPrimary.addItem(getMaterial(Material.getMaterial(gunMaterial.toUpperCase()),   s, lore));
            }
        }
        p.openInventory(buyPrimary);
    }

    public  void loadSecondary(Player p){
        buySecondary = Bukkit.createInventory(p, 54, "§d§lSecondary Weapons");

        for(String s : Main.guns){
            if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("SECONDARY")){
                String gunMaterial =  GunsFile.getData().getString("Guns." + s + ".GunMaterial");
             //   String ammoMaterial=  GunsFile.getData().getString("Guns." + s+".AmmoMaterial");
                int amount = GunsFile.getData().getInt("Guns." + s + ".AmmoAmount");
             //   String ammoName =  GunsFile.getData().getString("Guns." + s + ".AmmoName");
                int level = GunsFile.getData().getInt("Guns." + s + ".Level");
                double cost = GunsFile.getData().getDouble("Guns." + s + ".Cost");
             //   String type = GunsFile.getData().getString("Guns." + s + ".Type");

                ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

                ArrayList<String> lore = new ArrayList<>();
                lore.add("§3§lLevel: §2" + level);
                lore.add("§3§lClip Size: §2" + amount);
                if(ThisPlugin.getPlugin().getConfig().getBoolean("SwiftEconomy.Enabled")){
                    lore.add("§3§lCost: §2" + cost);
                }
                if(guns.contains(s)){
                    lore.add("§d§lPurchased: §aTrue");
                }else{
                    lore.add("§d§lPurchased: §cFalse");
                }
                buySecondary.addItem(getMaterial(Material.getMaterial(gunMaterial.toUpperCase()), s, lore));
            }
        }
        p.openInventory(buySecondary);
    }

    public void loadSplode(Player p){
        buySplodes = Bukkit.createInventory(p, 54, "§d§lSecondary Weapons");

        for(String s : Main.guns){
            if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("SPLODE")){
                String gunMaterial =  GunsFile.getData().getString("Guns." + s + ".GunMaterial");
               // String ammoMaterial=  GunsFile.getData().getString("Guns." + s+".AmmoMaterial");
                int amount = GunsFile.getData().getInt("Guns." + s + ".AmmoAmount");
               // String ammoName =  GunsFile.getData().getString("Guns." + s + ".AmmoName");
                int level = GunsFile.getData().getInt("Guns." + s + ".Level");
                double cost = GunsFile.getData().getDouble("Guns." + s + ".Cost");
              //  String type = GunsFile.getData().getString("Guns." + s + ".Type");

                ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

                ArrayList<String> lore = new ArrayList<>();
                lore.add("§3§lLevel: §2" + level);
                lore.add("§3§lAmount: §2" + amount);
                if(ThisPlugin.getPlugin().getConfig().getBoolean("SwiftEconomy.Enabled")){
                    lore.add("§3§lCost: §2" + cost);
                }
                if(guns.contains(s)){
                    lore.add("§d§lPurchased: §aTrue");
                }else{
                    lore.add("§d§lPurchased: §cFalse");
                }
                buySplodes.addItem(getMaterial(Material.getMaterial(gunMaterial.toUpperCase()), s, lore));
            }
        }
        p.openInventory(buySecondary);
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
