package com.tatemylove.COD.Tasks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.CountDown;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestFinder extends BukkitRunnable {
    Main main;

    public ChestFinder (Main m){
        main = m;
    }
    @Override
    public void run() {
        if(BaseArena.states == BaseArena.ArenaStates.Started){
        String world = ArenaFile.getData().getString("Arenas." + CountDown.timeuntilstart + ".Spawns.Red.World");

       for(Player p : Bukkit.getServer().getOnlinePlayers()){
           if(main.WaitingPlayers.contains(p)){
               main.nonPlayers.remove(p);
           }else{
               main.WaitingPlayers.add(p);
           }
       }

        for(Chunk c : Bukkit.getServer().getWorld(world).getLoadedChunks()) {
            for (BlockState b : c.getTileEntities()) {
                if (b instanceof Chest) {
                    Chest chest = (Chest) b;


                    if (chest.hasMetadata("codChest")) {

                            if (chest.getInventory().getItem(0) == null) {

                                chest.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
        }
    }
}
