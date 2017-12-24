package com.tatemylove.COD.MySQL;

import com.tatemylove.COD.Main;
import com.tatemylove.COD.ScoreBoard.LobbyBoard;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WinsSQL {
    private WinsSQL winsSQL = null;
    Main main;
    public WinsSQL(Main m){
        winsSQL = WinsSQL.this;
        main = m;
    }

    public void addToDB(Player p){
        try{
            if(!scoreExists(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into CODwins(uuid, wins)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private boolean scoreExists(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM CODwins");
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
    public void addWins(Player p, int amount){
        try{
            if(scoreExists(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE CODwins SET wins= wins+'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void getWins(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM CODwins");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())){
                    if(rs.getObject("wins") != null) LobbyBoard.winsH.put(p.getName(),rs.getInt("wins"));

                }
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
