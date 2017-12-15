package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class AttackDogs {
    Main main;
    private static AttackDogs attackDogs = null;
    public ItemStack dogs = new ItemStack(Material.BONE);

    public AttackDogs(Main m){
        main = m;
        attackDogs = AttackDogs.this;
    }

    public void settUp(){
        ItemMeta meta = dogs.getItemMeta();
        meta.setDisplayName("§c§lDogs");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6RELEASE THE HOUNDS!");
        meta.setLore(lore);
        dogs.setItemMeta(meta);
    }
    public void onKill(PlayerDeathEvent e){
        Player p = e.getEntity();
        Player pp = e.getEntity().getKiller();

        TDM tdm = new TDM(main);

        if((main.PlayingPlayers.contains(p)) &&(main.PlayingPlayers.contains(pp))){
            if(main.killStreak.get(p.getName()) == 15){
                pp.getInventory().addItem(dogs);
                pp.sendMessage(main.prefix + "§c§lYou got Dogs. Right click to deploy!");

            }
        }
    }

    public void onInteract(PlayerInteractEvent e) {
        GetPlayersOnOtherTeam getPlayersOnOtherTeam = new GetPlayersOnOtherTeam(main);
        if (e.getItem() == null) return;
        if (!(e.getItem().getType() == Material.AIR)) return;
        if (e.getItem().equals(dogs)) ;
        if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))))
            return;

        e.getPlayer().sendMessage(main.prefix + "§5You released the hounds");

        for(Player pp : main.PlayingPlayers){
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
}
