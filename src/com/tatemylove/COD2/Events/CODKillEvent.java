package com.tatemylove.COD2.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CODKillEvent extends Event {
    Player killer;
    Player killed;
    String killedTeam;
    String killerTeam;


    public CODKillEvent(Player killed, Player killer, String killedTeam, String killerTeam){
        this.killed = killed;
        this.killer = killer;
        this.killedTeam = killedTeam;
        this.killerTeam=killerTeam;
    }

    public Player getKiller(){
        return killer;
    }
    public Player getKilled(){
        return killed;
    }

    public String getKilledTeam(){
        return killedTeam;
    }
    public String getKillerTeam(){
        return killerTeam;
    }

    private static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
