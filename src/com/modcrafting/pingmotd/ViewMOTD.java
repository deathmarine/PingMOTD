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
				String[] array = array();
				for (int i=1; i<array.length; i++){
					sender.sendMessage(array[i]);
				}
				return true;
			}
			return false;
		}
	public String[] array(){
		YamlConfiguration config = (YamlConfiguration) plugin.getConfig();
		int amtMsg = config.getInt("messagenum", 1);
		String[] strarray = new String[amtMsg + 1];
		for (int i=1; i < strarray.length; i ++) {
			String intStr = Integer.toString(i);
			    strarray[i] = config.getString("message." + intStr);
		}
		return strarray;
		
	}
 
}