package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Main;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class UAV {

    Main main;

    public UAV(Main main){
        this.main = main;
    }
    public static final ItemStack uav = new ItemStack(Material.FIREWORK_ROCKET);

    public static void settUp(){
        ItemMeta meta = uav.getItemMeta();
        meta.setDisplayName("§e§lUAV");
        meta.setLore(Arrays.asList("§bUAV ready on your go!"));
        uav.setItemMeta(meta);
    }

    public void onKill(EntityDeathEvent e){
        Entity player = e.getEntity();
        Entity killer = e.getEntity().getKiller();

        if(player instanceof Player && killer instanceof Player){
            Player p = (Player) player;
            Player pp = (Player) killer;

            if((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))){
                if(Main.killStreak.get(pp.getName()) == 3){
                    pp.getInventory().addItem(uav);
                    pp.sendMessage(main.prefix + "§aUAV waiting on your go!");
                }
            }
        }
    }

    public void onUse(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(uav)){
            GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam(main);
            e.getPlayer().getInventory().setItemInMainHand(null);

            for(Player all : main.PlayingPlayers){
                all.sendMessage(main.prefix + "§a" + e.getPlayer().getName() + " §ehas deployed a UAV!");
            }
            new BukkitRunnable(){
                int time = 10;
                @Override
                public void run() {
                    if(time >= 0) {
                        for (Player pp : getPlayersOnOtherTeam.get(e.getPlayer())) {
                            pp.getWorld().spawnEntity(pp.getLocation(), EntityType.FIREWORK);
                            if (time <= 0) {
                                break;
                            }
                        }
                    }else{
                        cancel();
                    }

                    time--;
                }
            }.runTaskTimer(main, 0, 20);

        }
    }
}
