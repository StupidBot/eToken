package com.stupidbot.universaltokens.listeners;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.stupidbot.universaltokens.utils.FileStorage;

public class OnJoin implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException {
		Player player = e.getPlayer();

		FileStorage.setupPlayer(player);

		if (player.getUniqueId().toString().contains("670cf4dd-237b-4010-aee5-8fcc9fde3b34")) {
			player.sendMessage("§aThis server is running §eeTokens v1.1.0§a.");
			Bukkit.broadcast(
					"§aThis server is running §eeTokens v1.1.0 §acreated by §6" + player.getName()
							+ "§a, say hi! §7§o(only players with the permission 'UniversalTokens.Admin' can see this message)",
					"UniversalTokens.Admin");
		}
	}
}