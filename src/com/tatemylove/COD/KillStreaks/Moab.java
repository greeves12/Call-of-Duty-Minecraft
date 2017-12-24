package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Moab {
    Main main;
    private static Moab moab = null;

    public Moab(Main m){
        main = m;
        moab = Moab.this;
    }
    private static ItemStack Moab = new ItemStack(Material.TNT);

    public static void settUp(){
        ItemMeta meta = Moab.getItemMeta();
        meta.setDisplayName("§3§lMOAB");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§b§lYou have a NUKE! This will end the game thus killing all players and will make your team win.");
        meta.setLore(lore);
        Moab.setItemMeta(meta);
    }

    public void onEntityKill(EntityDeathEvent e){
        Entity entity = e.getEntity();
        Entity entity1 = e.getEntity().getKiller();

        if(entity instanceof Player) {
            if(entity1 instanceof Player) {
                Player p = (Player) e.getEntity();
                Player pp = e.getEntity().getKiller();

                if ((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
                    if (Main.killStreak.get(p.getName()) == 30) {
                        pp.getInventory().addItem(Moab);
                        pp.sendMessage(main.prefix + "§c§lYou got a Nuke. Right click to launch!");
                    }
                }
            }
        }
    }

    public void onPlayerIneteract(PlayerInteractEvent e) {


        for (Player p : main.PlayingPlayers) {
            p.sendMessage(main.prefix + e.getPlayer().getName() + " §c§llaunched a NUKE!");
        }

        final Player p = e.getPlayer();


        if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(Moab)) {
            e.getPlayer().getInventory().setItemInMainHand(null);
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!(main.PlayingPlayers.isEmpty())) {
                        GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam(main);
                        if (!(getPlayersOnOtherTeam.get(p).isEmpty())) {
                            for (Player pp : getPlayersOnOtherTeam.get(p)) {
                                pp.setHealth(0);
                                pp.playSound(pp.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                            }
                        }
                    }
                }
            };
            if (BaseArena.type == BaseArena.ArenaType.TDM) {
                TDM tdm = new TDM(main);
                tdm.endTDM();
            }
            br.runTaskLater(ThisPlugin.getPlugin(), 60L);
        }
    }
}
