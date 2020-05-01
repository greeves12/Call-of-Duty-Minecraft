package com.tatemylove.COD2.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class CODStartEvent extends Event {
    ArrayList<Player> players;
    String Arena;
    String type;

    public CODStartEvent(ArrayList<Player> players, String Arena, String type){
        this.players = players;
        this.Arena = Arena;
        this.type = type;
    }

    public String getArena(){
        return Arena;

    }
    public String getType(){
        return type;

    }

    public ArrayList<Player> getPlayers(){
        return players;
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
