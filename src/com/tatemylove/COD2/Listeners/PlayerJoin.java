package com.tatemylove.COD2.Listeners;

import com.tatemylove.COD2.Events.CODJoinEvent;
import com.tatemylove.COD2.Events.CODLeaveEvent;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Files.StatsFile;
import com.tatemylove.COD2.Guns.BuyGuns;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.Inventories.SelectKit;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.Managers.LoadoutManager;
import com.tatemylove.COD2.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

public class PlayerJoin implements Listener {
    private HashMap<UUID, ItemStack[]> inv = new HashMap<>();
    private HashMap<UUID, Location> loc = new HashMap<>();
    public static HashMap<UUID, String> clazz = new HashMap<>();
    private HashMap<UUID, Integer> exp = new HashMap<>();

    Main main;
    public PlayerJoin(Main main){
        this.main=main;
    }


    @EventHandler
    public void playerLeave(PlayerQuitEvent e){
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            Main.WaitingPlayers.remove(e.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(e.getPlayer()));
        }
    }

    @EventHandler
    public void noDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(Main.WaitingPlayers.contains(p)){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void noHunger(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Main.WaitingPlayers.contains(p)) {
                e.setCancelled(true);
            }else if(Main.AllPlayingPlayers.contains(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void codJoin(CODJoinEvent e){
        inv.put(e.getPlayer().getUniqueId(), e.getPlayer().getInventory().getContents());
        e.getPlayer().getInventory().clear();
        GameInventory.lobbyInv(e.getPlayer());
        loc.put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
        exp.put(e.getPlayer().getUniqueId(), e.getPlayer().getLevel());

        for(Player p : Main.WaitingPlayers){
            p.sendMessage(Main.prefix + "§aPlayer: §e" + e.getPlayer() + " §ajoined the lobby");
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void codLeave(CODLeaveEvent e){
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setContents(inv.get(e.getPlayer().getUniqueId()));
        e.getPlayer().teleport(loc.get(e.getPlayer().getUniqueId()));
        e.getPlayer().setLevel(exp.get(e.getPlayer().getUniqueId()));

        for(Player p : Main.AllPlayingPlayers){
            p.sendMessage(Main.prefix + "§aPlayer: §e" + e.getPlayer() + " §aleft COD");
        }
    }

    @EventHandler
    public void noBuild(BlockPlaceEvent e){
        if(Main.AllPlayingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void noBreak(BlockBreakEvent e){
        if(Main.AllPlayingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void noThrow(PlayerDropItemEvent e){
        if(Main.WaitingPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

  /*  @EventHandler
    public void itemClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR){
            if(e.getItem().equals(GameInventory.achievements)){

            }else if(e.getItem().equals(GameInventory.kits)){
                new SelectKit().createKit(e.getPlayer());
            }else if(e.getItem().equals(GameInventory.buygun)){
                new BuyGuns().loadMenu(e.getPlayer());
            }
        }
    }*/

  @EventHandler
  public void chatHandler(AsyncPlayerChatEvent e){
      Player p = e.getPlayer();

      if(ThisPlugin.getPlugin().getConfig().getBoolean("cod-chat")) {
          if (Main.WaitingPlayers.contains(p)) {
              for (Player pp : Main.WaitingPlayers) {

                  if (PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Prestige") == 0) {
                      pp.sendMessage("§8[§bLevel " + PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level") + "§8] §a" + p.getName() + ": §7" + e.getMessage());

                  } else {
                      pp.sendMessage("§8[§3Prestige " + PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Prestige") + "§8] [§bLevel " + PlayerData.getData().getInt("Players." + p.getUniqueId().toString() + ".Level") + "§8] §a" + p.getName() + ": §7" + e.getMessage());
                  }
              }
              e.setCancelled(true);
          }
      }
  }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!PlayerData.getData().contains("Players." + e.getPlayer().getUniqueId().toString())){
            ArrayList<String> list = new ArrayList<>();
            list.add("Class1");
            list.add("Class2");
            list.add("Class3");
            list.add("Class4");
            list.add("Class5");



            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Guns", new ArrayList<String>());
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Level", 1);
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".EXP", 0);
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Prestige", 0);
            PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Perks", new ArrayList<String>());

            for(String s : list) {

                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Perk1", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Perk2", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Perk3", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Primary", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes." +s+ ".Secondary", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Splode1", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString()+".Classes."+s + ".Splode2", "");
                PlayerData.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Classes." + s + ".Enabled", false);



            }


                PlayerData.saveData();
                PlayerData.reloadData();

                Main.ownedGuns.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());



                Main.ownedPerks.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
                Main.unlockedAchievements.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>());
                StatsFile.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Wins", 0);
                StatsFile.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Deaths", 0);
                StatsFile.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".Kills", 0);
                StatsFile.getData().set("Players." + e.getPlayer().getUniqueId().toString() + ".EXP", 0);

                StatsFile.saveData();

                if(isEnabled(e.getPlayer())){
                    clazz.put(e.getPlayer().getUniqueId(), getEnabled(e.getPlayer()));
                }else{
                    clazz.put(e.getPlayer().getUniqueId(), getEnabled(e.getPlayer()));
                }


        }else{
            Main.ownedGuns.put(e.getPlayer().getUniqueId().toString(), new ArrayList<>(PlayerData.getData().getStringList("Players." + e.getPlayer().getUniqueId().toString() + ".Guns")));

            if(isEnabled(e.getPlayer())){
                clazz.put(e.getPlayer().getUniqueId(), getEnabled(e.getPlayer()));
            }else{
                clazz.put(e.getPlayer().getUniqueId(), "");
            }
        }

        if(main.getConfig().getBoolean("BungeeCord.Enabled")){
            Main.WaitingPlayers.add(e.getPlayer());
            e.getPlayer().teleport(GetLocations.getLobby());
            Bukkit.getServer().getPluginManager().callEvent(new CODJoinEvent(e.getPlayer()));
            e.getPlayer().sendMessage("§eYou joined the lobby");
        }
    }

    public static boolean isEnabled(Player p){
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes." ).getKeys(false)){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return true;
            }
        }
        return false;
    }

    public static String getEnabled(Player p){
        for(String s : PlayerData.getData().getConfigurationSection("Players." + p.getUniqueId().toString() + ".Classes.").getKeys(false)){
            if(PlayerData.getData().getBoolean("Players." + p.getUniqueId().toString() + ".Classes." + s + ".Enabled")){
                return s;
            }
        }
        return null;
    }

}
