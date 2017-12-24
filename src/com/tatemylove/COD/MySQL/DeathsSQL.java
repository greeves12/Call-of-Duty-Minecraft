package com.tatemylove.COD.MySQL;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeathsSQL {
    private DeathsSQL deathsSQL = null;
    Main main;
    public DeathsSQL (Main m){
        deathsSQL = DeathsSQL.this;
        main = m;
    }
    public void addToDB(Player p){
        try{
            if(!scoreExists(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into CODdeaths(uuid, deaths)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private boolean scoreExists(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM CODdeaths");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
            }
            rs.close();
            ps.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void addDeaths(Player p, int amount){
        try{
            if(scoreExists(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE CODdeaths SET deaths= deaths+'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void getDeaths(Player p){
        try{

            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM CODdeaths");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())){
                    if(rs.getObject("deaths") != null) LobbyBoard.deathsH.put(p.getName(),rs.getInt("deaths"));

                }
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
