package com.tatemylove.COD2.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CODJoinEvent extends Event {
    Player p;
    public CODJoinEvent(Player p){
        this.p = p;
    }

    public Player getPlayer(){
        return p;
    }
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
