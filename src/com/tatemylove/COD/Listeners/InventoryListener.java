package com.tatemylove.COD.Listeners;

import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Files.GunFile;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.OwnedFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Guns.BuyGuns;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Inventories.Kits;
import com.tatemylove.COD.Main;
import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryListener implements Listener {
    Main main;
    private static InventoryListener inventoryListener = null;

    public InventoryListener(Main m){
        main = m;
        inventoryListener = InventoryListener.this;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Inventory inventory = e.getInventory();
        Entity entity = e.getWhoClicked();

        if(entity instanceof Player){
            Player p = (Player) e.getWhoClicked();
            Guns guns = new Guns(main);
            BuyGuns buyGuns = new BuyGuns(main);
            Kits kits = new Kits(main);

            if(inventory.getName().equals(guns.tryGuns.getName())){
                if(e.getSlot() == 1){
                    p.closeInventory();
                    guns.loadGuns();

                    p.openInventory(guns.tryPrimary);
                }else if(e.getSlot() == 7){
                    p.closeInventory();
                    guns.loadGuns();
                    p.openInventory(guns.trySecondary);
                }else if(e.getSlot() == 49){
                    p.closeInventory();
                }
                e.setCancelled(true);
            }else if(inventory.getName().equals(guns.tryPrimary.getName())){
                for(int i = 0; GunFile.getData().contains("Guns." + i); i++) {
                    if (e.getSlot() == i){
                        CSUtility csUtility = new CSUtility();
                        ItemStack gun = csUtility.generateWeapon(GunFile.getData().getString("Guns." + i + ".Gun.GunName"));
                        p.getInventory().addItem(gun);


                        ItemStack ammo = new ItemStack(Material.getMaterial(GunFile.getData().getString("Guns." + i + ".Ammo.AmmoData")), GunFile.getData().getInt("Guns." + i + ".Ammo.AmmoAmount"));
                        ItemMeta meta = ammo.getItemMeta();
                        meta.setDisplayName("§e§lAmmo");
                        ammo.setItemMeta(meta);

                        p.getInventory().addItem(ammo);

                        p.sendMessage(main.prefix + "§aYou received " + GunFile.getData().getString("Guns." + i + ".Gun.GunName"));
                        p.closeInventory();
                    }
                }
            }else if(inventory.getName().equals(buyGuns.buyPrimary.getName())){
                for(int i = 0; GunFile.getData().contains("Guns." + i); i++){
                    if(e.getSlot() == i){
                        String ammoAmount = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoAmount");
                        String gunName = GunFile.getData().getString("Guns." + i + ".Gun.GunName");
                        String ammoName = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoName");
                        String gunData = GunFile.getData().getString("Guns." + i + ".Gun.GunData");
                        String ammoData = GunFile.getData().getString("Guns." + i + ".Ammo.AmmoData");
                        String type = GunFile.getData().getString("Guns." + i + ".POS");


                        int cost = GunFile.getData().getInt("Guns." + i + ".Cost");
                        int levelUnlock = GunFile.getData().getInt("Guns." + i + ".Level");
                        int pLevel = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Level");

                        if(pLevel >= levelUnlock){
                            if(main.getConfig().getBoolean("SwiftEconomy.Enabled")){
                                double money = SwiftEconomyAPI.playerMoney.get(p.getName());
                                if(money >= cost){
                                    int ID=0;
                                    while(!(OwnedFile.getData().get(p.getUniqueId().toString() + "." + ID) == null)){
                                        ID++;
                                    }


                                    if(GunFile.getData().getString("Guns." + i + ".POS").equalsIgnoreCase("PRIMARY")) {
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoAmount", ammoAmount);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunName", gunName);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoName", ammoName);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunData", gunData);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoData", ammoData);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Type", type);
                                        OwnedFile.saveData();
                                        OwnedFile.reloadData();
                                        p.sendMessage(main.prefix + "§aPurchase successful!");

                                        SwiftEconomyAPI api = new SwiftEconomyAPI();
                                        api.removeMoney(p, cost);
                                        p.closeInventory();
                                    }else if (type.equalsIgnoreCase("SECONDARY")) {
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoAmount", ammoAmount);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunName", gunName);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoName", ammoName);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunData", gunData);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoData", ammoData);
                                        OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Type", type);
                                        OwnedFile.saveData();
                                        OwnedFile.reloadData();

                                        SwiftEconomyAPI economyAPI = new SwiftEconomyAPI();
                                        economyAPI.removeMoney(p, cost);

                                        p.sendMessage(main.prefix + "§aPurchase successful!");
                                        p.closeInventory();
                                    }
                                }else{
                                    p.sendMessage(main.prefix + "§cPurchase failed! Not enough funds.");
                                    p.closeInventory();
                                }
                            }else{
                                int ID=0;
                                while(!(OwnedFile.getData().get(p.getUniqueId().toString() + "." + ID) == null)){
                                    ID++;
                                }


                                if(GunFile.getData().getString("Guns." + i + ".POS").equalsIgnoreCase("PRIMARY")) {
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoAmount", ammoAmount);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunName", gunName);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoName", ammoName);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunData", gunData);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoData", ammoData);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Type", type);
                                    OwnedFile.saveData();
                                    OwnedFile.reloadData();
                                    p.sendMessage(main.prefix + "§aPurchase successful!");
                                    p.closeInventory();
                                }else if (type.equalsIgnoreCase("SECONDARY")) {
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoAmount", ammoAmount);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunName", gunName);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoName", ammoName);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Gun.GunData", gunData);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Ammo.AmmoData", ammoData);
                                    OwnedFile.getData().set(p.getUniqueId().toString() + "." + ID + ".Type", type);
                                    OwnedFile.saveData();
                                    OwnedFile.reloadData();
                                    p.sendMessage(main.prefix + "§aPurchase successful!");
                                    p.closeInventory();
                                }
                            }
                        }else{
                            p.sendMessage(main.prefix + "§cYour level isn't high enough");
                            p.closeInventory();
                        }
                    }
                }
                e.setCancelled(true);
            }else if(inventory.getName().equals(buyGuns.mainStore.getName())){
                if(e.getSlot() == 1){
                    p.closeInventory();
                    buyGuns.loadPrimary(p);
                }else if(e.getSlot() == 7){
                    p.closeInventory();
                    buyGuns.loadSecondary(p);
                }
                e.setCancelled(true);
            }else if(inventory.getName().equals(kits.inv.getName())){
                for(int id = 0; OwnedFile.getData().contains(p.getUniqueId().toString() + "." + id); id++){
                    if(e.getSlot() == id){
                        String type = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Type");
                        String ammoAmount = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Ammo.AmmoAmount");
                        String gunName = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Gun.GunName");
                        String ammoName = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Ammo.AmmoName");
                        String gunData = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Gun.GunData");
                        String ammoData = OwnedFile.getData().getString(p.getUniqueId().toString() + "." + id + ".Ammo.AmmoData");

                        if(type.equalsIgnoreCase("PRIMARY")){
                            KitFile.getData().set(p.getUniqueId().toString() + ".Primary.AmmoAmount", ammoAmount);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Primary.GunName", gunName);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Primary.AmmoName", ammoName);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Primary.GunData", gunData);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Primary.AmmoData", ammoData);
                            KitFile.saveData();
                            KitFile.reloadData();
                            p.sendMessage(main.prefix + "§6§lPrimary set to §2§l" + gunName);
                            p.closeInventory();
                        }else if(type.equalsIgnoreCase("SECONDARY")){
                            KitFile.getData().set(p.getUniqueId().toString() + ".Secondary.AmmoAmount", ammoAmount);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Secondary.GunName", gunName);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Secondary.AmmoName", ammoName);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Secondary.GunData", gunData);
                            KitFile.getData().set(p.getUniqueId().toString() + ".Secondary.AmmoData", ammoData);
                            KitFile.saveData();
                            KitFile.reloadData();
                            p.sendMessage(main.prefix + "§6§lSecondary set to §2§l" + gunName);
                            p.closeInventory();
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
