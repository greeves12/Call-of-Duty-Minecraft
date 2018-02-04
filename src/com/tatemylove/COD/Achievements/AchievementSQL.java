package com.tatemylove.COD.Achievements;

import com.tatemylove.COD.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AchievementSQL {

    private boolean scoreExist(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM CODachievement");
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
                String number = "false";
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into CODachievement(uuid )\nvalues('" + p.getUniqueId().toString() + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean hasAchievement(Player p, String achievement){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM CODachievement");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if((rs.getString("uuid").equals(p.getUniqueId().toString())) && (rs.getString(achievement).equals("true")))return true;
            }
            rs.close();
            ps.close();
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void setAchievement(Player p, String achievement, String message){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE CODkills SET '" + achievement +"'=true'" + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        p.sendMessage("§b§l[Achievement] §aYou unlocked achievement §c§l" + message.toUpperCase());
    }
}
