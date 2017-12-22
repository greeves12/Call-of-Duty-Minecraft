package com.tatemylove.COD.Citizens;

import com.tatemylove.COD.Commands.MainCommand;
import com.tatemylove.COD.Files.KitFile;
import com.tatemylove.COD.Files.StatsFile;
import com.tatemylove.COD.Guns.Guns;
import com.tatemylove.COD.JSON.HoverMessages;
import com.tatemylove.COD.Lobby.GetLobby;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import com.tatemylove.COD.Utilities.SendCoolMessages;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {
    Main main;

    private static NPCListener npcListener = null;

    public NPCListener (Main m){
        main = m;
        npcListener = NPCListener.this;
    }
    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
        Guns guns = new Guns(main);
        Player p = e.getClicker();
        if(!main.PlayingPlayers.contains(e.getClicker())) {
            if (e.getNPC().getName().equals(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("GunRange.npc-name")))) {
                guns.createMainMenu(e.getClicker());
            }
        }else{
            e.getClicker().sendMessage(main.prefix + "§e§lYou cannot be in-game to test guns!");
        }
        if(!main.PlayingPlayers.contains(e.getClicker()) || (!main.WaitingPlayers.contains(e.getClicker()))){
            if(e.getNPC().getName().equalsIgnoreCase(main.getConfig().getString("JoinNPC.npc-name"))){
                GetLobby getLobby = new GetLobby(main);
                p.teleport(getLobby.getLobby(p));

                main.WaitingPlayers.add(p);

                int kills = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Kills");
                int wins = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Wins");
                int deaths = StatsFile.getData().getInt(p.getUniqueId().toString() + ".Deaths");

                LobbyBoard lobbyBoard = new LobbyBoard(main);
                lobbyBoard.killsH.put(p.getName(), kills);
                lobbyBoard.deathsH.put(p.getName(), deaths);
                lobbyBoard.winsH.put(p.getName(), wins);
                lobbyBoard.setLobbyBoard(p);

                if(!KitFile.getData().contains(p.getUniqueId().toString())){
                    HoverMessages hoverMessages = new HoverMessages();
                    p.sendMessage(main.prefix + "§6"+p.getName() +", §8It appears you don't have a kit!");
                    hoverMessages.hoverMessage(p, "/cod kit", "§6§l§nClick here §d§lto select a Kit", "§e§lSelect a Kit");
                }
                MainCommand cmd = new MainCommand(main);
                if(p.getInventory().getContents().length > 0) {
                    cmd.savedInventory.put(p, p.getInventory().getContents());
                    p.getInventory().clear();
                }

                if(p.getInventory().getArmorContents().length > 0){
                    cmd.armorSaved.put(p, p.getInventory().getArmorContents());
                }

                SendCoolMessages.sendTitle(p, "§a", 10, 30, 10);
                SendCoolMessages.sendSubTitle(p, "§e§lYou joined the Queue", 10, 30, 10);

            }
        }else{
            p.sendMessage(main.prefix + "§c§lYou are already in the queue or in-game");
        }
    }
}
