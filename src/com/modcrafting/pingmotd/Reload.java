package com.modcrafting.pingmotd;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {
	public static final Logger log = Logger.getLogger("Minecraft");
	PingMotd plugin;
	public Reload(PingMotd pingMotd) {
		this.plugin = pingMotd;
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		boolean auth = false;
		Player player = null;
		if (sender instanceof Player){
			player = (Player)sender;
			if (player.isOp()) auth = true;
		}else{
			auth = true; //if sender is not a player - Console
		}
		if(!auth){
			sender.sendMessage(ChatColor.RED + "You do not have the required permissions.");
			return true;
		}
		if(auth){
			log.log(Level.INFO, "[PingMOTD] Disabling Plugin.");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			log.log(Level.SEVERE, "[PingMOTD] Attempting Restart.");
			plugin.getServer().getPluginManager().enablePlugin(plugin);
			sender.sendMessage("§2Reloaded plugin.");
			return true;	
		}
		return false;
	}

}
