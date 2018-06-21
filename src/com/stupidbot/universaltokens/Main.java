package com.stupidbot.universaltokens;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.stupidbot.universaltokens.listeners.OnBlockBreak;
import com.stupidbot.universaltokens.listeners.OnChangeHeldItem;
import com.stupidbot.universaltokens.listeners.OnCommand;
import com.stupidbot.universaltokens.listeners.OnInventoryClick;
import com.stupidbot.universaltokens.listeners.OnInventoryClose;
import com.stupidbot.universaltokens.listeners.OnJoin;
import com.stupidbot.universaltokens.listeners.OnPlayerInteract;
import com.stupidbot.universaltokens.listeners.OnQuit;
import com.stupidbot.universaltokens.utils.FileStorage;
import com.stupidbot.universaltokens.utils.enchant.CustomEnchantment;

public class Main extends JavaPlugin implements Listener {
	private static Main instance;

	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this); // Lazyness
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
		getServer().getPluginManager().registerEvents(new OnQuit(), this);
		getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
		getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
		getServer().getPluginManager().registerEvents(new OnChangeHeldItem(), this);
		getServer().getPluginManager().registerEvents(new OnInventoryClose(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerInteract(), this);
		instance.getCommand("token").setExecutor(new OnCommand());
		try {
			FileStorage.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CustomEnchantment.register();
		System.out.println(getName() + " is now enabled!");
	}

	public void onDisable() {
		Map<UUID, FileConfiguration> playerFiles = FileStorage.getCachedFiles();

		for (Player all : Bukkit.getOnlinePlayers()) {
			FileConfiguration file = playerFiles.get(all.getUniqueId());
			try {
				FileStorage.savePlayerFile(all, file);

				Inventory inv = all.getOpenInventory().getTopInventory();

				if (inv != null)
					if (inv.getName() == "§5Token Shop")
						if (inv.getItem(22).getType() == Material.WOOD_PICKAXE
								|| inv.getItem(22).getType() == Material.STONE_PICKAXE
								|| inv.getItem(22).getType() == Material.IRON_PICKAXE
								|| inv.getItem(22).getType() == Material.DIAMOND_PICKAXE
								|| inv.getItem(22).getType() == Material.GOLD_PICKAXE) {
							all.getInventory().addItem(inv.getItem(22));
							all.closeInventory();
						}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		CustomEnchantment.unregister();
		instance = null;
		System.out.println(getName() + " is now disabled!");
	}

	public static Main getInstance() {
		return instance;
	}
}