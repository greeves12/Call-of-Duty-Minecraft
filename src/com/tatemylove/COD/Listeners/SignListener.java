package com.tatemylove.COD.Listeners;

import com.tatemylove.COD.Files.SignFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.Inventories.Kits;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.MySQL.DeathsSQL;
import com.tatemylove.COD.MySQL.KillsSQL;
import com.tatemylove.COD.MySQL.WinsSQL;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {
    Main main;
    public SignListener(Main m){
        main = m;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        Player p = e.getPlayer();

        if(p.hasPermission("cod.makesign")){
            if(e.getLine(0).equalsIgnoreCase("[cod]")){
                if(e.getLine(1).equalsIgnoreCase("join")){
                    e.setLine(0, main.prefix);
                    e.setLine(1, "§2Join");
                    //e.setLine(2, "§3"+String.valueOf(main.WaitingPlayers.size()) + "/" + main.max_players);

                    String world = e.getBlock().getWorld().getName();
                    int x = e.getBlock().getX();
                    int y = e.getBlock().getY();
                    int z = e.getBlock().getZ();

                    int total = x+y+z;

                    SignFile.getData().set(total + ".World", world);
                    SignFile.getData().set(total + ".Type", "Join");
                    SignFile.saveData();
                    SignFile.reloadData();
                    p.sendMessage(main.prefix + "§bSign created!");
                }else if(e.getLine(1).equalsIgnoreCase("leave")){
                    e.setLine(0, main.prefix);
                    e.setLine(1, "§cLeave");

                    String world = e.getBlock().getWorld().getName();
                    int x = e.getBlock().getX();
                    int y = e.getBlock().getY();
                    int z = e.getBlock().getZ();

                    int total = x+y+z;

                    SignFile.getData().set(total + ".World", world);
                    SignFile.getData().set(total + ".Type", "Leave");
                    SignFile.saveData();
                    SignFile.reloadData();
                    p.sendMessage(main.prefix + "§bSign created!");
                }else if(e.getLine(1).equalsIgnoreCase("testguns")){
                    e.setLine(0, main.prefix);
                    e.setLine(1, "§eGun Range");

                    String world = e.getBlock().getWorld().getName();
                    int x = e.getBlock().getX();
                    int y = e.getBlock().getY();
                    int z = e.getBlock().getZ();

                    int total = x+y+z;

                    SignFile.getData().set(total + ".World", world);
                    SignFile.getData().set(total + ".Type", "Range");
                    SignFile.saveData();
                    SignFile.reloadData();
                    p.sendMessage(main.prefix + "§bSign created!");
                }else if(e.getLine(1).equalsIgnoreCase("kit")){
                    e.setLine(0, main.prefix);
                    e.setLine(1, "§6Kits");

                    String world = e.getBlock().getWorld().getName();
                    int x = e.getBlock().getX();
                    int y = e.getBlock().getY();
                    int z = e.getBlock().getZ();

                    int total = x+y+z;

                    SignFile.getData().set(total + ".World", world);
                    SignFile.getData().set(total + ".Type", "Kits");
                    SignFile.saveData();
                    SignFile.reloadData();
                    p.sendMessage(main.prefix + "§bSign created!");
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getClickedBlock().getState() instanceof Sign){
            Sign sign = (Sign) e.getClickedBlock().getState();

            int x = sign.getX();
            int y = sign.getY();
            int z = sign.getZ();

            int total = x+y+z;

            if(sign.getLine(0).equalsIgnoreCase(main.prefix)){
                if(SignFile.getData().getString(total + ".Type").equalsIgnoreCase("Join")){
                    GetLobby lobby = new GetLobby(main);
                    p.teleport(lobby.getLobby(p));

                    LobbyBoard lobbyBoard = new LobbyBoard(main);
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

                    Main.kills.put(p.getName(), 0);
                    Main.deaths.put(p.getName(), 0);
                    Main.killStreak.put(p.getName(), 0);

                    main.WaitingPlayers.add(p);

                    SendCoolMessages.sendTitle(p, "§a", 10, 30, 10);
                    SendCoolMessages.sendSubTitle(p, "§e§lYou joined the Queue", 10, 30, 10);
                }else if(SignFile.getData().getString(total + ".Type").equalsIgnoreCase("Leave")){
                    main.PlayingPlayers.remove(p);
                    GetLobby lobby = new GetLobby(main);
                    p.teleport(lobby.getLobby(p));
                    SendCoolMessages.sendTitle(p, "§b", 10, 30, 10);
                    SendCoolMessages.sendSubTitle(p, "§8§lLeft COD lobby", 10, 30, 10);
                }else if(SignFile.getData().getString(total + ".Type").equalsIgnoreCase("Range")){
                    Guns guns = new Guns(main);
                    guns.loadGuns();
                    p.openInventory(guns.tryGuns);
                }else if(SignFile.getData().getString(total + ".Type").equalsIgnoreCase("Kits")){
                    Kits kits = new Kits(main);
                    kits.loadInventory(p);
                }
            }
        }
    }
}