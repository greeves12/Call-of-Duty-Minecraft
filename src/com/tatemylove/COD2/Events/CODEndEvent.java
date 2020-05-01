package com.tatemylove.COD2.Events;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class CODEndEvent extends Event {
    String Arena;
    String Type;
    ArrayList<Player> Players;

    public CODEndEvent(ArrayList<Player> Players, String Arena, String Type){
        this.Arena = Arena;
        this.Players = Players;
        this.Type = Type;
    }

    public String getArena(){
        return Arena;
    }
    public String getType(){
        return Type;
    }
    public ArrayList<Player> getPlayers(){
        return Players;
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
