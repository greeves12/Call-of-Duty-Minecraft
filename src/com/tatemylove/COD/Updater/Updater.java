package com.tatemylove.COD.Updater;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Updater {

    public void update(Player p){
        String version = "v1.0.3";

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

                if(latestVersion > Integer.parseInt(version)){
                    p.sendMessage("*** [COD] There is a new version available v" + latestVersion + "***");
                    p.sendMessage("*** Download the new build from here https://github.com/greeves12/COD/releases ***");
                }

            }catch(JsonIOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void autoUpdate(){
        try {
            String version = "1.0.3";
            String tagname = null;
            URL api = new URL("https://api.github.com/repos/greeves12/COD/releases/latest");
            URLConnection con = api.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);

            JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
            tagname = json.get("tag_name").getAsString();

            int latestVersion = Integer.parseInt(tagname.substring(1, tagname.length()));

            URL download = new URL("https://github.com/greeves12/COD/releases/download/v" + latestVersion + "/COD.jar");

            if(latestVersion > Integer.parseInt(version)) {
                InputStream in = download.openStream();
                Path path = new File("plugins/COD").toPath();
                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[COD] " + ChatColor.RED + "Found a new update v"+latestVersion+" downloading now");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
