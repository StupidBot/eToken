package com.stupidbot.universaltokens.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.stupidbot.universaltokens.Main;

public class FileStorage {
	private static Map<Player, FileConfiguration> playerFiles = new HashMap<Player, FileConfiguration>();

	public static void setup() throws IOException {
		Main instance = Main.getInstance();
		FileConfiguration config = instance.getConfig();
		File playerDataDirectory = new File(instance.getDataFolder() + File.separator + "player_data");

		config.addDefault("Tokens.Chance", 1.0000000);
		config.addDefault("Enchants.Efficiency.PricePerLevel", 75);
		config.addDefault("Enchants.Efficiency.MaxLevel", 500);
		config.addDefault("Enchants.Unbreaking.PricePerLevel", 50);
		config.addDefault("Enchants.Unbreaking.MaxLevel", 500);
		config.addDefault("Enchants.Explosive.PricePerLevel", 800);
		config.addDefault("Enchants.Explosive.MaxLevel", 5);
		config.addDefault("Enchants.NeverBreak.PricePerLevel", 1000);
		config.addDefault("Enchants.NightVision.PricePerLevel", 150);
		config.addDefault("Enchants.Fly.PricePerLevel", 500);
		config.addDefault("Enchants.Jump.PricePerLevel", 100);
		config.addDefault("Enchants.Jump.MaxLevel", 3);
		config.addDefault("Enchants.Speed.PricePerLevel", 120);
		config.addDefault("Enchants.Speed.MaxLevel", 3);
		config.addDefault("Enchants.Fortune.PricePerLevel", 80);
		config.addDefault("Enchants.Fortune.MaxLevel", 200);
		config.addDefault("FormatNumbers", true);

		config.options().copyDefaults(true);
		instance.saveConfig();
		instance.reloadConfig();

		if (!(playerDataDirectory.exists()))
			playerDataDirectory.mkdirs();

		for (Player all : Bukkit.getOnlinePlayers())
			setupPlayer(all);
	}

	public static void setupPlayer(Player player) throws IOException {
		File playerFile = getPlayerFile(player);
		FileConfiguration playerFileLoaded = YamlConfiguration.loadConfiguration(playerFile);

		if (!(playerFile.exists()))
			playerFile.createNewFile();

		playerFileLoaded.set("Name", player.getName());
		if (!(playerFileLoaded.isSet("Stats.Tokens")))
			playerFileLoaded.set("Stats.Tokens", 0);
		if (!(playerFileLoaded.isSet("Stats.TotalTokens")))
			playerFileLoaded.set("Stats.TotalTokens", 0);

		playerFileLoaded.save(playerFile);
		cachePlayerFile(player);
	}

	public static void cachePlayerFile(Player player) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder()
				+ File.separator + "player_data" + File.separator + player.getUniqueId().toString() + ".yml"));

		playerFiles.put(player, file);
	}

	public static void updateCachedPlayerFile(Player player, FileConfiguration file) {
		playerFiles.put(player, file);
	}

	public static void savePlayerFile(Player player, FileConfiguration file) throws IOException {
		file.save(new File(Main.getInstance().getDataFolder() + File.separator + "player_data" + File.separator
				+ player.getUniqueId().toString() + ".yml"));
		playerFiles.remove(player);
	}

	private static File getPlayerFile(Player player) {
		return new File(Main.getInstance().getDataFolder() + File.separator + "player_data" + File.separator
				+ player.getUniqueId().toString() + ".yml");
	}

	public static Map<Player, FileConfiguration> getCachedFiles() {
		return playerFiles;
	}
}