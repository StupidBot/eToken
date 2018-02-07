package com.stupidbot.universaltokens.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;

import com.stupidbot.universaltokens.Main;
import com.stupidbot.universaltokens.utils.FileStorage;
import com.stupidbot.universaltokens.utils.Inventories;
import com.stupidbot.universaltokens.utils.Text;
import com.stupidbot.universaltokens.utils.Tokens;

public class OnCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("token"))
			if (args.length > 0 && (args[0].equalsIgnoreCase("bal") || args[0].equalsIgnoreCase("balance")
					|| args[0].equalsIgnoreCase("shop") || args[0].equalsIgnoreCase("withdraw")
					|| args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("giveall")
					|| args[0].equalsIgnoreCase("reload"))) {
				if (args[0].equalsIgnoreCase("bal") || args[0].equalsIgnoreCase("balance"))
					if (sender instanceof Player) {
						Player player = (Player) sender;

						player.sendMessage("§eTokens: §a"
								+ Text.formatInt(FileStorage.getCachedFiles().get(player).getInt("Stats.Tokens")));

					} else
						sender.sendMessage("§cThis command can only be run by a player.");
				else if (args[0].equalsIgnoreCase("shop"))
					if (sender instanceof Player) {
						Player player = (Player) sender;
						ItemStack item = player.getInventory().getItemInMainHand();

						Inventories.openTokenShop(player);

						if (item != null)
							if (item.getType() == Material.WOOD_PICKAXE || item.getType() == Material.STONE_PICKAXE
									|| item.getType() == Material.IRON_PICKAXE
									|| item.getType() == Material.DIAMOND_PICKAXE
									|| item.getType() == Material.GOLD_PICKAXE) {
								Inventories.updateTokenShop(player, item);
								player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
							}
					} else
						sender.sendMessage("§cThis command can only be run by a player.");
				else if (args[0].equalsIgnoreCase("withdraw"))
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (args.length > 1) {
							FileConfiguration file = FileStorage.getCachedFiles().get(player);
							int tokens = file.getInt("Stats.Tokens");

							try {
								int args1 = Integer.parseInt(args[1]);

								if (!(args1 > tokens)) {
									ItemStack token = new ItemStack(Material.MAGMA_CREAM);
									ItemMeta tokenMeta = token.getItemMeta();
									tokenMeta.setDisplayName("§eToken");

									List<String> tokenLore = new ArrayList<String>();
									tokenLore.add("§7Right-click to deposit.");

									tokenMeta.setLore(tokenLore);
									token.setItemMeta(tokenMeta);

									for (int i = 0; i < args1; i++)
										player.getInventory().addItem(token);

									file.set("Stats.Tokens", tokens - args1);
									FileStorage.updateCachedPlayerFile(player, file);
									player.sendMessage("§6" + Text.formatInt(args1) + " §aTokens withdrawn.");
								} else
									player.sendMessage("§cYou do not have enough tokens to do that.");
							} catch (Exception e) {
								player.sendMessage("§6" + args[1] + " §cis not a valid number.");
							}
						} else
							player.sendMessage("§cIncorrect usage! /" + label + " withdraw <amount>.");
					} else
						sender.sendMessage("§cThis command can only be run by a player.");
				else if (args[0].equalsIgnoreCase("give")) {
					if (sender.hasPermission(new Permission("UniversalTokens.Admin")))
						if (args.length > 2) {
							Player giveTo = Bukkit.getPlayer(args[1]);

							if (giveTo != null) {
								int amount = Integer.parseInt(args[2]);

								try {
									Tokens.give(giveTo, amount, false);
									sender.sendMessage("§aGave §6" + giveTo.getName() + " §e" + Text.formatInt(amount)
											+ " §aTokens!");
								} catch (Exception e) {
									sender.sendMessage("§6" + args[2] + " §cis not a valid number.");
								}
							} else
								sender.sendMessage("§6" + args[1] + " §cis not an online player.");
						} else
							sender.sendMessage("§cIncorrect usage! /" + label + " give <player> <amount>.");
					else
						sender.sendMessage("§cYou don't have permission to use this command.");
				} else if (args[0].equalsIgnoreCase("giveall")) {
					if (sender.hasPermission(new Permission("UniversalTokens.Admin")))
						if (args.length > 1) {
							int amount = Integer.parseInt(args[1]);

							try {
								for (Player all : Bukkit.getOnlinePlayers())
									Tokens.give(all, amount, false);
								sender.sendMessage("§aGave §6everyone §e" + Text.formatInt(amount) + " §aTokens!");
							} catch (Exception e) {
								sender.sendMessage("§6" + args[2] + " §cis not a valid number.");
							}
						} else
							sender.sendMessage("§cIncorrect usage! /" + label + " giveall <amount>.");
					else
						sender.sendMessage("§cYou don't have permission to use this command.");
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission(new Permission("UniversalTokens.Admin"))) {
						Main.getInstance().reloadConfig();
						sender.sendMessage("§aConfig reloaded.");
					} else
						sender.sendMessage("§cYou don't have permission to use this command.");
				}
			} else if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(Text.center("§d§m===============§b§m===============", 131));
				Text.sendJSON(
						"[\"\",{\"text\":\"" + Text.center("§c§lHELP§r §7- §aClick or Hover for info", 131)
								+ "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://github.com/StupidBot/eTokens\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§eeTokens §7is an open source plugin\ndeveloped by §aStupidBot §7and\n§7made for §cECLIPSE NETWORK §d§lCLICK\n§d§lHERE§7 to view the code on GitHub.\"}]}}}]",
						player);
				player.sendMessage(" ");
				Text.sendJSON(
						"[\"\",{\"text\":\"" + Text.center("§a/token bal §c- §7Shows tokens in your account.", 131)
								+ "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/token balance\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aClick to type out.\"}]}}}]",
						player);
				player.sendMessage(" ");
				Text.sendJSON(
						"[\"\",{\"text\":\"" + Text.center("§a/token withdraw <amount> §c- §7Withdraw tokens.", 131)
								+ "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/token withdraw\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aClick to type out.\"}]}}}]",
						player);
				sender.sendMessage(" ");
				Text.sendJSON(
						"[\"\",{\"text\":\"" + Text.center("§a/token shop §c- §7Open tokens shop.", 131)
								+ "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/token shop\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aClick to type out.\"}]}}}]",
						player);
				player.sendMessage(" ");
				Text.sendJSON(
						"[\"\",{\"text\":\"" + Text.center("§a/token help §c- §7¯\\\\_(ツ)_/¯.", 131)
								+ "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/token help\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"§aClick to type out.\"}]}}}]",
						player);
				player.sendMessage(Text.center("§b§m===============§d§m===============", 131));
			} else
				sender.sendMessage("§cIncorrect usage!");
		return true;
	}
}