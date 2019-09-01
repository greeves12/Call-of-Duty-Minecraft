package com.tatemylove.COD2.Guns;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Main;
import org.bukkit.Material;

public class Guns {

    public static void createGuns(String name, int level, double cost, String type, String material){
        GunsFile.getData().set("Guns." + name + ".Level", level);
        GunsFile.getData().set("Guns." + name + ".Cost", cost);
        GunsFile.getData().set("Guns." + name + ".Type", type);
        GunsFile.getData().set("Guns." + name + ".GunMaterial", material);

        GunsFile.saveData();
        GunsFile.reloadData();

        Main.guns.add(name);
    }
}
