package com.tatemylove.COD2.Arenas;

import com.tatemylove.COD2.Events.CODEndEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.Tasks.CountDown;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Random;

public class TDM {


    public static void assignTeams(String name){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(BaseArena.type == BaseArena.ArenaType.TDM){
                Main.PlayingPlayers.addAll(Main.WaitingPlayers);
                Main.WaitingPlayers.clear();
                Main.RedTeam.clear();
                Main.BlueTeam.clear();

                for(int x = 0; x < Main.PlayingPlayers.size(); x++){
                    Player p = Main.PlayingPlayers.get(x);
                    if(Main.RedTeam.size() < Main.BlueTeam.size()){
                        Main.RedTeam.add(p);
                    }else if(Main.BlueTeam.size() < Main.RedTeam.size()){
                        Main.BlueTeam.add(p);
                    }else{
                        Random RandomTeam = new Random();
                        int TeamID = 0;
                        TeamID = RandomTeam.nextInt(2);
                        if (TeamID == 0) {
                            Main.RedTeam.add(p);
                        } else {
                            Main.BlueTeam.add(p);
                        }
                    }
                }
                startTDM(name);
            }
        }
    }

    public static void startTDM(String name){
        for(int ID =0; ID < Main.PlayingPlayers.size(); ID++){
            final Player p = Main.PlayingPlayers.get(ID);

            p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§bKnife", null));

            p.setGameMode(GameMode.SURVIVAL);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.sendMessage(Main.prefix + "§aGame starting. Arena: §e" + name);

            if(Main.RedTeam.contains(p)){
                p.teleport(GetArena.getRedSpawn(p));
                p.setCustomName("§c" + p.getName());
                p.setCustomNameVisible(true);
                p.setPlayerListName("§c" + p.getName());
                Color c = Color.fromBGR(0,0,255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            }else if(Main.BlueTeam.contains(p)){
                p.teleport(GetArena.getBlueSpawn(p));
                p.setCustomName("§9" + p.getName());
                p.setCustomNameVisible(true);
                p.setPlayerListName("§9" + p.getName());
                Color c = Color.fromBGR(255,0,0);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            }
        }
    }

    public static void endTDM(){
        Bukkit.getServer().getPluginManager().callEvent(new CODEndEvent(Main.PlayingPlayers, GetArena.getCurrentArena(), ArenasFile.getData().getString("Arenas." + GetArena.getCurrentArena() + ".Type")));
        BaseArena.states = BaseArena.ArenaStates.Countdown;
        new CountDown().runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
        for(Player p : Main.PlayingPlayers){
            p.teleport(GetLocations.getLobby());
            p.getInventory().clear();
            p.sendMessage(Main.prefix + " " + getBetterTeam());
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setPlayerListName(p.getName());
            p.setCustomNameVisible(true);
            p.setPlayerListName(p.getName());
        }

        Main.WaitingPlayers.addAll(Main.PlayingPlayers);
        Main.PlayingPlayers.clear();
        Main.BlueTeam.clear();
        Main.RedTeam.clear();
        Main.redscore=0;
        Main.bluescore=0;

    }

    public static String getBetterTeam() {
        if (Main.redscore > Main.bluescore) {
            String team = "§c§lRed: §4§l" + Main.redscore + " " + "§9§lBlue: §1§l" + Main.bluescore;
            return team;
        } else if (Main.bluescore > Main.redscore) {
            String team = "§9§lBlue: §1§l" + Main.bluescore + " " + "§c§lRed: §4§l" + Main.redscore;
            return team;
        } else {
            String team = "§e§lTie: §6§l" +Main.redscore + " §e§l- §6§l" +Main.bluescore;
            return team;
        }
    }

    private static ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta meta = s.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        s.setItemMeta(meta);
        return s;
    }
    private static ItemStack getColorArmor(Material m, Color c) {
        ItemStack i = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta(meta);
        return i;
    }
}
