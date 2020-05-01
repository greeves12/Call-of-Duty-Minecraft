package com.tatemylove.COD2.KillStreaks;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tatemylove.COD2.Arenas.BaseArena;
import com.tatemylove.COD2.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetPlayersOnOtherTeam {
    public ArrayList<Player> get(Player p, ArrayList<Player> RedTeam, ArrayList<Player> BlueTeam){
        if(Main.WaitingPlayers.contains(p)) return new ArrayList<Player>();

            if (RedTeam.contains(p)) {
                return BlueTeam;
            } else if (BlueTeam.contains(p)) {
                return RedTeam;
            }

        return new ArrayList<>();

    }
}
