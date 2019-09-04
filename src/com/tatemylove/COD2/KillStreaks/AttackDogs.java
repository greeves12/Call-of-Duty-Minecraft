package com.tatemylove.COD2.KillStreaks;

import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class AttackDogs {

    private static AttackDogs attackDogs = null;
    public final static ItemStack dogs = new ItemStack(Material.BONE);


    public static void settUp(){
        ItemMeta meta = dogs.getItemMeta();
        meta.setDisplayName("§c§lDogs");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6RELEASE THE HOUNDS!");
        meta.setLore(lore);
        dogs.setItemMeta(meta);
    }
    public void onKill(EntityDeathEvent e, HashMap<UUID, Integer> killStreak, ArrayList<Player> PlayingPlayers){
        Entity one = e.getEntity();
        Entity two = e.getEntity().getKiller();



        if(one instanceof Player) {
            if (two instanceof Player) {
                Player p = (Player) e.getEntity();
                Player pp = e.getEntity().getKiller();

                if ((PlayingPlayers.contains(p)) && (PlayingPlayers.contains(pp))) {
                    if (killStreak.get(pp.getName()) == 8) {
                        pp.getInventory().addItem(dogs);
                        pp.sendMessage(Main.prefix + "§c§lYou got Dogs. Right click to deploy!");

                    }
                }
            }
        }
    }

    public void onInteract(PlayerInteractEvent e, ArrayList<Player> PlayingPlayers, ArrayList<Player> RedTeam, ArrayList<Player> BlueTeam) {
        GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam();

        if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(AttackDogs.dogs)) {
            e.getPlayer().sendMessage(Main.prefix + "§5You released the hounds");

            for (Player pp : PlayingPlayers) {
                pp.sendMessage(Main.prefix + e.getPlayer().getName() + " §6§lreleased the hounds!");
            }

            final Player p = e.getPlayer();


            if (!(PlayingPlayers.isEmpty())) {
                if (!(getPlayersOnOtherTeam.get(p, RedTeam, BlueTeam).isEmpty())) {
                    p.getInventory().setItemInMainHand(null);
                    for (int i = 0; i < 5; i++) {
                        Player pp = getPlayersOnOtherTeam.get(p, RedTeam, BlueTeam).get(new Random().nextInt(getPlayersOnOtherTeam.get(p, RedTeam, BlueTeam).size()));

                        Location loc = p.getLocation();
                        final Wolf w = p.getWorld().spawn(loc, Wolf.class);

                        w.setMetadata("codAllowHit", new FixedMetadataValue(ThisPlugin.getPlugin(), w));
                        w.setAngry(true);
                        w.setAdult();
                        w.setOwner(p);
                        w.setCollarColor(DyeColor.BLUE);
                        w.setTarget(pp);

                        BukkitRunnable br = new BukkitRunnable() {
                            public void run() {
                                w.remove();
                            }
                        };

                        br.runTaskLater(ThisPlugin.getPlugin(), 20 * 30);
                    }
                } else {
                    p.sendMessage(Main.prefix + "§cThere needs to be 1 more player for this killsteak to work!");
                }
            }
        }
    }
}
