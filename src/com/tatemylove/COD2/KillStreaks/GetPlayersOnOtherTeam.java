package com.tatemylove.COD2.KillStreaks;

import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetPlayersOnOtherTeam {
    public ArrayList<Player> get(Player p){
        if(Main.WaitingPlayers.contains(p)) return new ArrayList<Player>();
        if(BaseArena.type == BaseArena.ArenaType.TDM) {
            if (Main.RedTeam.contains(p)) {
                return Main.BlueTeam;
            } else if (Main.BlueTeam.contains(p)) {
                return Main.RedTeam;
            } else {
                ArrayList<Player> players = new ArrayList<Player>();
                players.addAll(Main.PlayingPlayers);
                if (players.contains(p)) players.remove(p);
                return players;
            }
        }else if(BaseArena.type == BaseArena.ArenaType.KC){
            if(Main.BlueTeam.contains(p)){
                return Main.RedTeam;
            }else if(Main.RedTeam.contains(p)){
                return Main.BlueTeam;
            }else{
                ArrayList<Player> players = new ArrayList<>();
                players.addAll(Main.PlayingPlayers);
                if(players.contains(p)) players.remove(p);
                return players;
            }
        }
        return new ArrayList<>();

    }
}
