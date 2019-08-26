package com.tatemylove.COD2.Guns;

import com.tatemylove.COD2.Files.GunsFile;
import com.tatemylove.COD2.Main;
import org.bukkit.Material;

public class Guns {

    public static void createGuns(String name, String gunMaterial, String ammoMaterial, int ammoAmount, String ammoName, int level, double cost, String type){
        GunsFile.getData().set("Guns." + name + ".GunMaterial", gunMaterial);
        GunsFile.getData().set("Guns." + name+".AmmoMaterial", ammoMaterial);
        GunsFile.getData().set("Guns." + name + ".AmmoAmount", ammoAmount);
        GunsFile.getData().set("Guns." + name + ".AmmoName", ammoName);
        GunsFile.getData().set("Guns." + name + ".Level", level);
        GunsFile.getData().set("Guns." + name + ".Cost", cost);
        GunsFile.getData().set("Guns." + name + ".Type", type);

        GunsFile.saveData();
        GunsFile.reloadData();

        Main.guns.add(name);
    }
}
