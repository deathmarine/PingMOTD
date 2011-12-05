package com.modcrafting.pingmotd;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetMOTD implements CommandExecutor {
	PingMotd plugin;
	public SetMOTD(PingMotd pingMotd) {
		this.plugin = pingMotd;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		YamlConfiguration config = (YamlConfiguration) plugin.getConfig();
		boolean auth = false;
		Player player = null;
		String admin = "server";
		if (sender instanceof Player){
			player = (Player)sender;
			if (player.isOp()) auth = true;
			admin = player.getName();
		}else{
			auth = true; //if sender is not a player - Console
		}
		if (!auth){
			sender.sendMessage(ChatColor.RED + "You do not have the required permissions.");
			return true;
		}
		if(auth){
			if(args.length < 1) return false;
			String setMsg = combineSplit(0, args, " ");
			config.set("message", (String) setMsg);
			sender.sendMessage("Message Set: " + setMsg);
			plugin.log.log(Level.INFO, admin + " set ping message!");
			plugin.saveConfig();
			return true;
		}
		return false;
	}
	public String combineSplit(int startIndex, String[] string, String seperator) {
		StringBuilder builder = new StringBuilder();

		for (int i = startIndex; i < string.length; i++) {
			builder.append(string[i]);
			builder.append(seperator);
		}

		builder.deleteCharAt(builder.length() - seperator.length()); // remove
		return builder.toString();
	}
}
