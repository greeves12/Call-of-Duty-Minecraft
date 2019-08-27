package com.tatemylove.COD2.Tasks;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelUpdater extends BukkitRunnable {
    @Override
    public void run() {

            for (Player p : Bukkit.getOnlinePlayers()) {

                int level = LevelRegistryAPI.getLevel(p);
                int exp = LevelRegistryAPI.getEXP(p);
                int newXP = level * 500;

                if(exp >= newXP){
                    LevelRegistryAPI.resetExp(p, exp - newXP);
                    LevelRegistryAPI.addLevel(p, 1);
                }
        }
    }
}
