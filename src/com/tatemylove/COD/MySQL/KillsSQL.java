package com.tatemylove.COD.MySQL;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KillsSQL {
private KillsSQL killsSQL = null;
Main main;
public KillsSQL(Main m){
    killsSQL = KillsSQL.this;
    main = m;
}
    private boolean scoreExist(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM CODkills");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString()))return true;
            }
            rs.close();
            ps.close();
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void addToDB(Player p){
        try{
            if(!scoreExist(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into CODkills(uuid, kills)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addKills(Player p, int amount){
        try{
            if(scoreExist(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE CODkills SET kills= kills+'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void getKills(Player p){
        try{

            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM CODkills");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())){
                    if(rs.getObject("kills") != null) LobbyBoard.killsH.put(p.getName(),rs.getInt("kills"));

                }
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
