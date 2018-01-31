package com.stupidbot.universaltokens.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stupidbot.universaltokens.Main;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class Text {
	public static void sendJSON(String message, Player player) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(message));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static String formatInt(int number) {
		return NumberFormat.getInstance().format(number);
	}

	public static ItemStack updateLore(ItemStack tool, Enchantment enchant, int lvl) {
		ItemMeta toolMeta = tool.getItemMeta();
		toolMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		List<String> toolLore = tool.getItemMeta().hasLore() ? tool.getItemMeta().getLore() : new ArrayList<String>();
		String name = enchant.getName();
		boolean hasLore = false;

		if (enchant == Enchantment.DIG_SPEED)
			name = "Efficiency";
		else if (enchant == Enchantment.DURABILITY)
			name = "Unbreaking";
		else if (enchant == Enchantment.LUCK)
			name = "Fortune";

		for (int i = 0; i < toolLore.size(); i++) {
			if (ChatColor.stripColor(toolLore.get(i)).contains(name)) {
				toolLore.set(i, "§7" + name + " " + toRoman(lvl) + " (" + formatInt(lvl) + ")");
				hasLore = true;
				break;
			}
		}

		if (!(hasLore))
			toolLore.add("§7" + name + " I (1)");

		toolMeta.setLore(toolLore);
		tool.setItemMeta(toolMeta);

		return tool;
	}

	public static String toRoman(int mInt) {
		if (Main.getInstance().getConfig().getBoolean("FormatNumbers")) {
			String[] rnChars = { "M", "CM", "D", "C", "XC", "L", "X", "IX", "V", "I" };
			int[] rnVals = { 1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };
			String retVal = "";

			for (int i = 0; i < rnVals.length; i++) {
				int numberInPlace = mInt / rnVals[i];

				if (numberInPlace != 0) {
					retVal = retVal + ((numberInPlace == 4) && (i > 0) ? rnChars[i] + rnChars[(i - 1)]
							: new String(new char[numberInPlace]).replace("\000", rnChars[i]));
					mInt %= rnVals[i];
				}
			}
			return retVal;
		} else
			return (mInt + "");
	}

	public static String center(String message, int CENTER_PX) {
		// CENTER_PX 131 for chat
		if (message == null || message.equals(""))
			return null;

		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray())
			if (c == '§')
				previousCode = true;
			else if (previousCode) {
				previousCode = false;
				isBold = c == 'l' || c == 'L';
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();

		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}

		return (sb.toString() + message);
	}
}