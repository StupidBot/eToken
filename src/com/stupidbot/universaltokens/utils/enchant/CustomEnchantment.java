package com.stupidbot.universaltokens.utils.enchant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

import com.stupidbot.universaltokens.utils.enchant.enchants.Explosive;
import com.stupidbot.universaltokens.utils.enchant.enchants.Fly;
import com.stupidbot.universaltokens.utils.enchant.enchants.Jump;
import com.stupidbot.universaltokens.utils.enchant.enchants.NeverBreak;
import com.stupidbot.universaltokens.utils.enchant.enchants.NightVision;
import com.stupidbot.universaltokens.utils.enchant.enchants.Speed;

public class CustomEnchantment {
	private static List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
	public static Explosive EXPLOSIVE = new Explosive(101);
	public static NeverBreak NEVER_BREAK = new NeverBreak(102);
	public static NightVision NIGHT_VISION = new NightVision(103);
	public static Fly FLY = new Fly(104);
	public static Jump JUMP = new Jump(105);
	public static Speed SPEED = new Speed(106);

	static {
		ENCHANTMENTS.add(EXPLOSIVE);
		ENCHANTMENTS.add(NEVER_BREAK);
		ENCHANTMENTS.add(NIGHT_VISION);
		ENCHANTMENTS.add(FLY);
		ENCHANTMENTS.add(JUMP);
		ENCHANTMENTS.add(SPEED);
	}

	public static void register() {
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");

				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				for (Enchantment ench : ENCHANTMENTS)
					Enchantment.registerEnchantment(ench);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void unregister() {
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

			for (Enchantment ench : ENCHANTMENTS) {
				if (byId.containsKey(ench.getId()))
					byId.remove(ench.getId());
				if (byName.containsKey(ench.getName()))
					byName.remove(ench.getName());
			}
		} catch (Exception ignored) {
		}
	}
}
