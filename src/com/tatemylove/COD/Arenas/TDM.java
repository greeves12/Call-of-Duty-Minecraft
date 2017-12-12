package com.tatemylove.COD.Arenas;

import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TDM extends Main{
    public ArrayList<Player> redTeam =new ArrayList<>();
    public ArrayList<Player> blueTeam = new ArrayList<>();
    public HashMap<Player, String> Team = new HashMap<>();


    public void assignTeams(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Waiting){
            if (ArenaFile.getData().contains("Arenas." + id + ".Name")) {
                PlayingPlayers.addAll(WaitingPlayers);
                WaitingPlayers.clear();
                for (int assign = 0; assign < PlayingPlayers.size(); assign++) {
                    Player p = PlayingPlayers.get(assign);

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

    public void startTDM(String id){
        if(ArenaFile.getData().contains("Arenas." + id + ".Name")){
            if(BaseArena.states == BaseArena.ArenaStates.Started){
                for(int ID=0;ID< PlayingPlayers.size(); ID++){
                    final Player p = PlayingPlayers.get(ID);
                    if(redTeam.contains(p)){
                        p.getInventory().clear();

                        p.teleport();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 40);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setFoodLevel(20);
                        p.setHealth(20);
                        p.setDisplayName("§c" + p.getName());
                        p.setCustomNameVisible(true);
                        p.setPlayerListName("§c" + p.getName());

                        Color c = Color.fromBGR(255, 0, 0);
                        p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                        p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                        p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                        p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                    }else if(blueTeam.contains(p)){
                        p.getInventory().clear();

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
}
