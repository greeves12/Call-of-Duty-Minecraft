package com.tatemylove.COD2.Arenas;

import com.tatemylove.COD2.Achievement.AchievementAPI;
import com.tatemylove.COD2.Events.CODEndEvent;
import com.tatemylove.COD2.Events.CODKillEvent;
import com.tatemylove.COD2.Events.CODLeaveEvent;
import com.tatemylove.COD2.Files.ArenasFile;
import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Inventories.GameInventory;
import com.tatemylove.COD2.KillStreaks.AttackDogs;
import com.tatemylove.COD2.KillStreaks.Mortar;
import com.tatemylove.COD2.KillStreaks.UAV;
import com.tatemylove.COD2.Leveling.LevelRegistryAPI;
import com.tatemylove.COD2.Listeners.PlayerJoin;
import com.tatemylove.COD2.Locations.GetLocations;
import com.tatemylove.COD2.Main;
import com.tatemylove.COD2.MySQL.RegistryAPI;
import com.tatemylove.COD2.Perks.Scavenger;
import com.tatemylove.COD2.Tasks.CountDown;
import com.tatemylove.COD2.ThisPlugin;
import me.zombie_striker.qg.api.QualityArmory;
import me.zombie_striker.qg.guns.Gun;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class CTF implements Listener {


    private ArrayList<Player> BlueTeam = new ArrayList<>();
    private  ArrayList<Player> RedTeam = new ArrayList<>();
    private   ArrayList<Player> PlayingPlayers = new ArrayList<>();

    public HashMap<UUID, Integer> Kills = new HashMap<>();
    public  HashMap<UUID, Integer> Deaths = new HashMap<>();
    public  HashMap<UUID, Integer> Killstreak = new HashMap<>();

    private  int bluescore = 0;
    private  int redscore = 0;

    private BossBar bossBar = Bukkit.getServer().createBossBar("§cRED: "+redscore + "§7<PENDING> §9BLUE: "+bluescore, BarColor.PURPLE, BarStyle.SEGMENTED_6);



    private HashMap<UUID, ArrayList<String>> loadedPerks = new HashMap<>();


    private  String arena = "";



    public  void assignTeams(String name){

        Bukkit.getServer().getPluginManager().registerEvents(this, ThisPlugin.getPlugin());
        arena = name;



    //    if(Main.WaitingPlayers.size() >ThisPlugin.getPlugin().getConfig().getInt("max-players")) {
        //    for (int x = 0; x < ThisPlugin.getPlugin().getConfig().getInt("max-players"); x++) {
                PlayingPlayers.add(Main.WaitingPlayers.get(0));
                Main.AllPlayingPlayers.add(Main.WaitingPlayers.get(0));
                Main.WaitingPlayers.remove(0);
         //   }
      //  }else{
            PlayingPlayers.addAll(Main.WaitingPlayers);
            Main.AllPlayingPlayers.addAll(Main.WaitingPlayers);
            Main.WaitingPlayers.clear();

       // }


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

        ItemStack blueTag = new ItemStack(Material.BLUE_WOOL, 1);
        ItemStack redTag = new ItemStack(Material.RED_WOOL, 1);

        Item redWool = GetLocations.getRedFlag(name).getWorld().dropItem(GetLocations.getRedFlag(name), redTag);
        Item blueWool = GetLocations.getBlueFlag(name).getWorld().dropItem(GetLocations.getBlueFlag(name), blueTag);

        redWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), redTag));
        blueWool.setMetadata("codBlueFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));

        for(int ID =0; ID < PlayingPlayers.size(); ID++){
            final Player p = PlayingPlayers.get(ID);
            p.getInventory().clear();


            Kills.put(p.getUniqueId(), 0);
            Deaths.put(p.getUniqueId(), 0);
            Killstreak.put(p.getUniqueId(), 0);

            p.setGameMode(GameMode.SURVIVAL);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.sendMessage(Main.prefix + "§aGame starting. Arena: §e" + name);
            setBoard(p);


            if(RedTeam.contains(p)){
                p.teleport( GetArena.getRedSpawn(p, name));
                p.setCustomName("§c" + p.getName());
                p.setCustomNameVisible(true);
                p.setPlayerListName("§c" + p.getName());
                Color c = Color.fromBGR(0,0,255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                getNewLoadout(p);

            }else if(BlueTeam.contains(p)){
                p.teleport( GetArena.getBlueSpawn(p, name));
                p.setCustomName("§9" + p.getName());
                p.setCustomNameVisible(true);
                p.setPlayerListName("§9" + p.getName());
                Color c = Color.fromBGR(255,0,0);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));

                getNewLoadout(p);

            }
        }
    }

    public void endTDM(String name){
        Bukkit.getServer().getPluginManager().callEvent(new CODEndEvent(PlayingPlayers, name, ArenasFile.getData().getString("Arenas." + name + ".Type")));

        for(Player p : PlayingPlayers){
            if(redscore > bluescore ){
                if(RedTeam.contains(p)) {
                    RegistryAPI.registerWin(p);
                    LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-win"));
                    AchievementAPI.grantAchievement(p, "Victory");
                }else if(BlueTeam.contains(p)){
                    LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-loss"));
                }
            }else if(redscore < bluescore ){
                if(BlueTeam.contains(p)) {
                    RegistryAPI.registerWin(p);
                    LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-win"));
                    AchievementAPI.grantAchievement(p, "Victory");
                }else if(RedTeam.contains(p)){
                    LevelRegistryAPI.addExp(p, ThisPlugin.getPlugin().getConfig().getInt("exp-loss"));
                }
            }
            p.teleport(GetLocations.getLobby());
            p.getInventory().clear();
            GameInventory.lobbyInv(p);
            p.sendMessage(Main.prefix + " " + getBetterTeam());

            if(Deaths.get(p.getUniqueId()) != 0){
                double kd = (double) Kills.get(p.getUniqueId()) / Deaths.get(p.getUniqueId());
                p.sendMessage(Main.prefix + "§eYour KD is §a" +kd);
            }else{
                p.sendMessage(Main.prefix + "§eYour KD is §a" + Kills.get(p.getUniqueId()));
            }

            p.setHealth(20);
            p.setFoodLevel(20);
            p.setPlayerListName(p.getName());
            p.setCustomNameVisible(true);
            p.setPlayerListName(p.getName());
            p.removePotionEffect(PotionEffectType.SPEED);
            Main.AllPlayingPlayers.remove(p);
            bossBar.removePlayer(p);
            p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

            if(ThisPlugin.getPlugin().getConfig().getBoolean("BungeeCord.enabled")){
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bout);

                try{
                    out.writeUTF("Connect");
                    out.writeUTF(ThisPlugin.getPlugin().getConfig().getString("BungeeCord.fall-back"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        Main.arenas.add(name);
        Main.onGoingArenas.remove(name);
        if(Main.arenas.size() == 1) {
            new CountDown().runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
        }
        Main.WaitingPlayers.addAll(PlayingPlayers);
        PlayingPlayers.clear();
        BlueTeam.clear();
        RedTeam.clear();
        redscore=0;
        bluescore=0;

        for(Entity e : Bukkit.getWorld(ArenasFile.getData().getString("Arenas." + name + ".Spawns.Red.World")).getEntities()){
            if(e.hasMetadata("codRedFlag") || e.hasMetadata("codBlueFlag")){
                e.remove();
            }
        }


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

    private ItemStack getMaterial(Material m, String name, ArrayList<String> lore){
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


    private  void startCountdown(String name){

        for(Player p : PlayingPlayers){
            bossBar.addPlayer(p);
            bossBar.setVisible(true);

        }

        new BukkitRunnable(){
            int time = ThisPlugin.getPlugin().getConfig().getInt("game-time");

            @Override
            public void run() {


                bossBar.setTitle("§cRED: "+redscore + "§7 «§f"+formatThis(time)+"§7» §9BLUE: "+bluescore);

                if(PlayingPlayers.size() < ThisPlugin.getPlugin().getConfig().getInt("min-players")) {

                        endTDM(arena);


                }
                if(RedTeam.size() == 0 || BlueTeam.size() == 0){

                        endTDM(arena);


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

    private String formatThis(int time){
        long minutes = time /60;
        int secs = time %60;

        return (minutes+":"+secs);


    }

    @EventHandler
    public void filterChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(ThisPlugin.getPlugin().getConfig().getBoolean("cod-chat")) {
            if (PlayingPlayers.contains(p)) {
                for (Player pp : PlayingPlayers) {

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
    public void onDeath(EntityDeathEvent e){

        if(e.getEntity() instanceof Player ) {
            Player p = (Player) e.getEntity();
            Player pp = p.getKiller();

            if (pp != null) {
                if (PlayingPlayers.contains(p) && PlayingPlayers.contains(pp)) {
                    if (RedTeam.contains(p)) {
                        for (Player players : PlayingPlayers) {
                            players.sendMessage(Main.prefix + "§b" + pp.getName() + "§e killed §c" + p.getName());
                        }
                        Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, pp, "red", "blue"));
                        RegistryAPI.registerDeath(p);
                        RegistryAPI.registerKill(pp);
                        LevelRegistryAPI.addExp(pp, ThisPlugin.getPlugin().getConfig().getInt("exp-kill"));
                        Deaths.put(p.getUniqueId(), Deaths.get(p.getUniqueId()) +1);
                        Kills.put(pp.getUniqueId(), Kills.get(pp.getUniqueId()) + 1);
                        Killstreak.put(pp.getUniqueId(), Killstreak.get(pp.getUniqueId()) + 1);

                        if(RegistryAPI.getKills(pp) == 1){
                            AchievementAPI.grantAchievement(pp, "FirstBlood");
                        }else if(RegistryAPI.getKills(pp) == 10){
                            AchievementAPI.grantAchievement(pp, "10Kill");
                        }else if(RegistryAPI.getKills(pp) == 50){
                            AchievementAPI.grantAchievement(pp, "50Kills");
                        }else if(RegistryAPI.getKills(pp) == 200){
                            AchievementAPI.grantAchievement(pp, "200Kills");
                        }


                    } else if (BlueTeam.contains(p)) {
                        for (Player players : PlayingPlayers) {
                            players.sendMessage(Main.prefix + "§c" + pp.getName() + "§e killed §b" + p.getName());
                        }

                        RegistryAPI.registerDeath(p);
                        RegistryAPI.registerKill(pp);
                        LevelRegistryAPI.addExp(pp, ThisPlugin.getPlugin().getConfig().getInt("exp-kill"));

                        Deaths.put(p.getUniqueId(), Deaths.get(p.getUniqueId()) +1);
                        Kills.put(pp.getUniqueId(), Kills.get(pp.getUniqueId()) + 1);
                        Killstreak.put(pp.getUniqueId(), Killstreak.get(pp.getUniqueId()) + 1);

                        if(RegistryAPI.getKills(pp) == 1){
                            AchievementAPI.grantAchievement(pp, "FirstBlood");
                        }else if(RegistryAPI.getKills(pp) == 10){
                            AchievementAPI.grantAchievement(pp, "10Kill");
                        }else if(RegistryAPI.getKills(pp) == 50){
                            AchievementAPI.grantAchievement(pp, "50Kills");
                        }else if(RegistryAPI.getKills(pp) == 200){
                            AchievementAPI.grantAchievement(pp, "200Kills");
                        }

                        Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, pp, "blue", "red"));

                        if(p.getInventory().contains(Material.RED_WOOL)){
                            ItemStack blueTag = new ItemStack(Material.RED_WOOL, 1);
                            Item blueWool = p.getWorld().dropItem(p.getLocation(), blueTag);
                            blueWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));

                        }
                    }
                }
                if(!PlayerJoin.clazz.get(pp.getUniqueId()).equals("")) {
                    if (!PlayerData.getData().getString("Players." + pp.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(pp.getUniqueId()) + ".Primary").equalsIgnoreCase("")) {
                        // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                        Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + pp.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(pp.getUniqueId()) + ".Primary"));

                        Scavenger.giveAmmo(pp, loadedPerks, g);
                        //  p.getInventory().setItem(0, QualityArmory.getGunItemStack(g));
                    }
                    if (!PlayerData.getData().getString("Players." + pp.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(pp.getUniqueId()) + ".Secondary").equalsIgnoreCase("")) {
                        // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                        Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + pp.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(pp.getUniqueId()) + ".Secondary"));
                        Scavenger.giveAmmo(pp, loadedPerks, g);

                        // p.getInventory().setItem(1, QualityArmory.getGunItemStack(g));
                    }
                }
            } else {
                if (PlayingPlayers.contains(p)) {
                    if (RedTeam.contains(p)) {


                        RegistryAPI.registerDeath(p);
                        Deaths.put(p.getUniqueId(), Deaths.get(p.getUniqueId()) + 1);
                        Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "red", null));
                    } else if (BlueTeam.contains(p)) {
                        Deaths.put(p.getUniqueId(), Deaths.get(p.getUniqueId()) + 1);
                        Bukkit.getServer().getPluginManager().callEvent(new CODKillEvent(p, null, "blue", null));
                        RegistryAPI.registerDeath(p);


                    }
                }
            }
            new UAV().onKill(e, Killstreak, PlayingPlayers);
            new AttackDogs().onKill(e, Killstreak, PlayingPlayers);
            new Mortar().onEntityKill(e, PlayingPlayers, Killstreak);
        }
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){
        new UAV().onUse(e, RedTeam, BlueTeam, PlayingPlayers);
        new AttackDogs().onInteract(e, PlayingPlayers, RedTeam, BlueTeam);
        new Mortar().onInteract(e, PlayingPlayers, RedTeam, BlueTeam);

    }

    @EventHandler
    public void trackMovement(PlayerMoveEvent e){
        Player p = e.getPlayer();

        if(PlayingPlayers.contains(p)){
            if(BlueTeam.contains(p)){
                if(p.getInventory().contains(Material.RED_WOOL)){
                    if((p.getLocation().getBlockX() == GetArena.getBlueSpawn(p, arena).getBlockX()) && (p.getLocation().getBlockY() == GetArena.getBlueSpawn(p, arena).getBlockY()) && (p.getLocation().getBlockZ() == GetArena.getBlueSpawn(p, arena).getBlockZ())){
                        p.getInventory().remove(Material.RED_WOOL);
                        bluescore++;

                        ItemStack redTag = new ItemStack(Material.RED_WOOL, 1);

                        Item redWool = GetLocations.getRedFlag(arena).getWorld().dropItem(GetLocations.getRedFlag(arena), redTag);

                        redWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), redTag));

                        for(Player pp : PlayingPlayers){
                            pp.sendMessage(Main.prefix + "§9Blue team captured the red teams flag");
                        }

                    }
                }
            }else if(RedTeam.contains(p)){
                if(p.getInventory().contains(Material.BLUE_WOOL)){
                    if((p.getLocation().getBlockX() == GetArena.getRedSpawn(p, arena).getBlockX()) && (p.getLocation().getBlockY() == GetArena.getRedSpawn(p, arena).getBlockY()) && (p.getLocation().getBlockZ() == GetArena.getRedSpawn(p, arena).getBlockZ())){
                        p.getInventory().remove(Material.BLUE_WOOL);
                        redscore++;

                        ItemStack blueTag = new ItemStack(Material.BLUE_WOOL, 1);

                        Item blueWool = GetLocations.getBlueFlag(arena).getWorld().dropItem(GetLocations.getBlueFlag(arena), blueTag);

                        blueWool.setMetadata("codBlueFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));


                        for(Player pp : PlayingPlayers){
                            pp.sendMessage(Main.prefix + "§cRed team captured the blue teams flag");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) e.getEntity();
            if (PlayingPlayers.contains(p)) {


                if (e.getItem().hasMetadata("codRedFlag")) {
                    if (RedTeam.contains(p)) {


                        if(e.getItem().getLocation().getBlockX() != GetLocations.getRedFlag(arena).getBlockX() && (e.getItem().getLocation().getBlockZ() != GetLocations.getRedFlag(arena).getBlockZ())) {

                            e.getItem().remove();
                            ItemStack blueTag = new ItemStack(Material.RED_WOOL, 1);
                            Item blueWool = p.getWorld().dropItem(GetLocations.getRedFlag(arena), blueTag);
                            blueWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));
                            for (Player pp : PlayingPlayers) {
                                pp.sendMessage(Main.prefix + "§cRed flag has been reset");
                            }
                            e.setCancelled(true);
                        }else{
                            e.setCancelled(true);
                        }
                    } else if (BlueTeam.contains(p)) {
                        //e.getItem().remove();

                        for(Player pp : PlayingPlayers){
                            pp.sendMessage(Main.prefix + "§9Blue team has taken the flag");
                        }
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
                                FireworkMeta meta = f.getFireworkMeta();
                                FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.BLUE).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                                meta.addEffect(effect);
                                meta.setPower(2);
                                f.setFireworkMeta(meta);

                                if(p.isDead() || (Bukkit.getPlayer(p.getUniqueId()) == null) || !Main.AllPlayingPlayers.contains(p) || !p.getInventory().contains(Material.RED_WOOL)){
                                    cancel();
                                }

                            }
                        }.runTaskTimer(ThisPlugin.getPlugin(), 0, 60);

                    }
                } else if (e.getItem().hasMetadata("codBlueFlag")) {
                    if (BlueTeam.contains(p)) {
                        if(e.getItem().getLocation().getBlockX() != GetLocations.getBlueFlag(arena).getBlockX() && (e.getItem().getLocation().getBlockZ() != GetLocations.getBlueFlag(arena).getBlockZ())) {

                            for (Player pp : PlayingPlayers) {
                                pp.sendMessage(Main.prefix + "§9Blue flag has been reset");
                            }
                            e.getItem().remove();

                            ItemStack blueTag = new ItemStack(Material.BLUE_WOOL, 1);
                            Item blueWool = p.getWorld().dropItem(GetLocations.getBlueFlag(arena), blueTag);
                            blueWool.setMetadata("codBlueFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));
                            e.setCancelled(true);
                        }else{
                            e.setCancelled(true);
                        }
                    } else if (RedTeam.contains(p)) {
                        e.getItem().remove();
                        for(Player pp : PlayingPlayers){
                            pp.sendMessage(Main.prefix + "§cRed team has taken the flag");
                        }
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
                                FireworkMeta meta = f.getFireworkMeta();
                                FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                                meta.addEffect(effect);
                                meta.setPower(2);
                                f.setFireworkMeta(meta);

                                if(p.isDead() || (Bukkit.getPlayer(p.getUniqueId()) == null) || !Main.AllPlayingPlayers.contains(p) || !p.getInventory().contains(Material.BLUE_WOOL)){
                                    cancel();
                                }

                            }
                        }.runTaskTimer(ThisPlugin.getPlugin(), 0, 60);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onDEath(PlayerDeathEvent e){
        if(PlayingPlayers.contains(e.getEntity())){
            Player p = e.getEntity();
            e.setDeathMessage(null);
            e.getDrops().clear();
            Killstreak.put(p.getUniqueId(), 0);



            if(p.getInventory().contains(Material.BLUE_WOOL)){

                ItemStack blueTag = new ItemStack(Material.BLUE_WOOL, 1);
                Item blueWool = p.getWorld().dropItem(p.getLocation(), blueTag);
                blueWool.setMetadata("codBlueFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));



            }else if(p.getInventory().contains(Material.RED_WOOL)){

                ItemStack blueTag = new ItemStack(Material.RED_WOOL, 1);
                Item blueWool = p.getWorld().dropItem(p.getLocation(), blueTag);
                blueWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));

            }
Main.cooldowns.add(e.getEntity());
            new BukkitRunnable(){

                @Override
                public void run() {
                    Main.cooldowns.remove(e.getEntity());
                }
            }.runTaskLater(ThisPlugin.getPlugin(), 60);

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getDamager() instanceof Player){
                Player p = (Player) e.getEntity();
                Player pp = (Player) e.getDamager();

                if(PlayingPlayers.contains(p) && PlayingPlayers.contains(pp)){
                    if((RedTeam.contains(p) && RedTeam.contains(pp)) || (BlueTeam.contains(p) && BlueTeam.contains(pp))){
                        e.setCancelled(true);
                        return;
                    }
                    if(Main.cooldowns.contains(p)){
                        e.setCancelled(true);
                    }
                    if(pp.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
                        e.setDamage(100);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(PlayingPlayers.contains(e.getPlayer())){
            if(RedTeam.contains(e.getPlayer())){
                e.getPlayer().getInventory().clear();

                e.setRespawnLocation(GetArena.getRedSpawn(e.getPlayer(), arena));
                getNewLoadout(e.getPlayer());
            }else if(BlueTeam.contains(e.getPlayer())){
                e.getPlayer().getInventory().clear();
                e.setRespawnLocation(GetArena.getBlueSpawn(e.getPlayer(), arena));
                getNewLoadout(e.getPlayer());

            }
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        PlayingPlayers.remove(e.getPlayer());

        Main.AllPlayingPlayers.remove(p);

        p.removePotionEffect(PotionEffectType.SPEED);

        Bukkit.getServer().getPluginManager().callEvent(new CODLeaveEvent(e.getPlayer()));

        if(RedTeam.contains(p)){
            RedTeam.remove(p);
        }else if(BlueTeam.contains(p)){
            BlueTeam.remove(p);
        }


        if(p.getInventory().contains(Material.BLUE_WOOL)){
            ItemStack blueTag = new ItemStack(Material.BLUE_WOOL, 1);
            Item blueWool = p.getWorld().dropItem(p.getLocation(), blueTag);
            blueWool.setMetadata("codBlueFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));

            Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
            FireworkMeta meta = f.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.BLUE).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
            meta.addEffect(effect);
            meta.setPower(3);
            f.setFireworkMeta(meta);

            for(Player pp : PlayingPlayers){
                pp.sendMessage(Main.prefix + "§aPlayer: §e" + p.getName() + " §adropped the §cRed Flag");
            }

        }else if(p.getInventory().contains(Material.RED_WOOL)){
            ItemStack blueTag = new ItemStack(Material.RED_WOOL, 1);
            Item blueWool = p.getWorld().dropItem(p.getLocation(), blueTag);
            blueWool.setMetadata("codRedFlag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));

            Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
            FireworkMeta meta = f.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
            meta.addEffect(effect);
            meta.setPower(3);
            f.setFireworkMeta(meta);

            for(Player pp : PlayingPlayers){
                pp.sendMessage(Main.prefix + "§aPlayer: §e" + p.getName() + " §adropped the §9Blue Flag");
            }

        }
    }

    private  void getNewLoadout(Player p){
        ArrayList<String> ss = new ArrayList<>();
        ss.add(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk1"));
        ss.add(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk2"));
        ss.add(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk3"));

        loadedPerks.put(p.getUniqueId(), ss);

        if(!PlayerJoin.clazz.get(p.getUniqueId()).equals("")) {
            if (!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary").equalsIgnoreCase("")) {
                // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary"));


                p.getInventory().setItem(0, QualityArmory.getGunItemStack(g));
            }
            if (!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Secondary").equalsIgnoreCase("")) {
                // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Secondary"));


                p.getInventory().setItem(1, QualityArmory.getGunItemStack(g));
            }
            if (!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Splode1").equalsIgnoreCase("")) {
                // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Splode1"));


                p.getInventory().setItem(2, QualityArmory.getGunItemStack(g));
            }
            if (!PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Splode2").equalsIgnoreCase("")) {
                // p.getInventory().setItem(0, QualityArmory.getGunItemStack(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Primary")));

                Gun g = QualityArmory.getGunByName(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Splode2"));


                p.getInventory().setItem(3, QualityArmory.getGunItemStack(g));
            }
        }
        if(RedTeam.contains(p)){
            if(!loadedPerks.get(p.getUniqueId()).contains("§6§nFeatherWeight")) {
                Color c = Color.fromBGR(0, 0, 255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            }else{
                Color c = Color.fromBGR(0, 0, 255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor2(Material.LEATHER_BOOTS, c));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true));
            }
        }else if(BlueTeam.contains(p)){
            if(!loadedPerks.get(p.getUniqueId()).contains("§6§nFeatherWeight")) {
                Color c = Color.fromBGR(255, 0, 0);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));


            }else{
                Color c = Color.fromBGR(255, 0, 0);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor2(Material.LEATHER_BOOTS, c));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true));
            }
        }
        p.getInventory().setItem(8, getMaterial(Material.IRON_SWORD, "§bKnife", null));
    }

    private   ItemStack getColorArmor2(Material m, Color c) {
        ItemStack i = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        meta.setColor(c);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
        i.setItemMeta(meta);
        return i;
    }
    private HashMap<String, Scoreboard> gameboard = new HashMap<>();

    private void setBoard(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("Gameboard", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§lYour Scores");

      /*  Score kills = objective.getScore("");
        kills.setScore(12);*/

        Score blank2 = objective.getScore("  ");
        blank2.setScore(10);

       /* Score deaths = objective.getScore("");
        deaths.setScore(9);*/

        Score blank3 = objective.getScore("   ");
        blank3.setScore(7);
        Score blank4 = objective.getScore("    ");
        blank4.setScore(13);

       /* Score killstreak = objective.getScore("");
        killstreak.setScore(6);*/


        Team kill = board.registerNewTeam("kill");
        kill.addEntry(ChatColor.AQUA.toString());
        kill.setPrefix(ChatColor.GREEN.toString() + "§a");
        kill.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.AQUA.toString()).setScore(12);

        Team death = board.registerNewTeam("death");
        death.addEntry(ChatColor.RED.toString());
        death.setPrefix(ChatColor.GREEN.toString() + "§a");
        death.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.RED.toString()).setScore(9);

        Team killstreaks = board.registerNewTeam("killstreak");
        killstreaks.addEntry(ChatColor.DARK_GREEN.toString());
        killstreaks.setPrefix(ChatColor.GREEN.toString() + "§a");
        killstreaks.setSuffix(ChatColor.GREEN.toString() + "0");
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(6);

        gameboard.put(p.getName(), board);
        createBoard(p);

        new BukkitRunnable(){

            @Override
            public void run() {

                int kill = Kills.get(p.getUniqueId());
                int deathh = Deaths.get(p.getUniqueId());
                int killstreakz = Killstreak.get(p.getUniqueId());

                board.getTeam("kill").setSuffix("§aKills: §6" + kill);
                board.getTeam("death").setSuffix("§aDeaths: §6" + deathh);
                board.getTeam("killstreak").setSuffix("§aKill Streak: §6"  + killstreakz);
            }
        }.runTaskTimer(ThisPlugin.getPlugin(), 0, 20);
    }

    private void createBoard(Player p){
        p.setScoreboard(gameboard.get(p.getName()));
    }

}
