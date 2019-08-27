package com.tatemylove.COD2.Commands;

import com.tatemylove.COD2.Events.CODJoinEvent;
import com.tatemylove.COD2.Events.CODLeaveEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Files.LobbyFile;
import com.tatemylove.COD2.Guns.BuyGuns;
import com.tatemylove.COD2.Guns.Guns;

import com.tatemylove.COD2.Inventories.SelectKit;
import com.tatemylove.COD2.Listeners.PlayerJoin;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.Perks.PerkMenu;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MainCommand implements CommandExecutor {
    Main main;
    public MainCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(args.length == 0){
                p.sendMessage(Main.prefix + "§b/cod help");
                return true;
            }if(args[0].equalsIgnoreCase("create")){
                if(args.length == 3){
                    String name = args[1];
                    if((args[2].equalsIgnoreCase("tdm")) || (args[2].equalsIgnoreCase("kc")) || (args[2].equalsIgnoreCase("inf"))){
                        if(!Main.arenas.contains(name)){
                            CreateArenaCommand.createArena(p, name, args[2].toUpperCase());
                        }else{
                            p.sendMessage(Main.prefix + "§cArena already exists with that name");
                        }
                    }else{
                        p.sendMessage(Main.prefix + "§cThat is not a valid arena. §bKC, TDM, INF");
                    }
                }else{
                    p.sendMessage(Main.prefix + "§9Available GameModes are §6TDM §a, §6KC §e(Kill Confirmed) §a, §6INF §e(Infected)");
                    p.sendMessage(Main.prefix + "§7/cod create <name> <type>");
                }
            }
            if(!ArenasFile.getData().contains("Arenas.")){
                p.sendMessage(Main.prefix + "§cThere are no arenas! Ask an administrator to create one first, then /cod enable");
                return true;
            }

            //Admin commands
            if(p.hasPermission("cod.admin")){
                if(args[0].equalsIgnoreCase("setlobby")){
                    LobbyFile.getData().set("Lobby.World", p.getLocation().getWorld().getName());
                    LobbyFile.getData().set("Lobby.X", p.getLocation().getX());
                    LobbyFile.getData().set("Lobby.Y", p.getLocation().getY());
                    LobbyFile.getData().set("Lobby.Z", p.getLocation().getZ());

                    LobbyFile.saveData();
                    LobbyFile.reloadData();

                    p.sendMessage(Main.prefix + "§eLobby set!");
                }

                if(args[0].equalsIgnoreCase("setspawn")){
                    if(args.length == 3){
                        if(Main.arenas.contains(args[1])){
                            CreateArenaCommand.setSpawns(p, args, args[1]);
                        }else{
                            p.sendMessage(Main.prefix + "§cArena does not exist");
                        }
                    }else{
                        p.sendMessage(Main.prefix + "§7/cod setspawn <name> <blue/red>");
                    }
                }
                if(args[0].equalsIgnoreCase("delete")){
                    if(args.length == 2){
                        if(Main.arenas.contains(args[1])){
                            CreateArenaCommand.deleteArena(p, args[1]);
                        }else{
                            p.sendMessage(Main.prefix + "§cArena does not exist");
                        }
                    }else{
                        p.sendMessage(Main.prefix + "§7/cod delete <name>");
                    }
                }
                if(args[0].equalsIgnoreCase("enable")){
                    if(ArenasFile.getData().contains("Arenas.")){
                        main.enabled = true;
                        p.sendMessage(Main.prefix + "§aEnabled COD");
                    }else{
                        p.sendMessage(Main.prefix + "§cYou need to create an arena first");
                    }
                }
                if(args[0].equalsIgnoreCase("deletegun")){
                    if(args.length==2){
                        if(args[1] != null){
                            if(GunsFile.getData().contains("Guns." + args[1])){
                                GunsFile.getData().set("Guns." + args[1], null);
                                GunsFile.saveData();
                                p.sendMessage(Main.prefix + "§b" + args[1] + " §ddeleted");
                            }else{
                                p.sendMessage("§cGun doesn't exist");
                            }
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("make")){
                    if(args.length == 9){
                        String name = args[1];
                        String gunMaterial = args[2];
                        String ammoMaterial = args[3];
                        String ammoAmount = args[4];
                        String ammoName = args[5];
                        String level = args[6];
                        String cost = args[7];
                        String type = args[8];

                        if(!Main.guns.contains(name)){
                            if(type.equalsIgnoreCase("PRIMARY") || type.equalsIgnoreCase("SECONDARY")) {
                                Guns.createGuns(name, gunMaterial.toUpperCase(), ammoMaterial.toUpperCase(), Integer.parseInt(ammoAmount), ammoName, Integer.parseInt(level), Double.parseDouble(cost), type);
                                p.sendMessage(Main.prefix + "§6Type: " + type + " Material: " + gunMaterial + " Name: " + name);
                                p.sendMessage(Main.prefix + "§2Name: " + ammoName + " Material: " + ammoMaterial + " Amount: " + ammoAmount);
                                p.sendMessage(Main.prefix + "§dLevel: " + level + " Cost: " + cost);
                            }else{
                                p.sendMessage(Main.prefix + "§dMust be secondary or primary");
                            }
                        }else{
                            p.sendMessage(Main.prefix + "§cA gun with that name already exists");
                        }
                    }else{
                        p.sendMessage(Main.prefix + "§7/cod make §6<Gun Name> <Gun Material> <Ammo Material> <Ammo Amount> <Ammo Name> <Level Unlock> <Gun Cost> <primary/secondary>");
                    }
                }


            }
            //Player Commands
            if(p.hasPermission("cod.player")){
                if(args[0].equalsIgnoreCase("kit")){
                    new SelectKit().createKit(p);
                }
                if(args[0].equalsIgnoreCase("buy")){
                    if(!Main.PlayingPlayers.contains(p)){
                        BuyGuns buyGuns = new BuyGuns();
                        buyGuns.loadMenu(p);
                    }else{
                        p.sendMessage(Main.prefix + "§cGame in progress");
                    }
                }
                if(args[0].equalsIgnoreCase("perks")){
                    if(!Main.PlayingPlayers.contains(p)){
                        new PerkMenu().createMenu(p);
                    }else{
                        p.sendMessage(Main.prefix + "§cGame in progress");
                    }
                }
                if(args[0].equalsIgnoreCase("join")){
                    if( Main.PlayingPlayers.contains(p)){
                        p.sendMessage(Main.prefix + "§cYou can't join the lobby right now");
                        return true;

                    } if(Main.WaitingPlayers.contains(p)){
                        p.sendMessage(Main.prefix + "§cYou can't join the lobby right now");
                        return true;
                    }
                    p.teleport(GetLocations.getLobby());
                    Bukkit.getServer().getPluginManager().callEvent(new CODJoinEvent(p));
                    p.sendMessage(Main.prefix + "§eYou joined the lobby");
                    Main.WaitingPlayers.add(p);
                }
                if(args[0].equalsIgnoreCase("leave")){
                    if(!Main.WaitingPlayers.contains(p)){
                        p.sendMessage(Main.prefix + "§cYou are not in the lobby");
                        return true;
                    }
                    if(Main.PlayingPlayers.contains(p)){
                        p.sendMessage(Main.prefix + "§cYou are not in the lobby");
                        return true;
                    }
                    Main.WaitingPlayers.remove(p);
                    p.sendMessage(Main.prefix + "§aYou have left the lobby");
                    Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(p));
                }
                if(args[0].equalsIgnoreCase("try")){
                    ArrayList<String> list = Main.ownedGuns.get(p.getUniqueId().toString());

                }
            }
        }

        return true;
    }
}
