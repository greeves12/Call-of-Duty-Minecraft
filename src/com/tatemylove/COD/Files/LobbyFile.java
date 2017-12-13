package com.tatemylove.COD.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class LobbyFile {
    static ArenaFile instance = new ArenaFile();
    static Plugin p;
    static FileConfiguration bug;
    static File afile;

    public static void setup(Plugin p)
    {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        afile = new File(p.getDataFolder(), "lobby.yml");
        if (!afile.exists()) {
            try
            {
                afile.createNewFile();
            }
            catch (IOException e)
            {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create lobby.yml!");
            }
        }
        bug = YamlConfiguration.loadConfiguration(afile);
    }

    public static FileConfiguration getData()
    {
        return bug;
    }

    public static void saveData()
    {
        try
        {
            bug.save(afile);
        }
        catch (IOException e)
        {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save lobby.yml!");
        }
    }

    public static void reloadData()
    {
        bug = YamlConfiguration.loadConfiguration(afile);
    }

    public static PluginDescriptionFile getDesc()
    {
        return p.getDescription();
    }
}
