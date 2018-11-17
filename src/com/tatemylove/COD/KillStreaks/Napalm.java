package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Listeners.PlayerInteractItem;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
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

public class Napalm {
    public final static ItemStack napalm = new ItemStack(Material.SUGAR);

    Main main;
    private static Napalm napalms = null;
    public Napalm(Main m){
        main = m;
        napalms = Napalm.this;
    }

    public static void settUp(){
        ItemMeta meta = napalm.getItemMeta();
        meta.setDisplayName("§bAirStrike");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§dAirStrike waiting on your go");
        meta.setLore(lore);
        napalm.setItemMeta(meta);
    }

    public void onEntityKill(EntityDeathEvent e){
        Entity entity = e.getEntity();
        Entity entity1 = e.getEntity().getKiller();

        if(entity instanceof Player) {
            if(entity1 instanceof Player) {
                Player p = (Player) e.getEntity();
                Player pp = e.getEntity().getKiller();

                if ((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))) {
                    if (Main.killStreak.get(pp.getName()) == 4) {
                        pp.getInventory().addItem(napalm);
                        pp.sendMessage(main.prefix + "§6AirStrike waiting on your go!");
                    }
                }
            }
        }
    }

    public void onInteract(PlayerInteractEvent e){
        GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam(main);


        if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(napalm)){
            e.getPlayer().getInventory().setItemInMainHand(null);
            e.getPlayer().sendMessage(main.prefix + "§e§lFIRE IN THE HOLE!!");


            for(Player p : main.PlayingPlayers){
                p.sendMessage(main.prefix + "§a§l" + e.getPlayer().getName() + " §b§llaunched an airstrike! TAKE COVER!!");
            }
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    if(!(main.PlayingPlayers.isEmpty())){
                        if(!(getPlayersOnOtherTeam.get(e.getPlayer()).isEmpty())){
                            for(Player pp : getPlayersOnOtherTeam.get(e.getPlayer())){
                                if(!(isUnderRoof(pp))){
                                    pp.getWorld().createExplosion(pp.getLocation().getX(), pp.getLocation().getY(), pp.getLocation().getZ(), 3.0F, false, false);
                                    pp.damage(2000);
                                }
                            }
                        }
                    }
                }
            };
            br.runTaskLater(ThisPlugin.getPlugin(), 60L);
        }
    }

    private boolean isUnderRoof(Player p) {
        int x = p.getLocation().getBlockX();
        final int y = p.getLocation().getBlockY();
        int z = p.getLocation().getBlockZ();
        World world = p.getLocation().getWorld();

        for (int i = y ; i < world.getMaxHeight() ; i++) {
            Block block = world.getBlockAt(x, i, z);
            if ((block.getType().isSolid() && (block.getType()) != Material.BARRIER)) return true;
        }

        return false;
    }
}
