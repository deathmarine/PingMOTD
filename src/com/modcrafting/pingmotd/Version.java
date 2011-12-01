package com.modcrafting.pingmotd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class Version implements CommandExecutor {
	PingMotd plugin;
	public Version(PingMotd pingMotd) {
		this.plugin = pingMotd;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		PluginDescriptionFile pdfFile = plugin.getDescription();sender.sendMessage(ChatColor.GRAY + pdfFile.getName() + " version " + pdfFile.getVersion() + " by " + pdfFile.getAuthors());
		return true;
	}

}
