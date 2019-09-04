package com.tatemylove.COD2.Perks;

import com.tatemylove.COD2.Files.PlayerData;
import com.tatemylove.COD2.Listeners.PlayerJoin;
import me.zombie_striker.qg.ammo.Ammo;
import me.zombie_striker.qg.guns.Gun;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Scavenger {


    private static boolean hasScvaneger(Player p, HashMap<UUID, ArrayList<String>> perks){
        if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Class." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk1").equalsIgnoreCase("§7§nScavenger") || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Class." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk2").equalsIgnoreCase("§7§nScavenger") || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Class." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk3").equalsIgnoreCase("§7§nScavenger")){
            return true;
        }
        return false;
    }


    public static void giveAmmo(Player p, HashMap<UUID, ArrayList<String>>perks, Gun gun){
        if(PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk1").equalsIgnoreCase("§7§nScavenger") || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk2").equalsIgnoreCase("§7§nScavenger") || PlayerData.getData().getString("Players." + p.getUniqueId().toString() + ".Classes." + PlayerJoin.clazz.get(p.getUniqueId()) + ".Perk3").equalsIgnoreCase("§7§nScavenger")){


            Ammo ammo = gun.getAmmoType();

            ItemStack s = ammo.getItemStack();
            s.setAmount(5);
            p.getInventory().addItem(s);

        }
    }
}
