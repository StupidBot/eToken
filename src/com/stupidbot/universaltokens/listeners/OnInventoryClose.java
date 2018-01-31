package com.stupidbot.universaltokens.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class OnInventoryClose implements Listener {
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		Inventory inv = e.getInventory();

		if (inv.getName() == "§5Token Shop")
			if (inv.getItem(22).getType() == Material.WOOD_PICKAXE
					|| inv.getItem(22).getType() == Material.STONE_PICKAXE
					|| inv.getItem(22).getType() == Material.IRON_PICKAXE
					|| inv.getItem(22).getType() == Material.DIAMOND_PICKAXE
					|| inv.getItem(22).getType() == Material.GOLD_PICKAXE)
				player.getInventory().addItem(inv.getItem(22));
	}
}