package com.modcrafting.pingmotd;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PingMotd extends JavaPlugin implements Listener{
	Random gen = new Random();
	List<String> list = new ArrayList<String>();
	@Override
	public void onEnable() {
		this.getDataFolder().mkdir();
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
		list = this.getConfig().getStringList("Messages");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onServerListPing(ServerListPingEvent event){
		event.setMotd(ChatColor.translateAlternateColorCodes("&".toCharArray()[0], list.get(gen.nextInt(list.size()))));
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length<1){
			this.getServer().dispatchCommand(sender, "version pingmotd");
			return true;
		}
		if(args[0].equalsIgnoreCase("view")){
			if(!sender.hasPermission("pingmotd.view"))
				return deny(sender);
			int i = 1;
			try{
				if(args.length>1) 
					i=Integer.parseInt(args[1]);
			}catch(NumberFormatException nfe){}
			sender.sendMessage(ChatColor.GRAY+"---MOTDs List ---");
			for(int ii=(i-1)*10;ii<(10)*i;ii++)
				if(ii<(list.size()))
					sender.sendMessage(ChatColor.GREEN+"Message ID#"+ChatColor.AQUA+String.valueOf(ii)+" ["+list.get(ii)+"]");
			sender.sendMessage(ChatColor.GRAY+"Page ["+String.valueOf(i)+" of "+String.valueOf((int) Math.abs(((double) list.size())/10)+1)+"]");
		}
		if(args[0].equalsIgnoreCase("add")){
			if(!sender.hasPermission("pingmotd.add"))
				return deny(sender);
			StringBuilder builder = new StringBuilder();
			for (int i=1;i<args.length;i++) 
				builder.append(args[i]).append(" ");
			list.add(builder.deleteCharAt(builder.length() - 1).toString());
			this.getConfig().set("Messages", list);
			this.saveConfig();
			sender.sendMessage(ChatColor.GREEN+"Message added.");
		}
		if(args[0].equalsIgnoreCase("del")){
			if(!sender.hasPermission("pingmotd.del"))
				return deny(sender);
			if(args.length<1)
				return param(sender, "/"+command.getLabel()+" del [ID#]");
			try{
				int i=Integer.parseInt(args[1]);
				if(i<0||i>=list.size()){
					return param(sender, "ID#");
				}else{
					list.remove(i);
					this.getConfig().set("Messages", list);
					this.saveConfig();
					sender.sendMessage(ChatColor.BLUE+"Message Removed.");
				}
			}catch(NumberFormatException nfe){
				return param(sender, "ID#");
			}
		}
		if(args[0].equalsIgnoreCase("reload")){
			if(!sender.hasPermission("pingmotd.reload"))
				return deny(sender);
			this.reloadConfig();
			sender.sendMessage(ChatColor.AQUA+"Configuration Reloaded.");
		}
		return true;
	}
	private boolean deny(CommandSender sender) {
		sender.sendMessage(ChatColor.RED+"You do not have the required permissions.");
		return true;
	}
	private boolean param(CommandSender sender, String label) {
		sender.sendMessage(ChatColor.RED+"Enter the correct parameter." +label);
		return true;
	}
}
