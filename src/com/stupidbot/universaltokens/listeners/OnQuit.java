package com.stupidbot.universaltokens.listeners;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import com.stupidbot.universaltokens.utils.FileStorage;

public class OnQuit implements Listener {
	@EventHandler
	public void onQuit(PlayerQuitEvent e) throws IOException {
		Player player = e.getPlayer();
		FileConfiguration file = FileStorage.getCachedFiles().get(player.getUniqueId());

		FileStorage.savePlayerFile(player, file);

		Inventory inv = player.getOpenInventory().getTopInventory();

		if (inv != null)
			if (inv.getName() == "§5Token Shop")
				if (inv.getItem(22).getType() == Material.WOOD_PICKAXE
						|| inv.getItem(22).getType() == Material.STONE_PICKAXE
						|| inv.getItem(22).getType() == Material.IRON_PICKAXE
						|| inv.getItem(22).getType() == Material.DIAMOND_PICKAXE
						|| inv.getItem(22).getType() == Material.GOLD_PICKAXE)
					player.getInventory().addItem(inv.getItem(22));
	}
}