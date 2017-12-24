package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.KillStreaks.GetPlayersOnOtherTeam;
import com.tatemylove.COD.KillStreaks.Moab;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerInteractItem implements Listener {

    Main main;
    private PlayerInteractItem interactEvent = null;

    public PlayerInteractItem(Main m) {
        main = m;
        interactEvent = PlayerInteractItem.this;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        /*if (main.PlayingPlayers.contains(e.getPlayer())) {
            GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam(main);
            //if (e.getItem() == null) return;
            //if (!(e.getItem().getType() == Material.AIR)) return;
            /*if (!e.getItem().equals(AttackDogs.dogs)) return;
            if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))))
                return;

            if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().equals(AttackDogs.dogs)) {
                e.getPlayer().sendMessage(main.prefix + "§5You released the hounds");

                for (Player pp : main.PlayingPlayers) {
                    pp.sendMessage(main.prefix + e.getPlayer().getName() + " §6§lreleased the hounds!");
                }

                final Player p = e.getPlayer();

                p.getInventory().setItemInMainHand(null);

                if (!(main.PlayingPlayers.isEmpty())) {
                    if (!(getPlayersOnOtherTeam.get(p).isEmpty())) {
                        for (int i = 0; i < 5; i++) {
                            Player pp = getPlayersOnOtherTeam.get(p).get(new Random().nextInt(getPlayersOnOtherTeam.get(p).size()));

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
                        p.sendMessage(main.prefix + "§cThere needs to be 1 more player for this killsteak to work!");
                    }
                }
            }

            //Moab moab = new Moab(main);
            //moab.onPlayerIneteract(e);
        }
    }
    */
        AttackDogs dogs = new AttackDogs(main);
        dogs.onInteract(e);
    }
}

