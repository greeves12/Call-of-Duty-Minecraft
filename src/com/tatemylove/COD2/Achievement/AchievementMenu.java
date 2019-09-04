package com.tatemylove.COD2.Achievement;

import com.tatemylove.COD2.Files.AchievementFile;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AchievementMenu {

    public static Inventory inv = Bukkit.createInventory(null, 27, "§3§lAchievements");


    public void createInventory(Player p){
        inv= Bukkit.createInventory(null, 27, "§3§lAchievements");


        for(String s : Main.achievements){
            if(AchievementFile.getData().contains("Achievements." + s )){
                String mat = AchievementFile.getData().getString("Achievements." + s + ".Material");
                String displayname = AchievementFile.getData().getString("Achievements." + s + ".Name");
                String description = AchievementFile.getData().getString("Achievements." + s + ".Desc");

                ArrayList<String> lore = new ArrayList<>();

                if(AchievementFile.getData().contains("Players." + p.getUniqueId().toString() + "." + s + ".Date")) {
                    String date = AchievementFile.getData().getString("Players." + p.getUniqueId().toString() + "." + s + ".Date");
                    lore.add("");
                    lore.add("§bDescription: §a" + ChatColor.translateAlternateColorCodes('&', description));
                    lore.add("");
                    lore.add("§bUnlocked: §aYes");
                    lore.add("");
                    lore.add("§bDate Unlocked: §e" + date);

                    inv.addItem(getMaterial(Material.getMaterial(mat.toUpperCase()), ChatColor.translateAlternateColorCodes('&', displayname), lore));
                }else{
                    lore.add("");
                    lore.add("§bDescription: §a" + ChatColor.translateAlternateColorCodes('&', description));
                    lore.add("");
                    lore.add("§bUnlocked: §cNo");
                    inv.addItem(getMaterial(Material.getMaterial(mat.toUpperCase()), ChatColor.translateAlternateColorCodes('&', displayname), lore));
                }
            }
        }
        p.openInventory(inv);
    }

    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta meta = s.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        s.setItemMeta(meta);

        return s;
    }
}
