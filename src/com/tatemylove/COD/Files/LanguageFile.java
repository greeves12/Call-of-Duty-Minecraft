package com.tatemylove.COD.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class LanguageFile {
    private LanguageFile() { }

    static LanguageFile instance = new LanguageFile();

    public static LanguageFile getInstance() {
        return instance;
    }

    static Plugin p;

    static FileConfiguration language;
    static File lfile;

    public static void setup(Plugin p) {

        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        lfile = new File(p.getDataFolder(), "language.yml");

        if (!lfile.exists()) {
            try {
                lfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create language.yml!");
            }
        }

        language = YamlConfiguration.loadConfiguration(lfile);
        language.addDefault("join-message", "&e&lYou joined the Queue");
        language.addDefault("win-message", "&7&lYour team won");
        language.addDefault("lose-message", "&8&lYour team lost");

        language.options().copyDefaults(true);
        saveData();
    }

    public static FileConfiguration getData() {
        return language;
    }

    public static void saveData() {
        try {
            language.save(lfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save language.yml!");
        }
    }

    public static void reloadData() {
        language = YamlConfiguration.loadConfiguration(lfile);
    }

    public static PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}
