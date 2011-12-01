package com.modcrafting.pingmotd;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServerListener;

public class PingMOTDServerListener extends ServerListener{
	PingMotd plugin;

	public PingMOTDServerListener(PingMotd instance) {
		plugin = instance;
	}
	public void onServerListPing(ServerListPingEvent event){
		YamlConfiguration Config = (YamlConfiguration) plugin.getConfig();
		String defMsg = event.getMotd();
		String newMsg = Config.getString("message", defMsg);
		event.setMotd(newMsg);
	}
}
