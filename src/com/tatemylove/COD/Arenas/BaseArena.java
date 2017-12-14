package com.tatemylove.COD.Arenas;

public class BaseArena {
    public static ArenaStates states;
    public static ArenaType type;

    public static enum ArenaStates {
        Started, Waiting, Countdown
    }
    public static enum ArenaType{
        TDM, KC, INFECT
    }
    public static ArenaStates getStates(){
        return states;
    }

    public static ArenaType getType(){
        return type;
    }
}
