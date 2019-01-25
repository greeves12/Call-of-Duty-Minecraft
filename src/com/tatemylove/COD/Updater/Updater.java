package com.tatemylove.COD.Updater;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tatemylove.COD.Main;
import com.tatemylove.COD.ThisPlugin.ThisPlugin;
import com.tatemylove.COD.Utilities.NewChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Updater {

    public void fetchRelease(Player p){
        String version = Main.version;

        try {
            URL api = new URL("https://api.github.com/repos/greeves12/COD/releases/latest");
            URLConnection con = api.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);

            String date = null;

            try{
                JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
                date = json.get("published_at").getAsString();


                p.sendMessage(Main.adminPrefix + "§aServer is running version §b" + Main.version + "§a released §e" + date + " §aby §6greeves12");


            }catch(JsonIOException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void autoUpdate(){
        try {
            String version = Main.version;
            String parseVersion = version.replace(".", "");

            String tagname = null;
            URL api = new URL("https://api.github.com/repos/greeves12/COD/releases/latest");
            URLConnection con = api.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);

            JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
            tagname = json.get("tag_name").getAsString();

            String parsedTagName = tagname.replace(".", "");

            int latestVersion = Integer.valueOf(parsedTagName.substring(1, parsedTagName.length()));

            URL download = new URL("https://github.com/greeves12/COD/releases/download/" + tagname + "/COD.jar");

            if(latestVersion > Integer.parseInt(parseVersion)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"[COD] Found a new version " +ChatColor.RED+ tagname +ChatColor.LIGHT_PURPLE+ " downloading now!!");

               new BukkitRunnable(){

                   @Override
                   public void run() {
                       try {

                           InputStream in = download.openStream();
                           File temp = new File("plugins/update");
                           if (!temp.exists()) {
                               temp.mkdir();
                           }
                           Path path = new File("plugins/update" + File.separator + "COD.jar").toPath();
                           Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

                       }catch(IOException e){
                           e.printStackTrace();
                       }
                   }
               }.runTaskLaterAsynchronously(ThisPlugin.getPlugin(), 0);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
