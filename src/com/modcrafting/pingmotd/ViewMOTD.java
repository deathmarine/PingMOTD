package com.modcrafting.pingmotd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ViewMOTD implements CommandExecutor {
	PingMotd plugin;
	public ViewMOTD(PingMotd pingMotd) {
		this.plugin = pingMotd;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		YamlConfiguration config = (YamlConfiguration) plugin.getConfig();
			boolean auth = false;
			Player player = null;
			if (sender instanceof Player){
				player = (Player)sender;
				if (player.isOp()) auth = true; //defaulting to Op if no permissions or node
			}else{
				auth = true; //if sender is not a player - Console
			}
			if (!auth){
				sender.sendMessage(ChatColor.RED + "You do not have the required permissions.");
				return true;
			}
			if(auth){
				String getMsg = config.getString("message", null);
				sender.sendMessage("Message: " + getMsg);
				return true;
			}
			return false;
		}

}
