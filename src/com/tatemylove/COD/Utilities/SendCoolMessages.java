package com.tatemylove.COD.Utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.tatemylove.COD.Packets.WrapperPlayServerTitle;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class SendCoolMessages {

	/*FOR SPIGOT --- public static void sendTitle(Player p, String title, Integer FadeInTime, Integer StayOnScreenTime, Integer FadeOutTime) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"\"}").a(title);
			PacketTitle titlePacket = new PacketTitle(Action.TITLE, chatTitle);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlePacket);
			PacketTitle length = new PacketTitle(Action.TIMES, FadeInTime, StayOnScreenTime, FadeOutTime);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
		}
	}*/

	/*FOR SPIGOT --- public static void sendSubTitle(Player p, String subtitle, Integer FadeInTime, Integer StayOnScreenTime, Integer FadeOutTime) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"\"}").a(subtitle);
			PacketTitle titlePacket = new PacketTitle(Action.SUBTITLE, chatTitle);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlePacket);
			PacketTitle length = new PacketTitle(Action.TIMES, FadeInTime, StayOnScreenTime, FadeOutTime);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
		}
	}*/

	/*FOR SPIGOT --- public static void resetTitleAndSubtitle(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			PacketTitle titlePacket = new PacketTitle(Action.RESET);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlePacket);
		}
	}*/

	/*FOR SPIGOT --- public static void clearTitleAndSubtitle(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			PacketTitle titlePacket = new PacketTitle(Action.CLEAR);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlePacket);
		}
	}*/

    /*FOR SPIGOT --- public static void sendOverActionBar(Player player, String message) {
    	if (((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion() >= 47) {
    		CraftPlayer p = (CraftPlayer) player;
    		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
    		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
    		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    	}
    }*/

    /*FOR SPIGOT --- public static void TabHeaderAndFooter(String Header, String Footer, Player p) {
    	if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
    		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + Header + "\"}");
        	IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + Footer + "\"}");

        	PacketTabHeader header = new PacketTabHeader(tabTitle, tabFoot);

        	((CraftPlayer) p).getHandle().playerConnection.sendPacket(header);
    	}
    }*/

    public static void sendOverActionBar(Player player, String message) {
		/*CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);*/

        PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
        chat.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + message + "\"}"));
        chat.getBytes().write(0, (byte) 2);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, chat);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Unable to send packet " + chat, e);
        }
    }

    public static void sendTitle(Player p, String title, Integer FadeInTime, Integer StayOnScreenTime, Integer FadeOutTime) {
		/*((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\": \"\"}").a(title)));
		PacketPlayOutTitle length = new PacketPlayOutTitle(FadeInTime, StayOnScreenTime, FadeOutTime);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);*/

		/*ProtocolManager pm = ProtocolLibrary.getProtocolManager();

		PacketContainer times = pm.createPacket(PacketType.Play.Server.TITLE);
        times.getIntegers().write(0, 2);
        int[] timeNumbers = {FadeInTime, StayOnScreenTime, FadeOutTime};
        times.getIntegerArrays().write(0, timeNumbers);

        try {
			pm.sendServerPacket(p, times);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

        PacketContainer ptitle = pm.createPacket(PacketType.Play.Server.TITLE);
        ptitle.getIntegers().write(0, 0);
        ptitle.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + title + "\"}"));

        try {
			pm.sendServerPacket(p, ptitle);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}*/

		/*Title t = new Title(title, "", FadeInTime, StayOnScreenTime, FadeOutTime);
		t.send(p);*/

        WrapperPlayServerTitle time = new WrapperPlayServerTitle();
        time.setAction(EnumWrappers.TitleAction.TIMES);
        time.setFadeIn(FadeInTime);
        time.setStay(StayOnScreenTime);
        time.setFadeOut(FadeOutTime);
        time.sendPacket(p);

        WrapperPlayServerTitle t = new WrapperPlayServerTitle();
        t.setTitle(WrappedChatComponent.fromText(title));
        t.sendPacket(p);
    }

    public static void sendSubTitle(Player p, String subtitle, Integer FadeInTime, Integer StayOnScreenTime, Integer FadeOutTime) {
			/*((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\": \"\"}").a(subtitle)));
			PacketPlayOutTitle length = new PacketPlayOutTitle(FadeInTime, StayOnScreenTime, FadeOutTime);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);*/

		/*Title t = new Title("", subtitle, FadeInTime, StayOnScreenTime, FadeOutTime);
		try {
			t.sendSub(p);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

        WrapperPlayServerTitle time = new WrapperPlayServerTitle();

        time.setAction(EnumWrappers.TitleAction.TIMES);
        time.setFadeIn(FadeInTime);
        time.setStay(StayOnScreenTime);
        time.setFadeOut(FadeOutTime);
        time.sendPacket(p);

        WrapperPlayServerTitle t = new WrapperPlayServerTitle();
        t.setAction(EnumWrappers.TitleAction.SUBTITLE);
        t.setTitle(WrappedChatComponent.fromText(subtitle));
        t.sendPacket(p);
    }

    public static void resetTitleAndSubtitle(Player p) {
        //((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.RESET, null));

		/*Title t = new Title("", "", 0, 0, 0);
		t.resetTitle(p);*/

        WrapperPlayServerTitle t = new WrapperPlayServerTitle();
        t.setAction(EnumWrappers.TitleAction.RESET);
        t.sendPacket(p);
    }

    public static void clearTitleAndSubtitle(Player p) {
        //((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.CLEAR, null));

		/*Title t = new Title("", "", 0, 0, 0);
		t.clearTitle(p);*/

        WrapperPlayServerTitle t = new WrapperPlayServerTitle();
        t.setAction(EnumWrappers.TitleAction.CLEAR);
        t.sendPacket(p);
    }

    public static void TabHeaderAndFooter(String Header, String Footer, Player p) {

    	/*//if (Header == null) Header = "";
        if (Header != null) Header = ChatColor.translateAlternateColorCodes('&', Header);

        //if (Footer == null) Footer = "";
        if (Footer != null) Footer = ChatColor.translateAlternateColorCodes('&', Footer);

    	IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + Header + "\"}");
        IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + Footer + "\"}");

        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.sendPacket(headerPacket);
        }*/

        PacketContainer tab = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        tab.getChatComponents().write(0, WrappedChatComponent.fromText(Header));
        tab.getChatComponents().write(1, WrappedChatComponent.fromText(Footer));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, tab);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Unable to send packet " + tab, e);
        }
    }
}

