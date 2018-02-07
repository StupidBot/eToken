package com.stupidbot.universaltokens.listeners;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.stupidbot.universaltokens.Main;
import com.stupidbot.universaltokens.utils.Tokens;
import com.stupidbot.universaltokens.utils.enchant.CustomEnchantment;

public class OnBlockBreak implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack item = player.getInventory().getItemInMainHand();
		Location blockCenter = block.getLocation().add(0.5, 0.5, 0.5);
		FileConfiguration config = Main.getInstance().getConfig();
		double tokenChance = config.getDouble("Tokens.Chance");
		double tokenChanceR = new Random().nextDouble() * 100;

		if (tokenChanceR <= tokenChance) {

			Tokens.give(player, 1, true);

			blockCenter.getWorld().playSound(blockCenter, Sound.ENTITY_GENERIC_DRINK, 0.5f, 0.5f);
			blockCenter.getWorld().spawnParticle(Particle.SPELL_WITCH, blockCenter.getX(), blockCenter.getY(),
					blockCenter.getZ(), 50, 0.5, 0.5, 0.5);
		}

		if (item != null)
			if (item.containsEnchantment(CustomEnchantment.EXPLOSIVE)) {
				double explosiveChanceR = new Random().nextDouble() * 100;

				if (explosiveChanceR <= 20)
					blockCenter.getWorld().createExplosion(blockCenter,
							item.getEnchantmentLevel(CustomEnchantment.EXPLOSIVE));
			}
	}
}