package com.stupidbot.universaltokens.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.stupidbot.universaltokens.utils.Tokens;

public class OnPlayerInteract implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getItemInHand();

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
			if (item != null)
				if (item.hasItemMeta())
					if (item.getItemMeta().hasDisplayName())
						if (item.getItemMeta().getDisplayName() == "§eToken") {
							Tokens.give(player, item.getAmount(), false);
							player.setItemInHand(new ItemStack(Material.AIR));
						}
	}
}