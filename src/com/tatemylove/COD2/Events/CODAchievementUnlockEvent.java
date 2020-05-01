package com.tatemylove.COD2.Events;

import com.tatemylove.COD2.Arenas.BaseArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CODAchievementUnlockEvent extends Event {

    public CODAchievementUnlockEvent(Player player, Enum e ){

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
