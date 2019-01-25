package com.tatemylove.COD.MySQL;

import com.tatemylove.COD.Main;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQL {
    public static Connection connection;

    public MySQL(String userName, String password, String ip, String database, Main main){
        new BukkitRunnable() {
            @Override
            public void run() {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database + "?user=" + userName + "&password=" + password);
                    createKillTable();
                    createDeathTable();
                    createWinTable();
                    createAchievementTable();
                    //updateAchievementTable();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.runTaskLaterAsynchronously(main, 5);
    }
    private void createKillTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CODkills (uuid VARCHAR(36), kills int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createDeathTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CODdeaths (uuid VARCHAR (36), deaths int)");
        ps.executeUpdate();
        ps.close();
    }
    private void createWinTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CODwins (uuid VARCHAR (36), wins int)");
        ps.executeUpdate();
        ps.close();
    }

    private void createAchievementTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS  CODachievement (uuid VARCHAR (36), fb VARCHAR (36), mk VARCHAR (36), rc VARCHAR (36), lt VARCHAR (36), fw VARCHAR (36), ok VARCHAR (36) )");
        ps.executeUpdate();
        ps.close();
    }
    private void updateAchievementTable() throws Exception{
        PreparedStatement ps = connection.prepareStatement("ALTER TABLE CODachievements ADD ");
        ps.executeUpdate();
        ps.close();
    }
}
