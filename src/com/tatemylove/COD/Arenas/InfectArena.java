package com.tatemylove.COD.Arenas;

import com.tatemylove.COD.Files.ArenaFile;
import com.tatemylove.COD.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InfectArena {
    public static ArrayList<Player> infect = new ArrayList<>();
    public static ArrayList<Player> humans = new ArrayList<>();
    public static HashMap<Player, String> Team = new HashMap<>();
    Main main;

    public InfectArena(Main m){
        main = m;
    }

    public void assignTeams(String id){
        if(BaseArena.states == BaseArena.ArenaStates.Started){
            if(ArenaFile.getData().contains("Arenas." + id)){
                main.PlayingPlayers.addAll(main.WaitingPlayers);
                main.WaitingPlayers.clear();

                for(int assign = 0; assign < main.PlayingPlayers.size(); assign++){
                    Player p = main.PlayingPlayers.get(assign);

                    if(infect.size() < 1){
                        infect.add(p);
                    }else if(infect.size() >=1){
                        humans.add(p);
                    }else{
                        Random RandomTeam = new Random();
                        int teamID = 0;
                        teamID = RandomTeam.nextInt(2);

                        if(teamID == 0){
                            infect.add(p);
                        }else{
                            humans.add(p);
                        }
                    }
                    if(infect.contains(p)){
                        Team.put(p, "Infect");
                    }else if(humans.contains(p)){
                        Team.put(p, "Humans");
                    }
                }
            }
        }
    }
    public void startInfect(String id){

    }
}
