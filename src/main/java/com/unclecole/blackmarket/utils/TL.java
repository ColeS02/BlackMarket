package com.unclecole.blackmarket.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public enum TL {
	NO_PERMISSION("messages.no-permission", "&cYou don't have the permission to do that."),
	INVALID_ARGUMENTS("messages.invalid-arguments", "&cInvalid Arguments: <command>"),
	INVALID_PLAYER("messages.invalid-player", "&cInvalid Player"),
	INVALID_LOCATION("messages.invalid-location", "&cInvalid Location"),
	SOLD_ITEMS("messages.sold-items", "&fYou have successfully sold %amount%x %item% for %money%"),
	SOLD_CHEST("messages.sold-chest", "&fYou have successfully sold chest for %money%!"),
	RESET_AUCTION("messages.reset-auction", "&5&lBLACK MARKET &8» &7The black market has been restocked with new items!"),
	INSUFFICIENT_FUNDS("messages.insufficient-funds", "&cYou don't have enough to buy this item!"),
	BOUGHT_ITEMS("messages.bought-items", "&aSuccessfully bought 1x %item%"),
	NOTHING_TOO_SELL("messages.nothing-too-sell", "&fThere is nothing too sell!"),
	RELOADED_PLUGIN("messages.reloaded", "&aPlugin has been reloaded!");
	private final String path;

	private String def;
	private static ConfigFile config;

	TL(String path, String start) {
		this.path = path;
		this.def = start;
	}

	public String getDefault() {
		return this.def;
	}

	public String getPath() {
		return this.path;
	}

	public void setDefault(String message) {
		this.def = message;
	}

	public void send(CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			sender.sendMessage(PlaceholderAPI.setPlaceholders(player, C.color(getDefault())));
		} else {
			sender.sendMessage(C.strip(getDefault()));
		}
	}

	public static void loadMessages(ConfigFile configFile) {
		config = configFile;
		FileConfiguration data = configFile.getConfig();
		for (TL message : values()) {
			if (!data.contains(message.getPath())) {
				data.set(message.getPath(), message.getDefault());
			}
		}
		configFile.save();
	}


	public void send(CommandSender sender, PlaceHolder... placeHolders) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			sender.sendMessage(PlaceholderAPI.setPlaceholders(player, C.color(getDefault(), placeHolders)));
		} else {
			sender.sendMessage(C.strip(getDefault(), placeHolders));
		}
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(C.color(message));
	}

	public static void message(CommandSender sender, String message, PlaceHolder... placeHolders) {
		sender.sendMessage(C.color(message, placeHolders));
	}

	public static void message(CommandSender sender, List<String> message) {
		message.forEach(m -> sender.sendMessage(C.color(m)));
	}

	public static void message(CommandSender sender, List<String> message, PlaceHolder... placeHolders) {
		message.forEach(m -> sender.sendMessage(C.color(m, placeHolders)));
	}

	public static void log(Level lvl, String message) {
		Bukkit.getLogger().log(lvl, message);
	}
}
