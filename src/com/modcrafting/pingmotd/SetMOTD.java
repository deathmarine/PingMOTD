package com.modcrafting.pingmotd;

import java.util.List;
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
		if (sender instanceof Player){
			player = (Player)sender;
			if (player.isOp()) auth = true;
		}else{
			auth = true;
		}
		if (!auth){
			sender.sendMessage(ChatColor.RED + "You do not have the required permissions.");
			return true;
		}
		if(auth){
			if(args.length < 1) return false;
			List<String> list = config.getStringList("Messages");
			list.add(combineSplit(args));
			config.set("Messages", (List<String>) list);
			plugin.saveConfig();
			sender.sendMessage("Saved Message!");
			return true;
		}
		return false;
	}

	public String combineSplit(String[] string) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < string.length; i++) {
			builder.append(string[i]);
			builder.append(" ");
		}

		builder.deleteCharAt(builder.length() - " ".length()); // remove
		return builder.toString();
	}
}