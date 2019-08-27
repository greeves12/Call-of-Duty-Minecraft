package com.tatemylove.COD2.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class GameInventory {
    public static final ItemStack knife = new ItemStack(Material.IRON_SWORD);
    public static ItemStack achievements;
    public static final ItemStack kits = new ItemStack(Material.BOOK);
    public static final ItemStack buygun = new ItemStack(Material.EMERALD);

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


    public static void lobbyInv(Player p){
        achievements = SkullCreator.itemFromUuid(p.getUniqueId());
        SkullMeta meta = (SkullMeta) achievements.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        meta.setDisplayName("§3§nAchievements");
        achievements.setItemMeta(meta);

        p.getInventory().setItem(2, achievements);

        ItemMeta me = kits.getItemMeta();
        me.setDisplayName("§b§nLoadout Selector");
        kits.setItemMeta(me);

        p.getInventory().setItem(4, kits);

        ItemMeta mee = buygun.getItemMeta();
        mee.setDisplayName("§6§nBuy Weapons/Perks");
        buygun.setItemMeta(mee);
        p.getInventory().setItem(6, buygun);

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
