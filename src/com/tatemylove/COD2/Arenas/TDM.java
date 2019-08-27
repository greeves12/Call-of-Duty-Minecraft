package com.tatemylove.COD2.Arenas;

import com.tatemylove.COD2.Events.CODEndEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.Tasks.CountDown;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class TDM implements Listener {

   private ArrayList<Player> BlueTeam = new ArrayList<>();
   private ArrayList<Player> RedTeam = new ArrayList<>();
   private ArrayList<Player> PlayingPlayers = new ArrayList<>();
   private int bluescore = 0;
   private int redscore = 0;


    public  void assignTeams(String name){

               //PlayingPlayers.addAll(Main.WaitingPlayers);

                if(Main.WaitingPlayers.size() < ThisPlugin.getPlugin().getConfig().getInt("max-players")) {
                    for (int x = Main.WaitingPlayers.size(); x < ThisPlugin.getPlugin().getConfig().getInt("max-players"); x--) {
                        PlayingPlayers.add(Main.WaitingPlayers.get(x));
                        Main.WaitingPlayers.remove(x);
                    }
                }


                for(int x = 0; x < PlayingPlayers.size(); x++){
                    Player p = PlayingPlayers.get(x);
                    if(RedTeam.size() < BlueTeam.size()){
                        RedTeam.add(p);
                    }else if(BlueTeam.size() < RedTeam.size()){
                        BlueTeam.add(p);
                    }else{
                        Random RandomTeam = new Random();
                        int TeamID = 0;
                        TeamID = RandomTeam.nextInt(2);
                        if (TeamID == 0) {
                            RedTeam.add(p);
                        } else {
                            BlueTeam.add(p);
                        }
                    }
                }
                startTDM(name);
                startCountdown(name);

    }

    public  void startTDM(String name){
        for(int ID =0; ID < PlayingPlayers.size(); ID++){
            final Player p = PlayingPlayers.get(ID);

            p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§bKnife", null));

            p.setGameMode(GameMode.SURVIVAL);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.sendMessage(Main.prefix + "§aGame starting. Arena: §e" + name);

            if(RedTeam.contains(p)){
                p.teleport(new GetArena().getRedSpawn(p, name));
                p.setCustomName("§c" + p.getName());
                p.setCustomNameVisible(true);
                p.setPlayerListName("§c" + p.getName());
                Color c = Color.fromBGR(0,0,255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            }else if(BlueTeam.contains(p)){
                p.teleport(new GetArena().getBlueSpawn(p, name));
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

    public  void endTDM(String name){
        Bukkit.getServer().getPluginManager().callEvent(new CODEndEvent(PlayingPlayers, name, ArenasFile.getData().getString("Arenas." + name + ".Type")));

        new CountDown().runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
        for(Player p : PlayingPlayers){
            if(redscore > bluescore && RedTeam.contains(p)){
                RegistryAPI.registerWin(p);
                LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-win"));
            }else if(redscore < bluescore && BlueTeam.contains(p)){
                RegistryAPI.registerWin(p);
                LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-win"));
            }
            p.teleport(GetLocations.getLobby());
            p.getInventory().clear();
            GameInventory.lobbyInv(p);
            p.sendMessage(Main.prefix + " " + getBetterTeam());
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setPlayerListName(p.getName());
            p.setCustomNameVisible(true);
            p.setPlayerListName(p.getName());
            p.removePotionEffect(PotionEffectType.SPEED);
        }

        Main.onGoingArenas.remove(name);

        Main.WaitingPlayers.addAll(PlayingPlayers);
        PlayingPlayers.clear();
        BlueTeam.clear();
        RedTeam.clear();
        redscore=0;
        bluescore=0;

    }

    public  String getBetterTeam() {
        if (redscore > bluescore) {
            String team = "§c§lRed: §4§l" + redscore + " " + "§9§lBlue: §1§l" +bluescore;
            return team;
        } else if (bluescore > redscore) {
            String team = "§9§lBlue: §1§l" + bluescore + " " + "§c§lRed: §4§l" + redscore;
            return team;
        } else {
            String team = "§e§lTie: §6§l" +redscore + " §e§l- §6§l" +bluescore;
            return team;
        }
    }

    private  ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
        ItemStack s = new ItemStack(m);
        ItemMeta meta = s.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        s.setItemMeta(meta);
        return s;
    }
    private  ItemStack getColorArmor(Material m, Color c) {
        ItemStack i = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta(meta);
        return i;
    }

    private void startCountdown(String name){

        new BukkitRunnable(){
            int time = ThisPlugin.getPlugin().getConfig().getInt("game-time");
            @Override
            public void run() {
                if(PlayingPlayers.size() < Main.minplayers){
                    endTDM(name);

                    cancel();
                }
                if(redscore == 20 || bluescore == 20){

                    endTDM(name);


                    cancel();
                }

                if(time == 0){
                    endTDM(name);


                    cancel();
                }
                time-=1;
            }
        }.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
    }
}
