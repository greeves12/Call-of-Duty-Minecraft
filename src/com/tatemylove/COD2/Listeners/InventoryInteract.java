package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Guns.BuyGuns;
import com.tatemylove.COD2.Inventories.SelectKit;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.Perks.PerkMenu;
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
                }else if(e.getSlot() == 4){
                    e.getWhoClicked().closeInventory();
                    new PerkMenu().createMenu(p);
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
                        int level = PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level");
                        if(level < GunsFile.getData().getInt("Guns." + meta.getDisplayName() + ".Level")){
                            p.sendMessage(Main.prefix + "§cYou need to be level §4" + GunsFile.getData().getInt("Guns." + meta.getDisplayName() + ".Level"));
                            return;
                        }
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
                        int level = PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level");
                        if(level < GunsFile.getData().getInt("Guns." + meta.getDisplayName() + ".Level")){
                            p.sendMessage(Main.prefix + "§cYou need to be level §4" + GunsFile.getData().getInt("Guns." + meta.getDisplayName() + ".Level"));
                            return;
                        }
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

            if(e.getClickedInventory().equals(PerkMenu.inventory)){

                if(e.getClickedInventory().getItem(e.getSlot()) != null){
                    ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                    ArrayList<String> list = Main.ownedPerks.get(p.getUniqueId().toString());
                    int level = PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level");

                    if(meta.getDisplayName().equals("§7§nScavenger")){
                        if(level < 5){
                            p.sendMessage(Main.prefix + "§cYou need to be level §45");
                            e.setCancelled(true);
                            return;
                        }
                    }else if(meta.getDisplayName().equals("§6§nFeatherWeight")){
                        if(level < 10){
                            p.sendMessage(Main.prefix + "§cYou need to be level §410");
                            e.setCancelled(true);
                            return;
                        }
                    }

                    if(list.contains(meta.getDisplayName())){
                        p.sendMessage(Main.prefix + "§cYou already own §4" + meta.getDisplayName());
                        p.closeInventory();
                    }else{
                        list.add(meta.getDisplayName());
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Perks", list);
                        PlayerData.saveData();
                        p.sendMessage(Main.prefix + "§aPurchase of §b" + meta.getDisplayName() + " §asuccessful");
                        p.closeInventory();
                    }
                }

                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(SelectKit.mainKit)){
                if(e.getSlot() == 1){
                    p.closeInventory();
                    new SelectKit().createPrimary(p);
                }else if(e.getSlot() == 4){
                    p.closeInventory();
                    new SelectKit().createPErks(p);
                }else if(e.getSlot() == 7){
                    p.closeInventory();
                    new SelectKit().createSecondary(p);
                }else if(e.getSlot() == 49){
                    p.closeInventory();
                }
                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(SelectKit.perks)){
                if(e.getClickedInventory().getItem(e.getSlot()) != null){
                    ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                    if(!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Perk").equals(meta.getDisplayName())) {

                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Perk", meta.getDisplayName());
                        PlayerData.saveData();
                        p.closeInventory();
                        p.sendMessage(Main.prefix + "§aPerk selected");
                    }else{
                        p.sendMessage(Main.prefix + "§cYou already have that perk selected");
                    }
                }
                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(SelectKit.primary)){
                ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                if(!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Primary").equals(meta.getDisplayName())) {

                    PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Primary", meta.getDisplayName());
                    PlayerData.saveData();
                    p.closeInventory();
                    p.sendMessage(Main.prefix + "§aPrimary selected");
                }else{
                    p.sendMessage(Main.prefix + "§cYou already have that primary selected");
                }
                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(SelectKit.secondary)){
                ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                if(!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Secondary").equals(meta.getDisplayName())) {

                    PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Secondary", meta.getDisplayName());
                    PlayerData.saveData();
                    p.closeInventory();
                    p.sendMessage(Main.prefix + "§aSecondary selected");
                }else{
                    p.sendMessage(Main.prefix + "§cYou already have that secondary selected");
                }
                e.setCancelled(true);
            }
        }
    }
}

