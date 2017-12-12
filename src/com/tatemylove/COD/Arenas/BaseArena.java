package com.tatemylove.COD.Arenas;

public class BaseArena {
    public static ArenaStates states;

    public static enum ArenaStates {
        Started, Waiting, Countdown
    }
    public static ArenaStates getStates(){
        return states;
    }
}
