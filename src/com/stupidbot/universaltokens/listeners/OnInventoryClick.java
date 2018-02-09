package com.stupidbot.universaltokens.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.stupidbot.universaltokens.utils.FileStorage;
import com.stupidbot.universaltokens.utils.Inventories;
import com.stupidbot.universaltokens.utils.Text;
import com.stupidbot.universaltokens.utils.enchant.CustomEnchantment;

public class OnInventoryClick implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory inv = e.getInventory();

		if (clicked != null)
			if (e.getRawSlot() < e.getInventory().getSize()) {
				if (inv.getName() == "§5Token Shop") {
					if (clicked.getType() == Material.WOOD_PICKAXE || clicked.getType() == Material.STONE_PICKAXE
							|| clicked.getType() == Material.IRON_PICKAXE
							|| clicked.getType() == Material.DIAMOND_PICKAXE
							|| clicked.getType() == Material.GOLD_PICKAXE)
						Inventories.openTokenShop(player);
					else if (clicked.getItemMeta().getDisplayName().contains("Efficiency"))
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(Enchantment.DIG_SPEED, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player, Text.updateLore(pick, Enchantment.DIG_SPEED, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aEfficiency levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					else if (clicked.getItemMeta().getDisplayName().contains("Unbreaking")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(Enchantment.DURABILITY, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player, Text.updateLore(pick, Enchantment.DURABILITY, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aUnbreaking levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Never Break")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = 1;

								pick.addUnsafeEnchantment(CustomEnchantment.NEVER_BREAK, lvl);

								ItemMeta pickMeta = pick.getItemMeta();

								pickMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
								pickMeta.setUnbreakable(true);

								pick.setItemMeta(pickMeta);

								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player,
										Text.updateLore(pick, CustomEnchantment.NEVER_BREAK, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aNever Break levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Night Vision")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = 1;

								pick.addUnsafeEnchantment(CustomEnchantment.NIGHT_VISION, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player,
										Text.updateLore(pick, CustomEnchantment.NIGHT_VISION, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aNight Vision levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Fly")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = 1;

								pick.addUnsafeEnchantment(CustomEnchantment.FLY, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player, Text.updateLore(pick, CustomEnchantment.FLY, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aFly levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Jump")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(CustomEnchantment.JUMP, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player, Text.updateLore(pick, CustomEnchantment.JUMP, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aJump levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Speed")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(CustomEnchantment.SPEED, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player,
										Text.updateLore(pick, CustomEnchantment.SPEED, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aSpeed levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Fortune")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(Enchantment.LUCK, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player, Text.updateLore(pick, Enchantment.LUCK, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aFortune levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					} else if (clicked.getItemMeta().getDisplayName().contains("Explosive")) {
						if (!(clicked.getItemMeta().getLore().get(0).contains("ENCHANT MAXED"))) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0))
									.replaceAll("[^\\d]", ""));
							if (file.getInt("Stats.Tokens") >= cost) {
								ItemStack pick = player.getOpenInventory().getTopInventory().getItem(22);
								int lvl = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())
										.replaceAll("[^\\d]", ""));

								pick.addUnsafeEnchantment(CustomEnchantment.EXPLOSIVE, lvl);
								file.set("Stats.Tokens", file.getInt("Stats.Tokens") - cost);
								FileStorage.updateCachedPlayerFile(player, file);
								Inventories.updateTokenShop(player,
										Text.updateLore(pick, CustomEnchantment.EXPLOSIVE, lvl));

								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
								player.sendMessage("§aExplosive levelled up!");
							} else {
								player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
								player.sendMessage("§cYou can't afford this, mine to get tokens.");
							}
						} else {
							player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 0.5f, 1f);
							player.sendMessage("§cThis enchant is already maxed.");
						}
					}
					e.setCancelled(true);
				}
			} else if (inv.getName() == "§5Token Shop")
				if (clicked.getType() == Material.WOOD_PICKAXE || clicked.getType() == Material.STONE_PICKAXE
						|| clicked.getType() == Material.IRON_PICKAXE || clicked.getType() == Material.DIAMOND_PICKAXE
						|| clicked.getType() == Material.GOLD_PICKAXE) {
					if (inv.getItem(22).getType() == Material.WOOD_PICKAXE
							|| inv.getItem(22).getType() == Material.STONE_PICKAXE
							|| inv.getItem(22).getType() == Material.IRON_PICKAXE
							|| inv.getItem(22).getType() == Material.DIAMOND_PICKAXE
							|| inv.getItem(22).getType() == Material.GOLD_PICKAXE)
						player.getInventory().addItem(inv.getItem(22));

					Inventories.updateTokenShop(player, clicked);
					player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);

					e.setCancelled(true);

					player.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
				}
	}
}