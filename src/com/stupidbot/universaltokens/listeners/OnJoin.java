package com.stupidbot.universaltokens.listeners;

import java.io.IOException;

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
	}	
}