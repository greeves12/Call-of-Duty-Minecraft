package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Achievement.AchievementAPI;
import com.tatemylove.COD2.Achievement.AchievementMenu;
import com.tatemylove.COD2.Events.CODLeaveEvent;
import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Guns.BuyGuns;
import com.tatemylove.COD2.Inventories.CreateClass;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Inventories.SelectKit;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.Perks.PerkMenu;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;

public class InventoryInteract implements Listener {

    private Integer getRow(int slot){
        if(slot >= 0 && slot <= 9){
            return 1;
        }else if(slot >= 10 && slot <=17){
            return 2;
        }else if(slot >= 18 && slot <= 26){
            return 3;
        }else if(slot >= 27 && slot <= 35){
            return 4;
        }else if(slot >= 36 && slot <=45){
            return 5;
        }else{
            return 0;
        }
    }

    private String classEnabled(Player p){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Class1");
        strings.add("Class2");
        strings.add("Class3");
        strings.add("Class4");
        strings.add("Class5");

        for(String s : strings){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return s;
            }
        }
        return null;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if(e.getClickedInventory() == null){
                return;
            }

            if(Main.AllPlayingPlayers.contains(p)) {
                if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                    e.setCancelled(true);
                }
            }

            if(e.getClickedInventory().equals(AchievementMenu.inv)){
                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(GameInventory.mainInv)){
                if(e.getSlot() == 18){
                    int level = LevelRegistryAPI.getLevel(p);
                    if(level < ThisPlugin.getPlugin().getConfig().getInt("prestige-level")){
                        p.sendMessage(Main.prefix + "§e§nPrestige failed:§c You need to be level §a" + ThisPlugin.getPlugin().getConfig().getInt("prestige-level"));
                        p.closeInventory();
                        return;
                    }
                    if(LevelRegistryAPI.getPrestige(p) >= ThisPlugin.getPlugin().getConfig().getInt("max-prestige")){
                        p.sendMessage(Main.prefix + "§cYou are max prestige");
                        p.closeInventory();
                        return;
                    }
                    LevelRegistryAPI.resetExp(p, 0);
                    LevelRegistryAPI.setLevel(p, 0);
                    LevelRegistryAPI.setPrestiege(p, LevelRegistryAPI.getPrestige(p)+1);
                    p.sendMessage(Main.prefix + "§aYou are now prestige " + LevelRegistryAPI.getPrestige(p));

                    PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes", null);
                    PlayerData.saveData();

                    PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Guns", new ArrayList<String>());
                    PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Perks", new ArrayList<String>());

                    ArrayList<String> list = new ArrayList<>();
                    list.add("Class1");
                    list.add("Class2");
                    list.add("Class3");
                    list.add("Class4");
                    list.add("Class5");


                    for(String s : list) {

                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Perk1", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Perk2", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Perk3", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Primary", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes." +s+ ".Secondary", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Splode1", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString()+".Classes."+s + ".Splode2", "");
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled", false);



                    }

                    PlayerData.saveData();

                    p.closeInventory();
                    AchievementAPI.grantAchievement(p, "Prestige");
                }else if(e.getSlot() == 12){
                    p.closeInventory();
                    new AchievementMenu().createInventory(p);
                }else if(e.getSlot() == 2){
                    p.closeInventory();
                    new CreateClass().createKit(p);
                }else if(e.getSlot() == 6){
                    p.closeInventory();
                    new BuyGuns().loadMenu(p);
                }else if(e.getSlot() == 26){
                    p.closeInventory();
                }

                e.setCancelled(true);
            }

            if(e.getClickedInventory().equals(CreateClass.perks3)){
                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk3", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk3", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk3", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk3", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk3", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk3", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk3", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk3", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk3", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk3", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }
            }

            if(e.getClickedInventory().equals(CreateClass.perks2)){
                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }
            }

            if(e.getClickedInventory().equals(CreateClass.perks)){
                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Perk1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Perk1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Perk1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Perk1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null){
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Perk1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }
            }

            if(e.getClickedInventory().equals(CreateClass.splode)){

                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){

                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Splode1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Splode1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Splode1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Splode1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Splode1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Splode1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Splode1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Splode1", null);
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Splode1", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Splode1", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }

                e.setCancelled(true);
            }

            if(e.getClickedInventory().equals(CreateClass.splode2)){

                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Splode2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Splode2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Splode2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Splode2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Splode2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Splode2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Splode2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Splode2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Splode2", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Splode2", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }

                e.setCancelled(true);
            }

            if(e.getClickedInventory().equals(CreateClass.primary)){
                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Primary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Primary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Primary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Primary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Primary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Primary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Primary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Primary","");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Primary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Primary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }
                e.setCancelled(true);
            }

           else if(e.getClickedInventory().equals(CreateClass.secondary)){
                if(CreateClass.classnumber.get(p.getUniqueId()) == 1){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Secondary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class1.Secondary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 2){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Secondary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class2.Secondary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 3){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Secondary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class3.Secondary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 4){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Secondary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class4.Secondary","");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }else if(CreateClass.classnumber.get(p.getUniqueId()) == 5){
                    if(e.getClickedInventory().getItem(e.getSlot()) != null) {
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Secondary", e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }else{
                        PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes.Class5.Secondary", "");
                        PlayerData.saveData();
                        e.getWhoClicked().closeInventory();
                        new CreateClass().createKit(p);
                    }
                }
                e.setCancelled(true);
            }

            if(e.getClickedInventory().equals(CreateClass.mainArea)) {

                    if (e.getSlot() % 9 == 1 && e.getSlot() != 0) {
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createPrimary(p, getRow(e.getSlot()));
                    } else if (e.getSlot() % 9 == 2 && e.getSlot() != 0) {
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createSecondary(p, getRow(e.getSlot()));
                    } else if (e.getSlot() % 9 == 3 && e.getSlot() != 0) {
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createSplode(p, getRow(e.getSlot()));
                    } else if (e.getSlot() % 9 == 4) {
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createSplode2(p, getRow(e.getSlot()));
                    }else if(e.getSlot() % 9 == 5){
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createPerks(p, getRow(e.getSlot()));
                    }else if(e.getSlot() % 9 == 6){
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createPerks2(p, getRow(e.getSlot()));
                    }else if(e.getSlot() % 9 == 7){
                        CreateClass.classnumber.put(p.getUniqueId(), getRow(e.getSlot()));

                        new CreateClass().createPerks3(p, getRow(e.getSlot()));
                    }else if(e.getSlot() % 9 == 8){
                        if(isEnabled(p)){
                            if(!(getEnabled(p).equals( e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()))){
                                PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes." + getEnabled(p) + ".Enabled", false);
                                PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes." + e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().substring(2) + ".Enabled", true);
                                PlayerData.saveData();
                                p.closeInventory();
                                new CreateClass().createKit(p);
                                PlayerJoin.clazz.put(p.getUniqueId(), getEnabled(p));
                            }
                        }else{
                            PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Classes." + e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().substring(2) + ".Enabled", true);
                            PlayerData.saveData();
                            p.closeInventory();
                            new CreateClass().createKit(p);
                            PlayerJoin.clazz.put(p.getUniqueId(), getEnabled(p));
                        }

                    }

                    e.setCancelled(true);


            }



            if (e.getClickedInventory().equals(BuyGuns.mainStore)) {
                if(Main.AllPlayingPlayers.contains(p)){
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
                }else if(e.getSlot() == 22){
                    e.getWhoClicked().closeInventory();
                    new BuyGuns().loadSplode(p);
                }
                e.setCancelled(true);
            }
            if (e.getClickedInventory().equals(BuyGuns.buyPrimary)) {
                if(Main.AllPlayingPlayers.contains(p)){
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

                            AchievementAPI.grantAchievement(p, "GettingUpgrade");
                        }
                    }
                }

                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(BuyGuns.buySecondary)){
                if(Main.AllPlayingPlayers.contains(p)){
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
                            AchievementAPI.grantAchievement(p, "GettingUpgrade");
                        }
                    }
                }
                e.setCancelled(true);
            }
            if(e.getClickedInventory().equals(BuyGuns.buySplodes)){
                if(Main.AllPlayingPlayers.contains(p)){
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
                            AchievementAPI.grantAchievement(p, "GettingUpgrade");
                        }
                    }
                }
                e.setCancelled(true);
            }

            if(e.getClickedInventory().equals(PerkMenu.inventory)){
                e.setCancelled(true);
                if(e.getClickedInventory().getItem(e.getSlot()) != null){
                    ItemMeta meta = e.getClickedInventory().getItem(e.getSlot()).getItemMeta();
                    ArrayList<String> list = Main.ownedPerks.get(p.getUniqueId().toString());
                    int level = PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level");
                    for(String s : Main.perks){
                        if(GunsFile.getData().getString("Perks." + s + ".Name").equals(meta.getDisplayName())){

                            if(level < GunsFile.getData().getInt("Perks." + s + ".Level")){
                                p.sendMessage(Main.prefix + "§4Purchase failed: §cYou need to be Level: §a" + GunsFile.getData().getInt("Perks." + s + ".Level"));
                                return;
                            }

                            if(list.contains(meta.getDisplayName())){
                                p.sendMessage(Main.prefix + "§cYou already own §4" + meta.getDisplayName());
                                p.closeInventory();
                                return;
                            }else{
                                list.add(meta.getDisplayName());
                                PlayerData.getData().set("Players." + p.getUniqueId().toString() + ".Perks", list);
                                PlayerData.saveData();
                                p.sendMessage(Main.prefix + "§aPurchase of §b" + meta.getDisplayName() + " §asuccessful");
                                p.closeInventory();

                                AchievementAPI.grantAchievement(p, "PerkUp");
                            }
                        }
                    }

                }


            }
           /* if(e.getClickedInventory().equals(SelectKit.mainKit)){
                if(e.getSlot() == 1){
                    p.closeInventory();
                 //   new SelectKit().createPrimary(p);
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
            }*/
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if(Main.WaitingPlayers.contains(e.getPlayer())) {
                if (e.getItem() != null) {
                    if (e.getItem().getType() == Material.COMPASS) {
                        new GameInventory().createMenu(e.getPlayer());
                    } else if (e.getItem().getType() == Material.BARRIER) {
                        Main.WaitingPlayers.remove(e.getPlayer());
                        e.getPlayer().sendMessage(Main.prefix + "§aYou have left the lobby");
                        Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(e.getPlayer()));
                    }
                }
            }
        }
    }

    private boolean isEnabled(Player p){
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes." ).getKeys(false)){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return true;
            }
        }
        return false;
    }

    private String getEnabled(Player p){
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes.").getKeys(false)){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return s;
            }
        }
        return null;
    }
}

