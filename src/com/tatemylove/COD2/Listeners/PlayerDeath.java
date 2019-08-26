package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Events.CODKillEvent;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDEath(PlayerDeathEvent e){
        if(Main.PlayingPlayers.contains(e.getEntity())){
            e.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getDamager() instanceof Player){
                Player p = (Player) e.getEntity();
                Player pp = (Player) e.getDamager();

                if(Main.PlayingPlayers.contains(p) && Main.PlayingPlayers.contains(pp)){
                    if((Main.RedTeam.contains(p) && Main.RedTeam.contains(pp)) || (Main.BlueTeam.contains(p) && Main.BlueTeam.contains(pp))){
                        e.setCancelled(true);
                    }
                    if(pp.getInventory().getItemInMainHand().equals(GameInventory.knife)){
                        p.setHealth(0);
                        for(Player player : Main.PlayingPlayers){
                            player.sendMessage(Main.prefix + "§e" + p.getName() + " §dgot dookied on");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Player ) {
            Player p = (Player) e.getEntity();
            Player pp = p.getKiller();
            if (BaseArena.type == BaseArena.ArenaType.TDM) {
                if (pp != null) {
                    if (Main.PlayingPlayers.contains(p) && Main.PlayingPlayers.contains(pp)) {
                        if (Main.RedTeam.contains(p)) {
                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§b" + p.getName() + "§e killed §c" + pp.getName());
                            }
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, pp, "red", "blue"));
                            Main.bluescore += 1;
                        } else if (Main.BlueTeam.contains(p)) {
                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§c" + p.getName() + "§e killed §b" + pp.getName());
                            }
                            Main.redscore += 1;
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, pp, "blue", "red"));
                        }
                    }
                } else {
                    if (Main.PlayingPlayers.contains(p)) {
                        if (Main.RedTeam.contains(p)) {
                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§c" + p.getName() + "§e played themselves");
                            }
                            Main.bluescore += 1;
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "red", null));
                        } else if (Main.BlueTeam.contains(p)) {

                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§b" + p.getName() + "§e played themselves");
                            }
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "blue", null));
                            Main.redscore += 1;
                        }
                    }
                }

            }
        }
    }
}
