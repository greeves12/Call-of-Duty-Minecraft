package com.tatemylove.COD.Arenas;

import com.shampaggon.crackshot.CSUtility;
import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.LanguageFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Leveling.PlayerLevels;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.MySQL.DeathsSQL;
import com.tatemylove.COD.MySQL.KillsSQL;
import com.tatemylove.COD.MySQL.WinsSQL;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.ScoreBoard.GameBoard;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
                        p.getInventory().clear();

                        p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife", null));

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
                        p.closeInventory();
                        if (redTeam.contains(p)) {

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
                            p.setCustomName("§c" + p.getName());
                            p.setCustomNameVisible(true);
                            p.setPlayerListName("§c" + p.getName());

                            Color c = Color.fromBGR(0, 0, 255);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                            SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD\n" + getBetterTeam(), p);


                        } else if (blueTeam.contains(p)) {
                            p.teleport(getArena.getBlueSpawn(p));

                            p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§eKnife", null));

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
                            p.setCustomName("§9" + p.getName());
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
        MainRunnable runnable = new MainRunnable(main);
        runnable.stopCountDown();
        runnable.startCountDown();
        for(Player pp : main.PlayingPlayers) {
            GetLobby lobby = new GetLobby(main);
            pp.getInventory().clear();
            pp.teleport(lobby.getLobby(pp));
            if (!main.getConfig().getBoolean("MySQL.Enabled")) {
                int kills = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Kills");
                int deaths = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Deaths");

                int inKills = Main.kills.get(pp.getName());
                int inDeaths = Main.deaths.get(pp.getName());

                StatsFile.getData().set(pp.getUniqueId().toString() + ".Kills", inKills + kills);
                StatsFile.getData().set(pp.getUniqueId().toString() + ".Deaths", inDeaths + deaths);

                StatsFile.saveData();
                StatsFile.reloadData();
            }else{
                int inKills = Main.kills.get(pp.getName());
                int inDeaths = Main.deaths.get(pp.getName());

                DeathsSQL deathsSQL = new DeathsSQL(main);
                WinsSQL winsSQL = new WinsSQL(main);
                KillsSQL killsSQL = new KillsSQL(main);
                deathsSQL.addDeaths(pp, inDeaths);
                winsSQL.addWins(pp, 1);
                killsSQL.addKills(pp, inKills);
            }
            Main.kills.put(pp.getName(), 0);
            Main.deaths.put(pp.getName(), 0);
            Main.killStreak.put(pp.getName(), 0);

            LobbyBoard lobbyBoard = new LobbyBoard(main);
            if(!main.getConfig().getBoolean("MySQL.Enabled")) {
                int kills = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Kills");
                int wins = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Wins");
                int deaths = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Deaths");

                LobbyBoard.killsH.put(pp.getName(), kills);
                LobbyBoard.deathsH.put(pp.getName(), deaths);
                LobbyBoard.winsH.put(pp.getName(), wins);
                pp.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                lobbyBoard.setLobbyBoard(pp);
            }else{
                DeathsSQL deathsSQL = new DeathsSQL(main);
                WinsSQL winsSQL = new WinsSQL(main);
                KillsSQL killsSQL = new KillsSQL(main);
                WinsSQL.getWins(pp);
                KillsSQL.getKills(pp);
                DeathsSQL.getDeaths(pp);
                pp.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                lobbyBoard.setLobbyBoard(pp);
            }
        }
        if (main.RedTeamScore > main.BlueTeamScore) {
            for(Player pp : redTeam){
                PlayerLevels level = new PlayerLevels(main);
                level.addExp(pp, 5000);
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("win-amount"));

                SendCoolMessages.sendTitle(pp, ChatColor.translateAlternateColorCodes('&', LanguageFile.getData().getString("win-message")), 30, 50, 30);
            }
            for(Player pp : blueTeam){
                PlayerLevels level = new PlayerLevels(main);
                level.addExp(pp, 1000);
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("lose-amount"));

                SendCoolMessages.sendTitle(pp, ChatColor.translateAlternateColorCodes('&', LanguageFile.getData().getString("lose-message")), 30, 50, 30);
            }
            for (Player pp : main.PlayingPlayers) {
                int kills = Main.kills.get(pp.getName());
                int deaths = Main.deaths.get(pp.getName());
                double values = (double) kills / (double) deaths;
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + getArena.getCurrentArena());
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §c§lRed: §1§l" + main.BlueTeamScore + " " + "§r§9Blue: §4" + main.RedTeamScore + "         §b§lTotal Kills:§a§l ");
                pp.sendMessage("§7║");

                pp.sendMessage("§7║ §lKD: §5" + values);

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
                PlayerLevels level = new PlayerLevels(main);
                level.addExp(pp, 5000);
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("win-amount"));
            }
            for(Player pp : redTeam){
                PlayerLevels level = new PlayerLevels(main);
                level.addExp(pp, 1000);
                SwiftEconomyAPI swiftEconomyAPI = new SwiftEconomyAPI();
                swiftEconomyAPI.giveMoney(pp, ThisPlugin.getPlugin().getConfig().getDouble("lose-amount"));
            }
            for(Player pp : main.PlayingPlayers){
                int kills = Main.kills.get(pp.getName());
                int deaths = Main.deaths.get(pp.getName());

                double value = (double) kills / (double)deaths;

                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + getArena.getCurrentArena());
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §9§lBlue: §1§l" + main.BlueTeamScore + " " + "§r§cRed: §4" + main.RedTeamScore + "         §b§lTotal Kills:§a§l " + Main.kills.get(pp.getName()));
                pp.sendMessage("§7║");


                pp.sendMessage("§7║ §lKD: §5" + value);

                DecimalFormat df = new DecimalFormat("#.##");

                if(!main.getConfig().getBoolean("MySQL.Enabled")) {
                    int wins = StatsFile.getData().getInt(pp.getUniqueId().toString() + ".Wins");

                    StatsFile.getData().set(pp.getUniqueId().toString() + ".Wins", wins + 1);

                    StatsFile.saveData();
                    StatsFile.reloadData();
                }
            }
        }
        if(!main.getConfig().getBoolean("BungeeCord.Enabled")) {
            main.WaitingPlayers.addAll(main.PlayingPlayers);
        }else{
            for(Player pp : main.PlayingPlayers){
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
        }
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
