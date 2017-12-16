package com.tatemylove.COD.Listeners;

import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Files.GunFile;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Main;
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
            }
        }
    }
}
