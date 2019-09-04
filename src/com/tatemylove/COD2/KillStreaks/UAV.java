package com.tatemylove.COD2.KillStreaks;

import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class UAV {
    public static final ItemStack uav = new ItemStack(Material.FIREWORK_ROCKET);

    public static void settUp(){
        ItemMeta meta = uav.getItemMeta();
        meta.setDisplayName("§e§lUAV");
        meta.setLore(Arrays.asList("§bUAV ready on your go!"));
        uav.setItemMeta(meta);
    }

    public void onKill(EntityDeathEvent e, HashMap<UUID, Integer> killstreak, ArrayList<Player> PlayingPlayers){
        Entity player = e.getEntity();
        Entity killer = e.getEntity().getKiller();

        if(player instanceof Player && killer instanceof Player){
            Player p = (Player) player;
            Player pp = (Player) killer;

            if((PlayingPlayers.contains(p)) && (PlayingPlayers.contains(pp))){
                if(killstreak.get(pp.getUniqueId()) == 3){
                    pp.getInventory().addItem(uav);
                    pp.sendMessage(Main.prefix + "§aUAV waiting on your go!");
                }
            }
        }
    }

    public void onUse(PlayerInteractEvent e, ArrayList<Player> RedTeam, ArrayList<Player> BlueTeam, ArrayList<Player> PlayingPlayers){
        if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(uav) || e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getInventory().getItemInMainHand().equals(uav)){
            GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam();

            e.getPlayer().getInventory().setItemInMainHand(null);

            for(Player all : PlayingPlayers){
                all.sendMessage(Main.prefix + "§a" + e.getPlayer().getName() + " §ehas deployed a UAV!");
            }
            new BukkitRunnable(){
                int time = 10;
                @Override
                public void run() {
                    if(time >= 0) {
                        for (Player pp : getPlayersOnOtherTeam.get(e.getPlayer(), RedTeam, BlueTeam)) {
                            if(RedTeam.contains(pp)) {
                                Firework f = pp.getWorld().spawn(pp.getLocation(), Firework.class);
                                FireworkMeta meta = f.getFireworkMeta();
                                FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                                meta.addEffect(effect);
                                meta.setPower(2);
                                f.setFireworkMeta(meta);
                            }else if(BlueTeam.contains(pp)){
                                Firework f = pp.getWorld().spawn(pp.getLocation(), Firework.class);
                                FireworkMeta meta = f.getFireworkMeta();
                                FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.BLUE).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                                meta.addEffect(effect);
                                meta.setPower(2);
                                f.setFireworkMeta(meta);
                            }
                            if (time <= 0) {
                                break;
                            }
                        }
                    }else{
                        cancel();
                    }

                    time--;
                }
            }.runTaskTimer(ThisPlugin.getPlugin(), 0, 40);
e.setCancelled(true);
        }
    }
}
