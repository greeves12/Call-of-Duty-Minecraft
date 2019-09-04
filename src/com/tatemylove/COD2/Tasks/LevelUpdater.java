package com.tatemylove.COD2.Tasks;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelUpdater extends BukkitRunnable {
    @Override
    public void run() {

            for (Player p : Bukkit.getOnlinePlayers()) {

                if(PlayerData.getData().contains("Players." + p.getUniqueId().toString())) {

                    int level = LevelRegistryAPI.getLevel(p);
                    int exp = LevelRegistryAPI.getEXP(p);
                    int newXP = level * 500;

                    if (exp >= newXP) {
                        if(LevelRegistryAPI.getLevel(p) < ThisPlugin.getPlugin().getConfig().getInt("prestige-level")) {
                            LevelRegistryAPI.resetExp(p, exp - newXP);
                            LevelRegistryAPI.addLevel(p, 1);
                        }
                    }
                }
                if(Main.WaitingPlayers.contains(p) || Main.AllPlayingPlayers.contains(p)){
                    int exp =  LevelRegistryAPI.getEXP(p);

                    p.setLevel(exp);
                }
        }
    }
}
