package com.tatemylove.COD.JSON;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HoverMessages {

    public void hoverMessage(Player p, String cmd, String text, String hoverText){
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§8§l[COD] \", \"extra\":[{\"text\":\"" + text+"\", \"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hoverText+ "\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + cmd + "\"}}]}");

        PacketPlayOutChat packet = new PacketPlayOutChat(component);

        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
