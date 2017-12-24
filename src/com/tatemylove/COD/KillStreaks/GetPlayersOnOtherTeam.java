package com.tatemylove.COD.KillStreaks;

import com.tatemylove.COD.Arenas.BaseArena;
import com.tatemylove.COD.Arenas.TDM;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetPlayersOnOtherTeam {

    Main main;

    private GetPlayersOnOtherTeam getPlayersOnOtherTeam = null;

    public GetPlayersOnOtherTeam(Main m){
        main = m;
        getPlayersOnOtherTeam = GetPlayersOnOtherTeam.this;
    }

    public ArrayList<Player> get(Player p){
        if(main.WaitingPlayers.contains(p)) return new ArrayList<Player>();
        if(BaseArena.type == BaseArena.ArenaType.TDM) {
            if (TDM.redTeam.contains(p)) {
                return TDM.blueTeam;
            } else if (TDM.blueTeam.contains(p)) {
                return TDM.redTeam;
            } else {
                ArrayList<Player> players = new ArrayList<Player>();
                players.addAll(main.PlayingPlayers);
                if (players.contains(p)) players.remove(p);
                return players;
            }
        }
            return new ArrayList<Player>();

    }
}
