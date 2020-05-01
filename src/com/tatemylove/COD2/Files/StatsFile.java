package com.tatemylove.COD2.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class StatsFile {
    static PlayerData instance = new PlayerData();
    static Plugin p;
    static FileConfiguration bug;
    static File afile;

    public static void setup(Plugin p)
    {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        afile = new File(p.getDataFolder(), "stats.yml");
        if (!afile.exists()) {
            try
            {
                afile.createNewFile();
            }
            catch (IOException e)
            {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create player.yml!");
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
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save gun.yml!");
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
