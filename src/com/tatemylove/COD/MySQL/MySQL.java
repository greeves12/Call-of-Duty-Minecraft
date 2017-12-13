package com.tatemylove.COD.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    public static Connection connection;

    public MySQL(String userName, String password, String ip, String database){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database + "?user=" + userName + "&password=" + password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
