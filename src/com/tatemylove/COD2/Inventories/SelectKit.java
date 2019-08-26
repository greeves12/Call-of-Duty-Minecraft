package com.tatemylove.COD2.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SelectKit {
    public static Inventory main = Bukkit.createInventory(null, 54, "§b§lSelect Loadout");
    public static Inventory primary = Bukkit.createInventory(null, 54, "§a§lPrimary");
    public static Inventory secondary = Bukkit.createInventory(null, 54, "§6§lSecondary");

}
