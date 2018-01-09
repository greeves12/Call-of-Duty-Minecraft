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
            for (Player pp : main.PlayingPlayers) {
                PlayerLevels playerLevels = new PlayerLevels(main);
                int exp = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".EXP");
                int level = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Level");
                if (level == 1) {
                    if (exp >= playerLevels.levelTwo) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 2) {
                    if (exp >= playerLevels.levelThree) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 3) {
                    if (exp >= playerLevels.levelFour) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 4) {
                    if (exp >= playerLevels.levelFive) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 5) {
                    if (exp >= playerLevels.levelSix) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 6) {
                    if (exp >= playerLevels.levelSeven) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 7) {
                    if (exp >= playerLevels.levelEight) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 8) {
                    if (exp >= playerLevels.levelNine) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 9) {
                    if (exp >= playerLevels.levelTen) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                }
            }
        }
    }
}
