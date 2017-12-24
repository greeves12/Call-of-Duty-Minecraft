package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerInteractListener implements Listener {
    Main main;

    public PlayerInteractListener(Main m) {
        main = m;
    }

    @EventHandler
    public void onTouch(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity entity1 = e.getDamager();
        if ((entity instanceof Player) && (entity1 instanceof Player)) {
            Player p = (Player) e.getEntity();
            Player pp = (Player) e.getDamager();

            if (!ThisPlugin.getPlugin().getConfig().getBoolean("friendly-fire")) {
                if ((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
                    if (TDM.blueTeam.contains(p) && (TDM.blueTeam.contains(pp))) {
                        e.setCancelled(true);
                        return;
                    } else if (TDM.redTeam.contains(p) && (TDM.redTeam.contains(pp))) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            if(main.PlayingPlayers.contains(p)) {
                if (pp.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
                    e.setDamage(2000.0D);
                }
            }
            if(PlayerDeathListener.invincible.contains(p)){
                e.setCancelled(true);
            }
        }else if(entity1 instanceof Wolf){
            if(entity instanceof Player) {
                Player p = (Player) entity;
                if (main.PlayingPlayers.contains(p)) {
                    e.setDamage(8.0D);
                }
            }
        }
    }

    @EventHandler
    public void noInvMove(InventoryClickEvent e) {
        Entity entity = e.getWhoClicked();
        if (entity instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (main.PlayingPlayers.contains(p)) {
                if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void noSwitch(PlayerSwapHandItemsEvent e){
        if(main.getConfig().getBoolean("swap-hands")) {
            if (main.PlayingPlayers.contains(e.getPlayer()) || (main.WaitingPlayers.contains(e.getPlayer()))) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void noDrop(PlayerDropItemEvent e){
        if(main.PlayingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }else if(main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
}

