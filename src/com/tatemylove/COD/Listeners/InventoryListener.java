package com.tatemylove.COD.Listeners;

import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

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
                    p.openInventory(guns.trySecondary);
                }
                e.setCancelled(true);
            }
        }
    }
}
