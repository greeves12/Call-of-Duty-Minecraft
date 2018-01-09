package com.tatemylove.COD.Updater;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;


public class Updater {

    public void update(Player p){
        String version = "1.0.3";

        try {
            URL api = new URL("https://api.github.com/repos/greeves12/COD/releases/latest");
            URLConnection con = api.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);

            String tagName = null;

            try{
                JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
                tagName = json.get("tag_name").getAsString();

                int latestVersion = Integer.parseInt(tagName.substring(1, tagName.length()));

                if(latestVersion > Integer.valueOf(version)){
                    p.sendMessage("*** [COD] There is a new version available ***");
                    p.sendMessage("*** Download the new build from here https://github.com/greeves12/COD/releases ***");
                }

            }catch(JsonIOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
