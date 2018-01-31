package com.stupidbot.universaltokens.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Tokens {
	public static void give(Player player, int amount, boolean updateTotal) {
		FileConfiguration file = FileStorage.getCachedFiles().get(player);
		boolean positive = amount > 0;

		file.set("Stats.Tokens", file.getInt("Stats.Tokens") + amount);

		if (updateTotal && positive)
			file.set("Stats.TotalTokens", file.getInt("Stats.TotalTokens") + amount);

		FileStorage.updateCachedPlayerFile(player, file);

		Text.sendJSON(
				"[\"\",{\"text\":\"§eTokens: " + (positive ? "§a+" : "§c") + Text.formatInt(amount)
						+ " §7(hover)\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/token shop\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§eTokens §aare used for enchanting.\n§d§lCLICK HERE §ato run §b/token shop§a!\"}]}}}]",
				player);
	}
}