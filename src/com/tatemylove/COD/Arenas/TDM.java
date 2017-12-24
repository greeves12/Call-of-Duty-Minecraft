package com.tatemylove.COD.Arenas;

import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.ScoreBoard.GameBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TDM  {
    public static ArrayList<Player> redTeam = new ArrayList<>();
    public static ArrayList<Player> blueTeam = new ArrayList<>();
    public static HashMap<Player, String> Team = new HashMap<>();


    Main main;

    private static TDM tdms = null;

    public TDM(Main ma) {
        main = ma;
        tdms = TDM.this;
    }

    public void assignTeams(String id) {
        if (BaseArena.states == BaseArena.ArenaStates.Started) {
            if (BaseArena.type == BaseArena.ArenaType.TDM) {
                if (ArenaFile.getData().contains("Arenas." + id + ".Name")) {
                    main.PlayingPlayers.addAll(main.WaitingPlayers);
                    main.WaitingPlayers.clear();
                    redTeam.clear();
                    blueTeam.clear();
                    for (int assign = 0; assign < main.PlayingPlayers.size(); assign++) {
                        Player p = main.PlayingPlayers.get(assign);

                        if (redTeam.size() < blueTeam.size()) {
                            redTeam.add(p);
                        } else if (blueTeam.size() < redTeam.size()) {
                            blueTeam.add(p);
                        } else {
                            Random RandomTeam = new Random();
                            int TeamID = 0;
                            TeamID = RandomTeam.nextInt(2);
                            if (TeamID == 0) {
                                redTeam.add(p);
                            } else {
                                blueTeam.add(p);
                            }
                        }
                        if (redTeam.contains(p)) {
                            Team.put(p, "Red");
                        } else {
                            Team.put(p, "Blue");
                        }
                        continue;
                    }
                }
            }
        }
    }

    public void startTDM(String id) {
        MainRunnable mainRunnable = new MainRunnable(main);
        mainRunnable.startGameTime();
        GetArena getArena = new GetArena();
        if (ArenaFile.getData().contains("Arenas." + id + ".Name")) {
            if (BaseArena.states == BaseArena.ArenaStates.Started) {
                if (BaseArena.type == BaseArena.ArenaType.TDM) {
                    main.RedTeamScore = 0;
                    main.BlueTeamScore = 0;
                    for (int ID = 0; ID < main.PlayingPlayers.size(); ID++) {
                        final Player p = main.PlayingPlayers.get(ID);

                        GameBoard gameBoard = new GameBoard(main);
                        gameBoard.setGameBoard(p);

                        /*if(KitFile.getData().contains(p.getUniqueId().toString())){
                            CSUtility csUtility = new CSUtility();
                            csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Secondary.GunName"));
                        }*/
                        if (redTeam.contains(p)) {
                            p.getInventory().clear();

                            p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife", null));

                            if(KitFile.getData().contains(p.getUniqueId().toString() + ".Primary.GunName")){
                                CSUtility csUtility = new CSUtility();
                                ItemStack gun = csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.GunName"));
                                p.getInventory().setItem(2, gun);
                            }

                            p.teleport(getArena.getRedSpawn(p));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
                                    SendCoolMessages.sendSubTitle(p, "§c§lYOU JOINED THE §4§lRED TEAM", 10, 30, 10);
                                }
                            }, 40);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setFoodLevel(20);
                            p.setHealth(20);
                            p.setDisplayName("§c" + p.getName());
                            p.setCustomNameVisible(true);
                            p.setPlayerListName("§c" + p.getName());

                            Color c = Color.fromBGR(0, 0, 255);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                            SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + getBetterTeam(), p);


                        } else if (blueTeam.contains(p)) {
                            p.getInventory().clear();
                            p.teleport(getArena.getBlueSpawn(p));

                            p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife", null));

                            if(KitFile.getData().contains(p.getUniqueId().toString() + ".Primary.GunName")){
                                CSUtility csUtility = new CSUtility();
                                ItemStack gun = csUtility.generateWeapon(KitFile.getData().getString(p.getUniqueId().toString() + ".Primary.GunName"));
                                p.getInventory().setItem(2, gun);
                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                @Override
                                public void run() {
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
                                    SendCoolMessages.sendSubTitle(p, "§9§lYOU JOINED THE §1§lBLUE TEAM", 10, 30, 10);
                                }
                            }, 40);
                            p.setGameMode(GameMode.SURVIVAL);
                            p.setFoodLevel(20);
                            p.setHealth(20);
                            p.setDisplayName("§9" + p.getName());
                            p.setCustomNameVisible(true);
                            p.setPlayerListName("§9" + p.getName());

                            Color c = Color.fromBGR(255, 0, 0);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                            SendCoolMessages.TabHeaderAndFooter("§9§lBlue §1§lTeam", "§6§lCOD\n" + getBetterTeam(), p);
                        }
                    }
                }
            }
        }

    }
    private ItemStack getColorArmor(Material m, Color c) {
        ItemStack i = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta(meta);
        return i;
    }
    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta meta = s.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        s.setItemMeta(meta);
        return s;
    }

    public String getBetterTeam() {
        if (main.RedTeamScore > main.BlueTeamScore) {
            String team = "§c§lRed: §4§l" + main.RedTeamScore + " " + "§9§lBlue: §1§l" + main.BlueTeamScore;
            return team;
        } else if (main.BlueTeamScore > main.RedTeamScore) {
            String team = "§9§lBlue: §1§l" + main.BlueTeamScore + " " + "§c§lRed: §4§l" + main.RedTeamScore;
            return team;
        } else {
            String team = "§e§lTie: §6§l" + main.RedTeamScore + " §e§l- §6§l" + main.BlueTeamScore;
            return team;
        }
    }

    public void endTDM() {
        GetArena getArena = new GetArena();
        BaseArena.states = BaseArena.ArenaStates.Countdown;
        for(Player pp : main.PlayingPlayers) {
            if (!main.getConfig().getBoolean("MySQL.Enabled")) {
                int kills = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Kills");
                int deaths = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Deaths");

                int inKills = main.kills.get(pp.getName());
                int inDeaths = main.deaths.get(pp.getName());

                StatsFile.getData().set(pp.getUniqueId().toString() + ".Kills", inKills + kills);
                StatsFile.getData().set(pp.getUniqueId().toString() + ".Deaths", inDeaths + deaths);

                StatsFile.saveData();
                StatsFile.reloadData();
            }else{

            }
        }
        if (main.RedTeamScore > main.BlueTeamScore) {
            for(Player pp : redTeam){
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("win-amount"));
            }
            for(Player pp : blueTeam){
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("lose-amount"));
            }
            for (Player pp : main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + getArena.getCurrentArena());
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §c§lRed: §1§l" + main.BlueTeamScore + " " + "§r§9Blue: §4" + main.RedTeamScore + "         §b§lTotal Kills:§a§l ");

                DecimalFormat df = new DecimalFormat("#.##");

                if(!main.getConfig().getBoolean("MySQL.Enabled")) {
                    int wins = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Wins");

                    StatsFile.getData().set(pp.getUniqueId().toString() + ".Wins", wins+1);

                    StatsFile.saveData();
                    StatsFile.reloadData();
                }


            }
        }else if(main.BlueTeamScore > main.RedTeamScore){
            for(Player pp : blueTeam){
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("win-amount"));
            }
            for(Player pp : redTeam){
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("lose-amount"));
            }
            for(Player pp : main.PlayingPlayers){
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + getArena.getCurrentArena());
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §9§lBlue: §1§l" + main.BlueTeamScore + " " + "§r§cRed: §4" + main.RedTeamScore + "         §b§lTotal Kills:§a§l ");

                DecimalFormat df = new DecimalFormat("#.##");

                int wins = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Wins");

                StatsFile.getData().set(pp.getUniqueId().toString() + ".Wins", wins+1);

                StatsFile.saveData();
                StatsFile.reloadData();
            }
        }
        main.WaitingPlayers.addAll(main.PlayingPlayers);
        main.PlayingPlayers.clear();
        redTeam.clear();
        blueTeam.clear();
    }

    public Location respawnPlayer(Player p){
        if(redTeam.contains(p)){
            GetArena getArena = new GetArena();
            return getArena.getRedSpawn(p);
        }else if(blueTeam.contains(p)){
            GetArena getArena = new GetArena();
            return getArena.getBlueSpawn(p);
        }else{
            return null;
        }

    }
}
