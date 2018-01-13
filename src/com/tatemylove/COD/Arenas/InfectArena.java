package com.tatemylove.COD.Arenas;

import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.ScoreBoard.GameBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InfectArena {
    public static ArrayList<Player> infect = new ArrayList<>();
    public static ArrayList<Player> humans = new ArrayList<>();
    public static HashMap<Player, String> Team = new HashMap<>();
    Main main;

    public InfectArena(Main m){
        main = m;
    }

    public void assignTeams(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(ArenaFile.getData().contains("Arenas." + id)){
                main.PlayingPlayers.addAll(main.WaitingPlayers);
                main.WaitingPlayers.clear();

                for(int assign = 0; assign < main.PlayingPlayers.size(); assign++){
                    Player p = main.PlayingPlayers.get(assign);

                    if(infect.size() < 1){
                        infect.add(p);
                    }else if(infect.size() >= 1){
                        humans.add(p);
                    }else{
                        Random RandomTeam = new Random();
                        int teamID = 0;
                        teamID = RandomTeam.nextInt(2);

                        if(teamID == 0){
                            infect.add(p);
                        }else{
                            humans.add(p);
                        }
                    }
                    if(infect.contains(p)){
                        Team.put(p, "Infect");
                    }else if(humans.contains(p)){
                        Team.put(p, "Humans");
                    }
                    continue;
                }
            }
        }
    }
    public void startInfect(String id){
       // MainRunnable mainRunnable = new MainRunnable(main);
        //mainRunnable.startGameTime();
        GetArena getArena = new GetArena();
        if (ArenaFile.getData().contains("Arenas." + id + ".Name")) {
            if (BaseArena.states == BaseArena.ArenaStates.Started) {
                if (BaseArena.type == BaseArena.ArenaType.INFECT) {
                    for (int ID = 0; ID < main.PlayingPlayers.size(); ID++) {
                        final Player p = main.PlayingPlayers.get(ID);

                        GameBoard gameBoard = new GameBoard(main);
                        gameBoard.setGameBoard(p);
                        p.getInventory().clear();

                        p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife", null));


                        p.closeInventory();
                        if (infect.contains(p)) {

                            p.teleport(GetArena.getRedSpawn(p));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
                                    SendCoolMessages.sendSubTitle(p, "§7§lYOU JOINED THE §8§lINFECTED TEAM", 10, 30, 10);
                                }
                            }, 40);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setFoodLevel(20);
                            p.setHealth(20);
                            p.setDisplayName("§8" + p.getName());
                            p.setCustomNameVisible(true);
                            p.setPlayerListName("§8" + p.getName());

                            Color c = Color.fromBGR(0, 0, 255);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                           // SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + getBetterTeam(), p);


                        } else if (humans.contains(p)) {

                            if(KitFile.getData().contains(p.getUniqueId().toString() + ".Primary.GunName")){
                                CSUtility csUtility = new CSUtility();
                                ItemStack gun = csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.GunName"));
                                p.getInventory().setItem(2, gun);

                                ItemStack ammo = new ItemStack(Material.getMaterial(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.AmmoData")), Integer.parseInt(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.AmmoAmount")));
                                ItemMeta meta = ammo.getItemMeta();
                                meta.setDisplayName("§e§lPrimary Ammo");
                                ammo.setItemMeta(meta);

                                p.getInventory().setItem(3, ammo);
                            }

                            if(KitFile.getData().contains(p.getUniqueId().toString() + ".Secondary.GunName")){
                                CSUtility csUtility = new CSUtility();
                                ItemStack gun = csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Secondary.GunName"));
                                p.getInventory().setItem(5, gun);

                                ItemStack ammo = new ItemStack(Material.getMaterial(KitFile.getData().getString(p.getUniqueId().toString() + ".Secondary.AmmoData")), Integer.parseInt(KitFile.getData().getString(p.getUniqueId().toString() + ".Secondary.AmmoAmount")));
                                ItemMeta meta = ammo.getItemMeta();
                                meta.setDisplayName("§e§lSecondary Ammo");
                                ammo.setItemMeta(meta);

                                p.getInventory().setItem(6, ammo);
                            }

                            p.teleport(GetArena.getBlueSpawn(p));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
                                    SendCoolMessages.sendSubTitle(p, "§2§lYOU JOINED THE §a§lHUMANS TEAM", 10, 30, 10);
                                }
                            }, 40);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setFoodLevel(20);
                            p.setHealth(20);
                            p.setDisplayName("§2" + p.getName());
                            p.setCustomNameVisible(true);
                            p.setPlayerListName("§2" + p.getName());

                            Color c = Color.fromBGR(255, 0, 0);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                           // SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + getBetterTeam(), p);
                        }
                    }
                }
            }
        }
    }

    public void endInfect(Player pp){
        MainRunnable runnable = new MainRunnable(main);
        GetLobby lobby = new GetLobby(main);

        BaseArena.states = BaseArena.ArenaStates.Countdown;

        runnable.stopCountDown();
        runnable.startCountDown();

        new BukkitRunnable(){

            @Override
            public void run() {
                if(!main.getConfig().getBoolean("BungeeCord.Enabled")) {
                    pp.teleport(lobby.getLobby(pp));
                }else{
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);

                    try{
                        out.writeUTF("Connect");
                        out.writeUTF(main.getConfig().getString("BungeeCord.fallback-server"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    pp.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
                }
                pp.closeInventory();
                pp.getInventory().clear();


                main.WaitingPlayers.addAll(main.PlayingPlayers);
                main.PlayingPlayers.clear();
            }
        }.runTaskLater(ThisPlugin.getPlugin(), 10L);


    }
    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta me = s.getItemMeta();
        me.setLore(lore);
        me.setDisplayName(name);
        s.setItemMeta(me);
        return s;
    }
    private ItemStack getColorArmor (Material m, Color c){
        ItemStack s = new ItemStack(m);
        LeatherArmorMeta l = (LeatherArmorMeta) s.getItemMeta();
        l.setColor(c);
        s.setItemMeta(l);
        return s;
    }

    public Location respawnPlayer(Player p){
        if(infect.contains(p)){
            GetArena getArena = new GetArena();
            return GetArena.getRedSpawn(p);
        }else if(humans.contains(p)){
            GetArena getArena = new GetArena();
            return GetArena.getBlueSpawn(p);
        }else{
            return null;
        }

    }
}
