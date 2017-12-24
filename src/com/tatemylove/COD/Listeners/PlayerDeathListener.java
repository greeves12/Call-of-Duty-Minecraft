package com.tatemylove.COD.Listeners;

import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.GetArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.KillStreaks.AttackDogs;
import com.tatemylove.COD.KillStreaks.Moab;
import com.tatemylove.COD.KillStreaks.Napalm;
import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import net.minecraft.server.v1_12_R1.ItemMapEmpty;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerDeathListener implements Listener {
    Main main;
    public PlayerDeathListener(Main m){
        main = m;
    }
    public static ArrayList<Player> invincible = new ArrayList<>();

    @EventHandler
    public void onPDeath(PlayerDeathEvent e){
        if(main.PlayingPlayers.contains(e.getEntity())){
            e.setDeathMessage(null);
        }
    }


    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Entity one = e.getEntity();
        Entity two = e.getEntity().getKiller();
        if (one instanceof Player) {

            Player p = (Player) e.getEntity();

            final CraftPlayer craftPlayer = (CraftPlayer) p;

            e.getDrops().clear();

            if (BaseArena.type == BaseArena.ArenaType.TDM) {

                if (two instanceof Player) {
                    Player pp = e.getEntity().getKiller();
                    //if(Main.killStreak.containsKey(pp.getName())) {
                    Main.killStreak.put(pp.getName(), Main.killStreak.get(pp.getName()) + 1);
                    //}
                    //if(Main.killStreak.containsKey(p.getName())) {
                    Main.killStreak.put(p.getName(), 0);
                    //}
                    //if(Main.kills.containsKey(pp.getName())) {
                    Main.kills.put(pp.getName(), Main.kills.get(pp.getName()) + 1);
                    //}
                    // if(Main.deaths.containsKey(p.getName())) {
                    Main.deaths.put(p.getName(), Main.deaths.get(p.getName()) + 1);
                    //}
                }

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (p.isDead()) {
                            craftPlayer.getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                        }
                    }
                }.runTaskLater(ThisPlugin.getPlugin(), 20L);


            } else if (BaseArena.type == BaseArena.ArenaType.KC) {

            }
            AttackDogs dogs = new AttackDogs(main);
            dogs.onKill(e);

            //Moab moab = new Moab(main);
            //moab.onEntityKill(e);

            //Napalm napalm = new Napalm(main);
            //napalm.onEntityKill(e);
            if (two instanceof Player) {
                Player pp = e.getEntity().getKiller();
                PlayerLevels playerLevels = new PlayerLevels(main);
                int exp = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".EXP");
                int level = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Level");
                playerLevels.addExp(pp, main.getConfig().getInt("exp-per-kill"));

                if (level == 1) {
                    if (exp >= playerLevels.levelTwo) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 2) {
                    if (exp >= playerLevels.levelThree) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 3) {
                    if (exp >= playerLevels.levelFour) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 4) {
                    if (exp >= playerLevels.levelFive) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 5) {
                    if (exp >= playerLevels.levelSix) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 6) {
                    if (exp >= playerLevels.levelSeven) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 7) {
                    if (exp >= playerLevels.levelEight) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 8) {
                    if (exp >= playerLevels.levelNine) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                } else if (level == 9) {
                    if (exp >= playerLevels.levelTen) {
                        playerLevels.addLevel(pp, 1);
                        playerLevels.resetExp(pp);
                    }
                }
                invincible.add(p);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        TDM tdm = new TDM(main);

        e.setRespawnLocation(tdm.respawnPlayer(p));
        p.getInventory().clear();
        if(KitFile.getData().contains(p.getUniqueId().toString() + ".Primary.GunName")){
            CSUtility csUtility = new CSUtility();
            ItemStack gun = csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.GunName"));
            p.getInventory().setItem(2, gun);
        }
        p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife"));
        if(TDM.blueTeam.contains(p)){
            main.RedTeamScore++;
            Color c = Color.fromRGB(0,0,255);
            p.getInventory().setHelmet(getColor(Material.LEATHER_HELMET, c));
            p.getInventory().setChestplate(getColor(Material.LEATHER_CHESTPLATE, c));
            p.getInventory().setLeggings(getColor(Material.LEATHER_LEGGINGS, c));
            p.getInventory().setBoots(getColor(Material.LEATHER_BOOTS, c));
        }else if(TDM.redTeam.contains(p)){
            main.BlueTeamScore++;
            Color c = Color.fromRGB(255,0,0);
            p.getInventory().setHelmet(getColor(Material.LEATHER_HELMET, c));
            p.getInventory().setChestplate(getColor(Material.LEATHER_CHESTPLATE, c));
            p.getInventory().setLeggings(getColor(Material.LEATHER_LEGGINGS, c));
            p.getInventory().setBoots(getColor(Material.LEATHER_BOOTS, c));
        }
        new BukkitRunnable(){
            int time = main.getConfig().getInt("invincible");
            @Override
            public void run() {
                if(time == 0){
                    cancel();
                    invincible.remove(p);
                    p.sendMessage(main.prefix + "§cPvP timer has ended for you");
                }
                time-=1;
            }
        }.runTaskTimer(ThisPlugin.getPlugin(), 0L, 20L);
    }

    private ItemStack getColor(Material m, Color c){
        ItemStack stack = new ItemStack(m);
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(c);
        stack.setItemMeta(meta);
        return stack;
    }
    private ItemStack getMaterial(Material m, String name){
        ItemStack stack = new ItemStack(m);
        ItemMeta me = stack.getItemMeta();
        me.setDisplayName(name);
        stack.setItemMeta(me);
        return stack;
    }
}
