package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Guns.BuyGuns;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;

public class InventoryInteract implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory().equals(BuyGuns.mainStore)) {
                if(Main.PlayingPlayers.contains(p)){
                    p.closeInventory();
                }
                if (e.getSlot() == 1) {
                    e.getWhoClicked().closeInventory();
                    BuyGuns buyGuns = new BuyGuns();
                    buyGuns.loadPrimary((Player) e.getWhoClicked());
                } else if (e.getSlot() == 7) {
                    e.getWhoClicked().closeInventory();
                    BuyGuns buyGuns = new BuyGuns();
                    buyGuns.loadSecondary((Player) e.getWhoClicked());

                } else if (e.getSlot() == 49) {
                    e.getWhoClicked().closeInventory();
                }
                e.setCancelled(true);
            }
            if (e.getClickedInventory().equals(BuyGuns.buyPrimary)) {
                if(Main.PlayingPlayers.contains(p)){
                    p.closeInventory();
                }
                if (e.getClickedInventory().getItem(e.getSlot()) != null) {
                    ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                    if (Main.guns.contains(meta.getDisplayName())) {

                        ArrayList<String> list = Main.ownedGuns.get(p.getUniqueId().toString());
                        if(list.contains(meta.getDisplayName())){
                            p.sendMessage(Main.prefix + "§cYou already own §4" + meta.getDisplayName());
                            p.closeInventory();
                        }else{
                            list.add(meta.getDisplayName());
                            PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Guns", list);
                            PlayerData.saveData();
                            p.sendMessage(Main.prefix + "§aPurchase of §b" + meta.getDisplayName() + " §asuccessful");
                            p.closeInventory();
                        }
                    }
                }

                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(BuyGuns.buySecondary)){
                if(Main.PlayingPlayers.contains(p)){
                    p.closeInventory();
                }
                if(e.getClickedInventory().getItem(e.getSlot()) != null){
                    ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();

                    if(Main.guns.contains(meta.getDisplayName())){
                        ArrayList<String> list = Main.ownedGuns.get(p.getUniqueId().toString());
                        if(list.contains(meta.getDisplayName())){
                            p.sendMessage(Main.prefix + "§cYou already own §4" + meta.getDisplayName());
                            p.closeInventory();
                        }else{
                            list.add(meta.getDisplayName());
                            PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Guns", list);
                            PlayerData.saveData();
                            p.sendMessage(Main.prefix + "§aPurchase of §b" + meta.getDisplayName() + " §asuccessful");
                            p.closeInventory();
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}

