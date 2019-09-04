package com.tatemylove.COD2.KillStreaks;

import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Mortar {

    public final static ItemStack napalm = new ItemStack(Material.SUGAR);


    public static void settUp(){
        ItemMeta meta = napalm.getItemMeta();
        meta.setDisplayName("§bAirStrike");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§dAirStrike waiting on your go");
        meta.setLore(lore);
        napalm.setItemMeta(meta);
    }

    public void onEntityKill(EntityDeathEvent e, ArrayList<Player> PlayingPlayers, HashMap<UUID, Integer> killStreak){
        Entity entity = e.getEntity();
        Entity entity1 = e.getEntity().getKiller();

        if(entity instanceof Player) {
            if(entity1 instanceof Player) {
                Player p = (Player) e.getEntity();
                Player pp = e.getEntity().getKiller();

                if ((PlayingPlayers.contains(p)) && (PlayingPlayers.contains(pp))) {
                    if (killStreak.get(pp.getUniqueId()) == 4) {
                        pp.getInventory().addItem(napalm);
                        pp.sendMessage(Main.prefix + "§6AirStrike waiting on your go!");
                    }
                }
            }
        }
    }

    public void onInteract(PlayerInteractEvent e, ArrayList<Player> PlayingPlayers, ArrayList<Player> RedTeam, ArrayList<Player> BlueTeam){
        GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam();


        if(e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(napalm)){
            e.getPlayer().getInventory().setItemInMainHand(null);
            e.getPlayer().sendMessage(Main.prefix + "§e§lFIRE IN THE HOLE!!");


            for(Player p : PlayingPlayers){
                p.sendMessage(Main.prefix + "§a§l" + e.getPlayer().getName() + " §b§llaunched an airstrike! TAKE COVER!!");
            }
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    if(!(PlayingPlayers.isEmpty())){
                        if(!(getPlayersOnOtherTeam.get(e.getPlayer(), RedTeam, BlueTeam).isEmpty())){
                            for(Player pp : getPlayersOnOtherTeam.get(e.getPlayer(), RedTeam, BlueTeam)){
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
