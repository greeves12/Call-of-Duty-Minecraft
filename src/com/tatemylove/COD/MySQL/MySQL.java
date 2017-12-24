package com.tatemylove.COD.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MySQL {
    public static Connection connection;

    public MySQL(String userName, String password, String ip, String database){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database + "?user=" + userName + "&password=" + password);
            createKillTable();
            createDeathTable();
            createWinTable();
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
