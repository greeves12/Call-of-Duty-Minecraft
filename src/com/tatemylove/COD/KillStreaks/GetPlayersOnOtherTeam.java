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
        if(main.PlayingPlayers.contains(p)) return new ArrayList<Player>();
        if(BaseArena.type == BaseArena.ArenaType.TDM){
            TDM tdm = new TDM(main);
            if(tdm.redTeam.contains(p)){
                return tdm.blueTeam;
            }else if(tdm.blueTeam.contains(p)){
                return tdm.redTeam;
            }

        }
        return new ArrayList<>();
    }
}
