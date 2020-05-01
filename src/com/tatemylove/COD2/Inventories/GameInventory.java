package com.tatemylove.COD2.Inventories;

import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GameInventory {
    public static final ItemStack knife = new ItemStack(Material.IRON_SWORD);


    public static Inventory mainInv = Bukkit.createInventory(null, 27, "§3§lMain Menu");

    public static void settUp(){
        ItemMeta meta = knife.getItemMeta();
        meta.setDisplayName("§c§lKnife");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6The 'Get Rekt Scrub'");
        meta.setLore(lore);
        knife.setItemMeta(meta);
    }

    public static void leaderBoard(Player p){

    }

    public void createMenu(Player p){
        mainInv = Bukkit.createInventory(p, 27, "§3§lMain Menu");

        ItemStack achievements = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) achievements.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        meta.setDisplayName("§2§lYour Achievements");
        achievements.setItemMeta(meta);

        mainInv.setItem(18, getMaterial(Material.ANVIL, "§6§nPrestige", new ArrayList<>(Arrays.asList("§bClick to Prestige","" ,"§7Level Required: §a" + ThisPlugin.getPlugin().getConfig().getInt("prestige-level")))));
        mainInv.setItem(2, getMaterial(Material.ENDER_CHEST, "§e§lCreate-A-Class", null));
        mainInv.setItem(12, achievements);
        mainInv.setItem(4, getMaterial(Material.WRITTEN_BOOK, "§3§lYour Stats", null));

        mainInv.setItem(14, getMaterial(Material.WITHER_SKELETON_SKULL, "§7§lGlobal Stats", null));
        mainInv.setItem(6, getMaterial(Material.EMERALD, "§4§lArmory", new ArrayList<>(Arrays.asList("§dGet your weapons here"))));
        mainInv.setItem(26, getMaterial(Material.BARRIER, "§c§lClose Menu", null));

        p.openInventory(mainInv);

    }

    public static void lobbyInv(Player p){

        p.getInventory().setItem(0, getMaterial(Material.COMPASS, "§a§lMain Menu", null));

        p.getInventory().setItem(8, getMaterial(Material.BARRIER, "§c§lLeave Game", null));
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
