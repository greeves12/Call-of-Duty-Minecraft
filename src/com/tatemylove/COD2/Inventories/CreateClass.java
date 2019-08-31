package com.tatemylove.COD2.Inventories;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Main;
import me.zombie_striker.qg.api.QualityArmory;
import me.zombie_striker.qg.guns.utils.WeaponSounds;
import me.zombie_striker.qg.guns.utils.WeaponType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CreateClass {
    public static Inventory mainArea = Bukkit.createInventory(null, 45, "§b§lCreate A Class");
    public static Inventory primary = Bukkit.createInventory(null, 54, "§a§lPrimary");
    public static Inventory secondary = Bukkit.createInventory(null, 54, "§6§lSecondary");
    public static Inventory perks = Bukkit.createInventory(null, 54, "§6§lPerks");
    public static Inventory perks2 = Bukkit.createInventory(null, 54, "§6§lPerks");
    public static Inventory perks3 = Bukkit.createInventory(null, 54, "§6§lPerks");
    public static Inventory splode = Bukkit.createInventory(null, 54, "§5§lSplode's");
    public static Inventory splode2 = Bukkit.createInventory(null, 54, "§5§lSplode's");

    public static HashMap<UUID, Integer> classnumber = new HashMap<>();

    private boolean isEnabled(Player p){
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes." ).getKeys(false)){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return true;
            }
        }
        return false;
    }


    public void createKit(Player p){
        mainArea = Bukkit.createInventory(p, 45, "§b§lSelect Loadout");

        ArrayList<String> craft = new ArrayList<>();
        craft.add("§bClick slots to the left to edit them.");


        mainArea.setItem(0, getMaterial(Material.CRAFTING_TABLE, "§6§lClass 1", null));
        mainArea.setItem(9, getMaterial(Material.CRAFTING_TABLE, "§6§lClass 2", null));
        mainArea.setItem(18, getMaterial(Material.CRAFTING_TABLE, "§6§lClass 3", null));
        mainArea.setItem(27, getMaterial(Material.CRAFTING_TABLE, "§6§lClass 4", null));
        mainArea.setItem(36, getMaterial(Material.CRAFTING_TABLE, "§6§lClass 5", null));



        int classnum = 1;
        int k = classnum *1;
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes.").getKeys(false)){

            if (PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString()+".Classes." + s + ".Enabled")) {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis class is §aEnabled");


                mainArea.setItem(k + 7, getMaterial(Material.LIME_DYE,"§6" + s , lore));

            } else {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4This class is §cDisabled");

                mainArea.setItem(k + 7, getMaterial(Material.REDSTONE, "§6"+s, lore));

            }


            if (Main.perks.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk3"))) {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your third Perk");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Perks."+PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk3") + ".Material");
                mainArea.setItem(k + 6, getMaterial(Material.getMaterial(mat.toUpperCase()), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Perk3"), lore));

            } else {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k + 6, getMaterial(Material.BARRIER, "§6§lPerk 3", lore));

            }


            if (Main.perks.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk2"))) {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your second Perk");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Perks."+PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk2") + ".Material");
                mainArea.setItem(k + 5, getMaterial(Material.getMaterial(mat.toUpperCase()), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Perk2"), lore));

            } else {
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k + 5, getMaterial(Material.BARRIER, "§6§lPerk 2", lore));

            }


                if (Main.perks.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk1"))) {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("§eThis is your first Perk");
                    lore.add("§bClick to change");

                    String mat = GunsFile.getData().getString("Perks."+PlayerData.getData().getString("Players." + p.getUniqueId().toString()+".Classes." + s + ".Perk1") + ".Material");
                    mainArea.setItem(k + 4, getMaterial(Material.getMaterial(mat.toUpperCase()), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Perk1"), lore));

                } else {
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("§4None selected");
                    lore.add("§bClick to change");
                    mainArea.setItem(k + 4, getMaterial(Material.BARRIER, "§6§lPerk 1", lore));

            }


            if(Main.guns.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Secondary"))){
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your secondary weapon");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Guns." + PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Secondary") + ".GunMaterial");
                mainArea.setItem(k+1, getMaterial(Material.getMaterial(mat), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Secondary"), lore));
            }else{
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k+1, getMaterial(Material.BARRIER, "§6§lSecondary",lore ));
            }

            if(Main.guns.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode1"))){
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your primary explosive");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Guns." + PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode1") + ".GunMaterial");
                mainArea.setItem(k+2, getMaterial(Material.getMaterial(mat), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode1"), lore));
            }else{
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k+2, getMaterial(Material.BARRIER, "§2§lPrimary Explosive",lore ));
            }

            if(Main.guns.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode2"))){
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your secondary explosive");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Guns." + PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode2") + ".GunMaterial");
                mainArea.setItem(k+3, getMaterial(Material.getMaterial(mat), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Splode2"), lore));
            }else{
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k+3, getMaterial(Material.BARRIER, "§2§lSecondary Explosive",lore ));
            }

            if(Main.guns.contains(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Primary"))){
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§eThis is your primary weapon");
                lore.add("§bClick to change");

                String mat = GunsFile.getData().getString("Guns." + PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Primary") + ".GunMaterial");
                mainArea.setItem(k, getMaterial(Material.getMaterial(mat), PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Primary"), lore));
            }else{
                ArrayList<String> lore = new ArrayList<>();
                lore.add("§4None selected");
                lore.add("§bClick to change");
                mainArea.setItem(k, getMaterial(Material.BARRIER, "§b§lPrimary",lore ));
            }
            k+=9;
            classnum++;

        }


        p.openInventory(mainArea);

    }

    public void createPrimary(Player p, int k){
        primary = Bukkit.createInventory(p, 54, "§a§lPrimary");

        ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

        ArrayList<String> strings = new ArrayList<>();
        strings.add("Class1");
        strings.add("Class2");
        strings.add("Class3");
        strings.add("Class4");
        strings.add("Class5");


        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
           // WeaponSounds.
            ArrayList<String> a = new ArrayList<>();

                if (PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + "Class"+k  + ".Primary").equals(s)) {
                    a.add("§a§lCurrently Selected");

                } else {
                    a.add("§bClick to select");


            }
            if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("PRIMARY")) {
                primary.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
            }

        }
        p.openInventory(primary);

    }

    public void createSecondary(Player p, int k){
        secondary = Bukkit.createInventory(null, 54, "§6§lSecondary");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Class1");
        strings.add("Class2");
        strings.add("Class3");
        strings.add("Class4");
        strings.add("Class5");

        ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
            ArrayList<String> a = new ArrayList<>();

                if (PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k  + ".Secondary").equals(s)) {
                    a.add("§a§lCurrently Selected");

                } else {
                    a.add("§bClick to select");


            }
            if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("SECONDARY")) {
                secondary.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
            }
        }
        p.openInventory(secondary);

    }

    public void createSplode(Player p, int k){
        splode = Bukkit.createInventory(null, 54, "§5§lSplode's");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Class1");
        strings.add("Class2");
        strings.add("Class3");
        strings.add("Class4");
        strings.add("Class5");

        ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
            ArrayList<String> a = new ArrayList<>();

                if (PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k  + ".Splode1").equals(s) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k  + ".Splode2").equals(s) ){
                    a.add("§a§lCurrently Selected");

                } else {
                    a.add("§bClick to select");
                    if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("SPLODE")) {
                        splode.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
                    }
                }


        }
        p.openInventory(splode);
    }

    public void createSplode2(Player p, int k){
        splode2 = Bukkit.createInventory(null, 54, "§5§lSplode's");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Class1");
        strings.add("Class2");
        strings.add("Class3");
        strings.add("Class4");
        strings.add("Class5");

        ArrayList<String> guns = Main.ownedGuns.get(p.getUniqueId().toString());

        for(String s : guns){
            String material = GunsFile.getData().getString("Guns." + s + ".GunMaterial");
            ArrayList<String> a = new ArrayList<>();

                if (PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k  + ".Splode2").equals(s) ||PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k  + ".Splode1").equals(s)  ) {
                    a.add("§a§lCurrently Selected");

                } else {
                    a.add("§bClick to select");
                    if(GunsFile.getData().getString("Guns." + s + ".Type").equalsIgnoreCase("SPLODE")) {
                        splode2.addItem(getMaterial(Material.getMaterial(material.toUpperCase()), s, a));
                    }

                }


        }
        p.openInventory(splode2);
    }

    public void createPerks(Player p, int k){
        perks = Bukkit.createInventory(null, 54, "§6§lPerks");



        for(String s : Main.perks){
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk1").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk2").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk3").equals(GunsFile.getData().getString("Perks." +s+".Name"))){
                a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
                perks.addItem(getMaterial(Material.getMaterial(GunsFile.getData().getString("Perks." + s + ".Material").toUpperCase()), GunsFile.getData().getString("Perks." +s + ".Name"), a));
            }

        }
        p.openInventory(perks);

    }

    public void createPerks2(Player p, int k){
        perks2 = Bukkit.createInventory(null, 54, "§6§lPerks");


        for(String s : Main.perks){
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk1").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk2").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk3").equals(GunsFile.getData().getString("Perks." +s+".Name"))){
                a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
                perks2.addItem(getMaterial(Material.getMaterial(GunsFile.getData().getString("Perks." + s + ".Material").toUpperCase()), GunsFile.getData().getString("Perks." +s + ".Name"), a));
            }

        }
        p.openInventory(perks2);

    }

    public void createPerks3(Player p, int k){
        perks3 = Bukkit.createInventory(null, 54, "§6§lPerks");


        for(String s : Main.perks){
            ArrayList<String> a = new ArrayList<>();
            if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk1").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk2").equals(GunsFile.getData().getString("Perks." +s+".Name")) || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes.Class" + k + ".Perk3").equals(GunsFile.getData().getString("Perks." +s+".Name"))){
                a.add("§a§lCurrently Selected");
            }else {
                a.add("§bClick to select");
                perks3.addItem(getMaterial(Material.getMaterial(GunsFile.getData().getString("Perks." + s + ".Material").toUpperCase()), GunsFile.getData().getString("Perks." +s + ".Name"), a));
            }

        }
        p.openInventory(perks3);

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
