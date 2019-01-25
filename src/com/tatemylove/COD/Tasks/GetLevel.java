package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GetLevel extends BukkitRunnable {
    Main main;

    public GetLevel(Main m) {
        main = m;
    }

    @Override
    public void run() {
        if (BaseArena.states == BaseArena.ArenaStates.Started) {
            for (Player p : main.PlayingPlayers) {
                PlayerLevels playerLevels = new PlayerLevels(main);
                int level = playerLevels.getLevel(p);
                int exp = playerLevels.getEXP(p);
                int newXP = level * 500;

                if(exp >= newXP){
                    playerLevels.resetExp(p, exp - newXP);
                    playerLevels.addLevel(p, 1);
                }
            }
        }
    }
}
