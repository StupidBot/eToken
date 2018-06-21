package com.stupidbot.universaltokens.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.stupidbot.universaltokens.Main;
import com.stupidbot.universaltokens.utils.enchant.CustomEnchantment;

public class Inventories {
	public static void openTokenShop(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, "§5Token Shop");
		FileConfiguration file = FileStorage.getCachedFiles().get(player.getUniqueId());

		ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta borderMeta = border.getItemMeta();
		borderMeta.setDisplayName(" ");
		border.setItemMeta(borderMeta);

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, border);

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setDisplayName("§eCurrent Tokens: §6" + Text.formatInt(file.getInt("Stats.Tokens")));

		List<String> skullLore = new ArrayList<String>();
		skullLore.add("§eTotal Tokens Earned: §6" + Text.formatInt(file.getInt("Stats.TotalTokens")));

		skullMeta.setLore(skullLore);
		skullMeta.setOwningPlayer(player);
		skull.setItemMeta(skullMeta);

		inv.setItem(4, skull);

		ItemStack pickSlot = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
		ItemMeta pickSlotMeta = pickSlot.getItemMeta();
		pickSlotMeta.setDisplayName("§cClick a pickaxe to enchant.");
		pickSlot.setItemMeta(pickSlotMeta);

		inv.setItem(22, pickSlot);

		ItemStack enchantSlot = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta enchantSlotMeta = enchantSlot.getItemMeta();
		enchantSlotMeta.setDisplayName("§7???");
		enchantSlot.setItemMeta(enchantSlotMeta);

		inv.setItem(10, enchantSlot);
		inv.setItem(19, enchantSlot);
		inv.setItem(29, enchantSlot);
		inv.setItem(39, enchantSlot);
		inv.setItem(40, enchantSlot);
		inv.setItem(41, enchantSlot);
		inv.setItem(33, enchantSlot);
		inv.setItem(25, enchantSlot);
		inv.setItem(16, enchantSlot);

		player.openInventory(inv);
		player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.5f, 1f);
	}

	public static void updateTokenShop(Player player, ItemStack pick) {
		Inventory inv = player.getOpenInventory().getTopInventory();
		FileConfiguration file = FileStorage.getCachedFiles().get(player.getUniqueId());
		FileConfiguration config = Main.getInstance().getConfig();

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skullMeta.setDisplayName("§eCurrent Tokens: §6" + Text.formatInt(file.getInt("Stats.Tokens")));

		List<String> skullLore = new ArrayList<String>();
		skullLore.add("§eTotal Tokens Earned: §6" + Text.formatInt(file.getInt("Stats.TotalTokens")));

		skullMeta.setLore(skullLore);
		skullMeta.setOwningPlayer(player);
		skull.setItemMeta(skullMeta);

		inv.setItem(4, skull);

		inv.setItem(22, pick);

		ItemStack efficiency = new ItemStack(Material.FEATHER, 1);
		ItemMeta efficiencyMeta = efficiency.getItemMeta();
		int efficiencyLvl = pick.getEnchantments().containsKey(Enchantment.DIG_SPEED)
				? pick.getEnchantmentLevel(Enchantment.DIG_SPEED) + 1 : 1;
		boolean efficiencyMaxed = efficiencyLvl > config.getInt("Enchants.Efficiency.MaxLevel");
		if (efficiencyMaxed)
			efficiencyLvl = config.getInt("Enchants.Efficiency.MaxLevel");

		efficiencyMeta.setDisplayName(
				"§aEfficiency §c" + Text.toRoman(efficiencyLvl) + " §7(" + Text.formatInt(efficiencyLvl) + ")");

		List<String> efficiencyLore = new ArrayList<String>();
		efficiencyLore.add(efficiencyMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Efficiency.PricePerLevel") * efficiencyLvl));
		efficiencyLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Efficiency.MaxLevel")));

		efficiencyMeta.setLore(efficiencyLore);
		efficiency.setItemMeta(efficiencyMeta);
		inv.setItem(10, efficiency);

		ItemStack unbreaking = new ItemStack(Material.ANVIL, 1);
		ItemMeta unbreakingMeta = unbreaking.getItemMeta();
		int unbreakingLvl = pick.getEnchantments().containsKey(Enchantment.DURABILITY)
				? pick.getEnchantmentLevel(Enchantment.DURABILITY) + 1 : 1;
		boolean unbreakingMaxed = unbreakingLvl > config.getInt("Enchants.Unbreaking.MaxLevel");
		if (unbreakingMaxed)
			unbreakingLvl = config.getInt("Enchants.Unbreaking.MaxLevel");

		unbreakingMeta.setDisplayName(
				"§aUnbreaking §c" + Text.toRoman(unbreakingLvl) + " §7(" + Text.formatInt(unbreakingLvl) + ")");

		List<String> unbreakingLore = new ArrayList<String>();
		unbreakingLore.add(unbreakingMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Unbreaking.PricePerLevel") * unbreakingLvl));
		unbreakingLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Unbreaking.MaxLevel")));

		unbreakingMeta.setLore(unbreakingLore);
		unbreaking.setItemMeta(unbreakingMeta);

		inv.setItem(19, unbreaking);

		ItemStack explosive = new ItemStack(Material.TNT, 1);
		ItemMeta explosiveMeta = unbreaking.getItemMeta();
		int explosiveLvl = pick.getEnchantments().containsKey(CustomEnchantment.EXPLOSIVE)
				? pick.getEnchantmentLevel(CustomEnchantment.EXPLOSIVE) + 1 : 1;
		boolean explosiveMaxed = explosiveLvl > config.getInt("Enchants.Explosive.MaxLevel");
		if (explosiveMaxed)
			explosiveLvl = config.getInt("Enchants.Explosive.MaxLevel");

		explosiveMeta.setDisplayName(
				"§aExplosive §c" + Text.toRoman(explosiveLvl) + " §7(" + Text.formatInt(explosiveLvl) + ")");

		List<String> explosiveLore = new ArrayList<String>();
		explosiveLore.add(explosiveMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Explosive.PricePerLevel") * explosiveLvl));
		explosiveLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Explosive.MaxLevel")));

		explosiveMeta.setLore(explosiveLore);
		explosive.setItemMeta(explosiveMeta);

		inv.setItem(29, explosive);

		ItemStack neverBreak = new ItemStack(Material.SHIELD, 1);
		ItemMeta neverBreakMeta = neverBreak.getItemMeta();
		int neverBreakLvl = pick.getEnchantments().containsKey(CustomEnchantment.NEVER_BREAK)
				? pick.getEnchantmentLevel(CustomEnchantment.NEVER_BREAK) + 1 : 1;
		boolean neverBreakMaxed = neverBreakLvl > 1;

		if (neverBreakMaxed)
			neverBreakLvl = 1;

		neverBreakMeta.setDisplayName("§aNever Break §cI §7(1)");

		List<String> neverBreakLore = new ArrayList<String>();
		neverBreakLore.add(neverBreakMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.NeverBreak.PricePerLevel")));
		neverBreakLore.add("§fMax Level: §b1");

		neverBreakMeta.setLore(neverBreakLore);
		neverBreak.setItemMeta(neverBreakMeta);

		inv.setItem(39, neverBreak);

		ItemStack nightVision = new ItemStack(Material.EYE_OF_ENDER, 1);
		ItemMeta nightVisionMeta = nightVision.getItemMeta();
		int nightVisionLvl = pick.getEnchantments().containsKey(CustomEnchantment.NIGHT_VISION)
				? pick.getEnchantmentLevel(CustomEnchantment.NIGHT_VISION) + 1 : 1;
		boolean nightVisionMaxed = nightVisionLvl > 1;

		if (nightVisionMaxed)
			nightVisionLvl = 1;

		nightVisionMeta.setDisplayName("§aNight Vision §cI §7(1)");

		List<String> nightVisionLore = new ArrayList<String>();
		nightVisionLore.add(nightVisionMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.NightVision.PricePerLevel")));
		nightVisionLore.add("§fMax Level: §b1");

		nightVisionMeta.setLore(nightVisionLore);
		nightVision.setItemMeta(nightVisionMeta);

		inv.setItem(40, nightVision);

		ItemStack fly = new ItemStack(Material.ELYTRA, 1);
		ItemMeta flyMeta = fly.getItemMeta();
		int flyLvl = pick.getEnchantments().containsKey(CustomEnchantment.FLY)
				? pick.getEnchantmentLevel(CustomEnchantment.FLY) + 1 : 1;
		boolean flyMaxed = flyLvl > 1;

		if (flyMaxed)
			flyLvl = 1;

		flyMeta.setDisplayName("§aFly §cI §7(1)");

		List<String> flyLore = new ArrayList<String>();
		flyLore.add(flyMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Fly.PricePerLevel")));
		flyLore.add("§fMax Level: §b1");

		flyMeta.setLore(flyLore);
		fly.setItemMeta(flyMeta);

		inv.setItem(41, fly);

		ItemStack jump = new ItemStack(Material.RABBIT_FOOT, 1);
		ItemMeta jumpMeta = jump.getItemMeta();
		int jumpLvl = pick.getEnchantments().containsKey(CustomEnchantment.JUMP)
				? pick.getEnchantmentLevel(CustomEnchantment.JUMP) + 1 : 1;
		boolean jumpMaxed = jumpLvl > config.getInt("Enchants.Jump.MaxLevel");
		if (jumpMaxed)
			jumpLvl = config.getInt("Enchants.Jump.MaxLevel");

		jumpMeta.setDisplayName("§aJump §c" + Text.toRoman(jumpLvl) + " §7(" + Text.formatInt(jumpLvl) + ")");

		List<String> jumpLore = new ArrayList<String>();
		jumpLore.add(jumpMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Jump.PricePerLevel") * jumpLvl));
		jumpLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Jump.MaxLevel")));

		jumpMeta.setLore(jumpLore);
		jump.setItemMeta(jumpMeta);

		inv.setItem(33, jump);

		ItemStack speed = new ItemStack(Material.SUGAR, 1);
		ItemMeta speedMeta = speed.getItemMeta();
		int speedLvl = pick.getEnchantments().containsKey(CustomEnchantment.SPEED)
				? pick.getEnchantmentLevel(CustomEnchantment.SPEED) + 1 : 1;
		boolean speedMaxed = speedLvl > config.getInt("Enchants.Speed.MaxLevel");
		if (speedMaxed)
			speedLvl = config.getInt("Enchants.Speed.MaxLevel");

		speedMeta.setDisplayName("§aSpeed §c" + Text.toRoman(speedLvl) + " §7(" + Text.formatInt(speedLvl) + ")");

		List<String> speedLore = new ArrayList<String>();
		speedLore.add(speedMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Speed.PricePerLevel") * speedLvl));
		speedLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Speed.MaxLevel")));

		speedMeta.setLore(speedLore);
		speed.setItemMeta(speedMeta);

		inv.setItem(25, speed);

		ItemStack fortune = new ItemStack(Material.GOLD_NUGGET, 1);
		ItemMeta fortuneMeta = fortune.getItemMeta();
		int fortuneLvl = pick.getEnchantments().containsKey(Enchantment.LUCK)
				? pick.getEnchantmentLevel(Enchantment.LUCK) + 1 : 1;
		boolean fortuneMaxed = fortuneLvl > config.getInt("Enchants.Fortune.MaxLevel");
		if (fortuneMaxed)
			fortuneLvl = config.getInt("Enchants.Fortune.MaxLevel");

		fortuneMeta
				.setDisplayName("§aFortune §c" + Text.toRoman(fortuneLvl) + " §7(" + Text.formatInt(fortuneLvl) + ")");

		List<String> fortuneLore = new ArrayList<String>();
		fortuneLore.add(fortuneMaxed ? "§d§lENCHANT MAXED"
				: "§eCost: §6" + Text.formatInt(config.getInt("Enchants.Fortune.PricePerLevel") * fortuneLvl));
		fortuneLore.add("§fMax Level: §b" + Text.formatInt(config.getInt("Enchants.Fortune.MaxLevel")));

		fortuneMeta.setLore(fortuneLore);
		fortune.setItemMeta(fortuneMeta);

		inv.setItem(16, fortune);
	}
}