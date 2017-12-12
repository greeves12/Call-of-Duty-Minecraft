package com.tatemylove.COD;

import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.ArenaFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public String prefix = "§7§l[COD§f§l-§8§lWarfare] ";

    public void onEnable(){
        MainCommand cmd = new MainCommand(this);
        getCommand("cod").setExecutor(cmd);

        ArenaFile.setup(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
    }
}
