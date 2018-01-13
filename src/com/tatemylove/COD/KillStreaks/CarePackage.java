package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Random;

public class CarePackage {
    private final static ItemStack carePackage = new ItemStack(Material.EGG);

    Main main;

    public CarePackage(Main m){
        main = m;
    }

    public static void settUp(){
        ItemMeta m = carePackage.getItemMeta();
        m.setDisplayName("§7§lCare-Package");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6Right click to deploy!");
        m.setLore(lore);
        carePackage.setItemMeta(m);

    }


    public void onKill(EntityDeathEvent e){
        Entity one = e.getEntity();
        Entity two = e.getEntity().getKiller();

        if(one instanceof Player){
            if(two instanceof Player){
                Player p = (Player) e.getEntity();
                Player pp = e.getEntity().getKiller();

                if((main.PlayingPlayers.contains(p)) && (main.PlayingPlayers.contains(pp))){
                    if(Main.killStreak.get(pp.getName()) == 3){
                        pp.getInventory().addItem(carePackage);
                        pp.sendMessage(main.prefix + "§6§lYou got a Care-Package. Right click to deploy!");
                    }
                }
            }
        }
    }

    public void onUse(PlayerEggThrowEvent e){

       // if((e.getPlayer().getInventory().getItemInMainHand().equals(carePackage))){
            e.setHatching(false);

            final Location loc = e.getEgg().getLocation();

            Block block = loc.getBlock();
            loc.getBlock().setType(Material.CHEST);

            Chest chest = (Chest) block.getState();
            chest.setMetadata("codChest", new FixedMetadataValue(ThisPlugin.getPlugin(), chest));

            Inventory inv = chest.getInventory();

            Random random = new Random();

            int id = 0;
            id = random.nextInt(2);

            if(id == 0){
                inv.addItem(Napalm.napalm);
            }else if(id == 1){
                inv.addItem(AttackDogs.dogs);
           // }

        }
    }
}
