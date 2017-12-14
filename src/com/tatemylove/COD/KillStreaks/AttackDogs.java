package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AttackDogs {
    Main main;
    private static AttackDogs attackDogs = null;
    public ItemStack dogs = new ItemStack(Material.BONE);

    public AttackDogs(Main m){
        main = m;
        attackDogs = AttackDogs.this;
    }

    public void settUp(){
        ItemMeta meta = dogs.getItemMeta();
        meta.setDisplayName("§c§lDogs");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6RELEASE THE HOUNDS!");
        meta.setLore(lore);
        dogs.setItemMeta(meta);
    }
}
