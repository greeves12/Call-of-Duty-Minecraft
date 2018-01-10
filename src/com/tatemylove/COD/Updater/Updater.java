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

    public void update(Player p){
        String version = Main.version;
        String parsedVersion = version.replace(".", "");

        try {
            URL api = new URL("https://api.github.com/repos/greeves12/COD/releases/latest");
            URLConnection con = api.openConnection();
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);

            String tagName = null;

            try{
                JsonObject json = new JsonParser().parse(new InputStreamReader(con.getInputStream())).getAsJsonObject();
                tagName = json.get("tag_name").getAsString();

                String finalTagName = tagName.replace(".", "");
                int latestVersion = Integer.parseInt(finalTagName.substring(1, finalTagName.length()));

               if(latestVersion > Integer.parseInt(parsedVersion)) {

                   p.sendMessage("§8*** [COD] §bThere is a new version available §a" + tagName + "§8***");
                   p.sendMessage("§8*** §dDownload the new build from here §8***");
                   p.sendMessage("§8*** §6§6https://github.com/greeves12/COD/releases §8***");
                   p.sendMessage("§bOnly Admins can see this message!");
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
