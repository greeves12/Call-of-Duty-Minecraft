package com.tatemylove.COD.Commands;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Citizens.JoinNPC;
import com.tatemylove.COD.Citizens.LeaveNPC;
import com.tatemylove.COD.Citizens.TryGuns;
import com.tatemylove.COD.Files.*;
import com.tatemylove.COD.Guns.BuyGuns;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Inventories.Kits;
import com.tatemylove.COD.JSON.HoverMessages;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.MySQL.DeathsSQL;
import com.tatemylove.COD.MySQL.KillsSQL;
import com.tatemylove.COD.MySQL.WinsSQL;
import com.tatemylove.COD.Runnables.CountDown;
import com.tatemylove.COD.Runnables.MainRunnable;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.NewChat;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.HashMap;

public class MainCommand implements CommandExecutor {
    Main main;
    public MainCommand(Main m){
        main = m;
    }
    public HashMap<Player, ItemStack[]> savedInventory = new HashMap<>();
    public HashMap<Player, ItemStack[]> armorSaved = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        CreateArenaCommand createArenaCommand = new CreateArenaCommand(main);
        GetLobby getLobby = new GetLobby(main);
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                    p.sendMessage("§8§l§nCOD-Warfare");
                    p.sendMessage("");
                    p.sendMessage("§7Author: §etatemylove");
                    p.sendMessage("§7Commands: §e/cod help");
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                if(p.hasPermission("cod.help")){
                    HelpCommand helpCommand = new HelpCommand();
                    helpCommand.helpMe(p, args);
                }
            }
            if(args[0].equalsIgnoreCase("enable")){
                if(p.hasPermission("cod.enable")) {
                    if (!StatsFile.getData().getBoolean("plugin-enabled")) {
                        File file = new File("plugins/COD/arenas.yml");
                        File lobby = new File("plugins/COD/lobby.yml");
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            BufferedReader lobbyready = new BufferedReader(new FileReader(lobby));
                            if (reader.readLine() != null) {
                                if (lobbyready.readLine() != null) {
                                    MainRunnable runnable = new MainRunnable(main);
                                    runnable.startCountDown();
                                    p.sendMessage(main.prefix + "§bCOD is setup and ready to play. Have fun!");
                                    StatsFile.getData().set("plugin-enabled", true);
                                    StatsFile.saveData();
                                    StatsFile.reloadData();
                                } else {
                                    p.sendMessage(main.prefix + "§8Lobby: §6Not Set");
                                }
                            } else {
                                p.sendMessage(main.prefix + "§8Arenas: §6Not Set");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        p.sendMessage(main.prefix + "§aPlugin Already Enabled");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("tryguns")){
                if(p.hasPermission("cod.tryguns")){
                    if(!main.PlayingPlayers.contains(p)) {
                        if(main.WaitingPlayers.contains(p)) {
                            Guns guns = new Guns(main);
                            guns.createMainMenu(p);
                        }
                    }else{
                        p.sendMessage(main.prefix + "§c§lYou cannot be ingame");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("kit")){
                if(p.hasPermission("cod.kits")){
                    if(!main.PlayingPlayers.contains(p)) {
                        if(main.WaitingPlayers.contains(p)) {
                            Kits kits = new Kits(main);
                            kits.loadInventory(p);
                        }
                    }else{
                        p.sendMessage(main.prefix + "§c§lYou cannot be ingame");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("join")) {
                if (p.hasPermission("cod.join")) {
                    if (StatsFile.getData().getBoolean("plugin-enabled")) {
                        if(main.PlayingPlayers.contains(p)){
                            p.sendMessage(main.prefix + "§3§lYou cannot be in-game");
                            return true;
                        }
                        if((!main.WaitingPlayers.contains(p))) {
                            LobbyBoard lobbyBoard = new LobbyBoard(main);
                            main.WaitingPlayers.add(p);
                            p.teleport(getLobby.getLobby(p));

                            Main.kills.put(p.getName(), 0);
                            Main.deaths.put(p.getName(), 0);
                            Main.killStreak.put(p.getName(), 0);

                        if(!main.getConfig().getBoolean("MySQL.Enabled")) {
                            int kills = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Kills");
                            int wins = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Wins");
                            int deaths = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Deaths");

                            LobbyBoard.killsH.put(p.getName(), kills);
                            LobbyBoard.deathsH.put(p.getName(), deaths);
                            LobbyBoard.winsH.put(p.getName(), wins);
                            lobbyBoard.setLobbyBoard(p);
                        }else{
                            DeathsSQL deathsSQL = new DeathsSQL(main);
                            WinsSQL winsSQL = new WinsSQL(main);
                            KillsSQL killsSQL = new KillsSQL(main);
                            WinsSQL.getWins(p);
                            KillsSQL.getKills(p);
                            DeathsSQL.getDeaths(p);
                            lobbyBoard.setLobbyBoard(p);
                        }

                            if(!KitFile.getData().contains(p.getUniqueId().toString())){
                                HoverMessages hoverMessages = new HoverMessages();
                                p.sendMessage(main.prefix + "§6§l"+p.getName() +"! §7It appears you don't have a kit!");
                                hoverMessages.hoverMessage(p, "/cod kit", "§6§l§nClick here §d§lto select a Kit", "§e§lSelect a Kit");
                            }

                            if(p.getInventory().getContents().length > 0) {
                                savedInventory.put(p, p.getInventory().getContents());
                                p.getInventory().clear();
                            }

                            if(p.getInventory().getArmorContents().length > 0){
                                armorSaved.put(p, p.getInventory().getArmorContents());
                            }

                            SendCoolMessages.sendTitle(p, "§a", 10, 30, 10);
                            SendCoolMessages.sendSubTitle(p, "§e§lYou joined the Queue", 10, 30, 10);

                            for (Player pp : main.WaitingPlayers) {
                                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageFile.getData().getString("join-message").replace("%player%", p.getName())));
                            }
                        }else{
                            p.sendMessage(main.prefix + "§3§lAlready in the Queue");
                        }
                    }else{
                        p.sendMessage(main.prefix + "§7COD is not setup. Ask an Administrator to set it up.");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("create")){
                if(p.hasPermission("cod.create")) {
                    if(args.length == 3) {
                        String name = args[1];
                        if ((args[2].equalsIgnoreCase("tdm")) || (args[2].equalsIgnoreCase("kc")) || (args[2].equalsIgnoreCase("inf"))) {
                            createArenaCommand.createArena(p, name, args[2].toUpperCase());
                        }
                    }else{
                        p.sendMessage(main.prefix + "§9Available GameModes are §6TDM §a, §6KC §e(Kill Confirmed) §a, §6INF §e(Infected)");
                        p.sendMessage(main.prefix + "§7/cod create <name> <type>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("delete")){
                if(p.hasPermission("cod.delete")) {
                    Integer id = Integer.valueOf(args[1]);

                    if (args.length == 2) {
                        createArenaCommand.deleteArena(p, id);
                    }else{
                        p.sendMessage(main.prefix + "§7/cod delete <ID>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("set")){
                if(p.hasPermission("cod.setspawn")) {
                    if (args.length == 3) {
                        int id = Integer.parseInt(args[1]);
                        createArenaCommand.setSpawns(p, args, id);
                    }else{
                        p.sendMessage(main.prefix + "§7/cod set <ID> <blue/red>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("setlobby")){
                if(p.hasPermission("cod.setlobby")){
                    getLobby.setLobby(p);
                }
            }
            if(args[0].equalsIgnoreCase("lobby")){
                if(p.hasPermission("cod.lobby")){
                    if(!main.PlayingPlayers.contains(p)) {
                        if(main.WaitingPlayers.contains(p)) {
                            p.teleport(getLobby.getLobby(p));
                        }
                    }else{
                        p.sendMessage(main.prefix + "§c§lYou cannot be ingame");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("leave")){
                if(p.hasPermission("cod.leave")) {
                    if (!main.getConfig().getBoolean("BungeeCord.Enabled")) {
                    if (main.WaitingPlayers.contains(p)) {
                            main.WaitingPlayers.remove(p);
                            SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                            SendCoolMessages.sendSubTitle(p, "§8§lLeft COD lobby", 10, 30, 10);
                            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                            p.sendMessage(main.prefix + "§8§lLeft COD lobby");
                            p.getInventory().clear();
                            if (armorSaved.containsKey(p)) {
                                p.getInventory().setArmorContents(armorSaved.get(p));
                            }
                            if (savedInventory.containsKey(p)) {
                                p.getInventory().setContents(savedInventory.get(p));
                            }

                            for (Player pp : main.WaitingPlayers) {
                                pp.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageFile.getData().getString("leave-message").replace("%player%", p.getName())));
                            }
                        } else {
                            p.sendMessage(main.prefix + "§4§lYou are not the in the Queue");
                        }
                    }else{
                        if(main.WaitingPlayers.contains(p)){
                            main.WaitingPlayers.remove(p);

                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            DataOutputStream out = new DataOutputStream(b);

                            try{
                                out.writeUTF("Connect");
                                out.writeUTF(main.getConfig().getString("BungeeCord.fallback-server"));
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            p.sendPluginMessage(ThisPlugin.getPlugin(), "BungeeCord", b.toByteArray());
                        }
                    }
                }
            }
            if(args[0].equalsIgnoreCase("reload")){
                if(p.hasPermission("cod.reload")){
                    main.saveDefaultConfig();
                    main.reloadConfig();

                    LobbyFile.saveData();
                    LobbyFile.reloadData();
                    ArenaFile.saveData();
                    ArenaFile.reloadData();
                    StatsFile.saveData();
                    StatsFile.reloadData();
                    GunFile.saveData();
                    GunFile.reloadData();
                    LanguageFile.saveData();
                    LanguageFile.reloadData();
                    KitFile.saveData();
                    KitFile.reloadData();
                    OwnedFile.saveData();
                    OwnedFile.reloadData();
                    SignFile.saveData();
                    SignFile.reloadData();

                    p.sendMessage(main.prefix + "§8§lConfigs reloaded!");
                }
            }
            if(args[0].equalsIgnoreCase("make")){
                if(p.hasPermission("cod.makegun")) {
                    if (args.length == 9) {
                        Guns guns = new Guns(main);
                        String gunID = args[2];
                        String gunName = args[3];
                        String ammoID = args[4];
                        String ammoAmount = args[5];
                        String ammoName = args[6];
                        String Level = args[7];
                        String Cost = args[8];
                        String type = args[1];

                        if (type.equalsIgnoreCase("PRIMARY")) {
                            if(Integer.parseInt(Level) <= 10) {
                                ItemStack gun = new ItemStack(Material.getMaterial(gunID.toUpperCase()));
                                ItemMeta meta = gun.getItemMeta();
                                meta.setDisplayName(gunName);
                                gun.setItemMeta(meta);

                                ItemStack ammo = new ItemStack(Material.getMaterial(ammoID.toUpperCase()));
                                ItemMeta ammoMeta = ammo.getItemMeta();
                                ammoMeta.setDisplayName(ammoName);
                                ammo.setItemMeta(ammoMeta);

                                guns.saveGun(gun, ammo, Integer.parseInt(Cost), Integer.parseInt(Level), Integer.valueOf(ammoAmount), type);

                                p.sendMessage(main.prefix + "§6Type: " + type + " Material: " + gunID + " Name: " + gunName);
                                p.sendMessage(main.prefix + "§2Name: " + ammoName + " Material: " + ammoID + " Amount: " + ammoAmount);
                                p.sendMessage(main.prefix + "§dLevel: " + Level + " Cost: " + Cost);
                            }else{
                                p.sendMessage(main.prefix + "§cMaximum level is 10");
                            }
                        }else if(type.equalsIgnoreCase("SECONDARY")){
                            if(Integer.parseInt(Level) <= 10) {
                                ItemStack gun = new ItemStack(Material.getMaterial(gunID.toUpperCase()));
                                ItemMeta meta = gun.getItemMeta();
                                meta.setDisplayName(gunName);
                                gun.setItemMeta(meta);

                                ItemStack ammo = new ItemStack(Material.getMaterial(ammoID.toUpperCase()));
                                ItemMeta ammoMeta = ammo.getItemMeta();
                                ammoMeta.setDisplayName(ammoName);
                                ammo.setItemMeta(ammoMeta);

                                guns.saveGun(gun, ammo, Integer.parseInt(Cost), Integer.parseInt(Level), Integer.valueOf(ammoAmount), type);

                                p.sendMessage(main.prefix + "§6Type: " + type + " Material: " + gunID + " Name: " + gunName);
                                p.sendMessage(main.prefix + "§2Name: " + ammoName + " Material: " + ammoID + " Amount: " + ammoAmount);
                                p.sendMessage(main.prefix + "§dLevel: " + Level + " Cost: " + Cost);
                            }else{
                                p.sendMessage(main.prefix + "§cMaximum level is 10");
                            }
                        }
                    }else{
                        p.sendMessage(main.prefix + "§7/cod make §6<primary/secondary> <Gun Material> <Gun Name> <Ammo Material> <Ammo Amount> <Ammo Name> <Level Unlock> <Gun Cost>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("deletegun")){
                if(p.hasPermission("cod.deletegun")) {
                    if (args.length == 2) {
                        int id = Integer.parseInt(args[1]);
                        Guns guns = new Guns(main);
                        if (GunFile.getData().contains("Guns." + id)) {
                            GunFile.getData().set("Guns." + id, null);
                            GunFile.saveData();
                            GunFile.reloadData();
                            p.sendMessage(main.prefix + "§aGun Deleted");
                            guns.loadGuns();
                        } else {
                            p.sendMessage(main.prefix + "§aGun with that ID doesn't exist");
                        }
                    }else{
                        p.sendMessage(main.prefix + "§7/cod deletegun <ID>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("npc")){
                if(p.hasPermission("cod.npc")) {
                    if (args.length == 2) {
                        if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null) {
                            if (args[1].equalsIgnoreCase("gunrange")) {
                                TryGuns tryGuns = new TryGuns(main);
                                tryGuns.createNPC(p);
                                p.sendMessage(main.prefix + "§bNpc spawned on your location");
                            } else if (args[1].equalsIgnoreCase("join")) {
                                JoinNPC joinNPC = new JoinNPC(main);
                                joinNPC.createJoin(p);
                                p.sendMessage(main.prefix + "§bNpc spawned on your location");
                            } else if (args[1].equalsIgnoreCase("leave")) {
                                LeaveNPC leaveNPC = new LeaveNPC(main);
                                leaveNPC.createLeaveNPC(p);
                                p.sendMessage(main.prefix + "§bNpc spawned on your location");
                            }
                        } else {
                            p.sendMessage(main.prefix + "§cCitizens 2 needs to be installed!");
                        }
                    }else{
                        p.sendMessage("§9Available NPC's are join, leave, gunrange");
                        p.sendMessage(main.prefix + "§5/cod npc <type>");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("buy")){
                if(p.hasPermission("cod.buyguns")){
                    if(!main.PlayingPlayers.contains(p)) {
                        if(main.WaitingPlayers.contains(p)) {
                            BuyGuns buy = new BuyGuns(main);
                            buy.loadMenu(p);
                        }
                    }else{
                        p.sendMessage(main.prefix + "§c§lYou cannot be ingame");
                    }
                }
            }
            if(args[0].equalsIgnoreCase("forcestart")){
                if(p.hasPermission("cod.force")){
                    if(BaseArena.states == BaseArena.ArenaStates.Countdown){
                        if(main.min_players > main.WaitingPlayers.size()){
                            MainRunnable runnable = new MainRunnable(main);
                            CountDown.timeuntilstart = 0;
                            p.sendMessage(main.prefix + NewChat.getNewColor("&6Force starting COD..."));
                        }
                    }
                }
            }
        }
        return true;
    }
}
