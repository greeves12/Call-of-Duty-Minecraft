package com.tatemylove.COD.Utilities;

import org.bukkit.ChatColor;

public class NewChat {

    public static final String getNewColor(String message){
        ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }
}
