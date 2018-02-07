package com.stupidbot.universaltokens.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.stupidbot.universaltokens.utils.enchant.CustomEnchantment;

public class OnChangeHeldItem implements Listener {
	@EventHandler
	public void onChangeHeldItem(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		ItemStack item = player.getInventory().getItem(e.getNewSlot());

		if (item != null) {
			if (item.containsEnchantment(CustomEnchantment.NIGHT_VISION)
					&& !(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)))
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, true, false));

			if (item.containsEnchantment(CustomEnchantment.FLY) && !(player.getAllowFlight()))
				player.setAllowFlight(true);

			if (item.containsEnchantment(CustomEnchantment.JUMP) && !(player.hasPotionEffect(PotionEffectType.JUMP)))
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000,
						item.getEnchantmentLevel(CustomEnchantment.JUMP) - 1, true, false));

			if (item.containsEnchantment(CustomEnchantment.SPEED) && !(player.hasPotionEffect(PotionEffectType.SPEED)))
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000,
						item.getEnchantmentLevel(CustomEnchantment.SPEED) - 1, true, false));
		}

		if ((item == null || !(item.containsEnchantment(CustomEnchantment.NIGHT_VISION)))
				&& player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);

		if ((item == null || !(item.containsEnchantment(CustomEnchantment.FLY))) && player.getAllowFlight()
				&& player.getGameMode() == GameMode.SURVIVAL)
			player.setAllowFlight(false);

		if ((item == null || !(item.containsEnchantment(CustomEnchantment.JUMP)))
				&& player.hasPotionEffect(PotionEffectType.JUMP))
			player.removePotionEffect(PotionEffectType.JUMP);

		if ((item == null || !(item.containsEnchantment(CustomEnchantment.SPEED)))
				&& player.hasPotionEffect(PotionEffectType.SPEED))
			player.removePotionEffect(PotionEffectType.SPEED);
	}
}