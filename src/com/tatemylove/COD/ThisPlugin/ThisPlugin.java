package com.tatemylove.COD.ThisPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ThisPlugin {
    public static Plugin getPlugin(){
        return Bukkit.getPluginManager().getPlugin("COD");
    }
}
