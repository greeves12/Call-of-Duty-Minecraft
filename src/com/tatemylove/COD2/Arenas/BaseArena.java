package com.tatemylove.COD2.Arenas;

public class BaseArena {
    public static ArenaStates states;
    public static ArenaType type;

    public enum ArenaStates{
        Started, Waiting, Countdown
    }
    public enum ArenaType {
        TDM, KC, INFECT
    }

    public static ArenaStates getStates(){
        return states;
    }

    public static ArenaType getType(){
        return type;
    }
}
