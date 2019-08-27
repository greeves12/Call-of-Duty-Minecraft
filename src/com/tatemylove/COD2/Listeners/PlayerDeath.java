package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Arenas.GetArena;
import com.tatemylove.COD2.Arenas.KillConfirmed;
import com.tatemylove.COD2.Events.CODKillEvent;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
    public void onRespawn(PlayerRespawnEvent e){
        if(Main.PlayingPlayers.contains(e.getPlayer())){
            if(Main.RedTeam.contains(e.getPlayer())){
                e.getPlayer().teleport(GetArena.getRedSpawn(e.getPlayer()));
            }else if(Main.BlueTeam.contains(e.getPlayer())){
                e.getPlayer().teleport(GetArena.getBlueSpawn(e.getPlayer()));
            }
        }
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e){
        new KillConfirmed().onPickUp(e);
    }


    @EventHandler
    public void onDeath(EntityDeathEvent e){

        if(e.getEntity() instanceof Player ) {
            Player p = (Player) e.getEntity();
            Player pp = p.getKiller();
            if(BaseArena.type == BaseArena.ArenaType.KC){
                new KillConfirmed().onDeath(e);
            }
            if (BaseArena.type == BaseArena.ArenaType.TDM) {

                if (pp != null) {
                    if (Main.PlayingPlayers.contains(p) && Main.PlayingPlayers.contains(pp)) {
                        if (Main.RedTeam.contains(p)) {
                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§b" + p.getName() + "§e killed §c" + pp.getName());
                            }
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, pp, "red", "blue"));
                            RegistryAPI.registerDeath(p);
                            RegistryAPI.registerKill(pp);
                            LevelRegistryAPI.addExp(pp, ThisPlugin.getPlugin().getConfig().getInt("exp-kill"));

                            Main.bluescore += 1;
                        } else if (Main.BlueTeam.contains(p)) {
                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§c" + p.getName() + "§e killed §b" + pp.getName());
                            }
                            Main.redscore += 1;
                            RegistryAPI.registerDeath(p);
                            RegistryAPI.registerKill(pp);
                            LevelRegistryAPI.addExp(pp, ThisPlugin.getPlugin().getConfig().getInt("exp-kill"));

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
                            RegistryAPI.registerDeath(p);
                            RegistryAPI.deaths.put(p.getUniqueId(), RegistryAPI.deaths.get(p.getUniqueId()));
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "red", null));
                        } else if (Main.BlueTeam.contains(p)) {

                            for (Player players : Main.PlayingPlayers) {
                                players.sendMessage(Main.prefix + "§b" + p.getName() + "§e played themselves");
                            }
                            Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "blue", null));
                            RegistryAPI.registerDeath(p);

                            Main.redscore += 1;
                        }
                    }
                }

            }
        }
    }
}
