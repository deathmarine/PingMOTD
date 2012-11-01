package com.modcrafting.pingmotd;
/*
 * 
 * Never Gonna Stop Meh
 * 
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PingMotd extends JavaPlugin implements Listener{
	public String maindir = "plugins/PingMOTD/";
	public String newMsg;
	protected void createDefaultConfiguration(String name) {
		File actual = new File(getDataFolder(), name);
		if (!actual.exists()) {

			InputStream input =
				this.getClass().getResourceAsStream("/" + name);
			if (input != null) {
				FileOutputStream output = null;

				try {
					output = new FileOutputStream(actual);
					byte[] buf = new byte[8192];
					int length = 0;
					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}

					this.getLogger().log(Level.INFO, getDescription().getName()
							+ ": Default configuration file written: " + name);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (input != null)
							input.close();
					} catch (IOException e) {}

					try {
						if (output != null)
							output.close();
					} catch (IOException e) {}
				}
			}
		}
	}
	@Override
	public void onEnable() {
		new File(maindir).mkdir();
		createDefaultConfiguration("config.yml");
		loadCommands();
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	private void loadCommands() {
		getCommand("pmotdadd").setExecutor(new SetMOTD(this));
		getCommand("pmotdview").setExecutor(new ViewMOTD(this));
		getCommand("pmotdreload").setExecutor(new Reload(this));
		getCommand("pmotd").setExecutor(new Version(this));
		return;
		
	}
	public String newMsg(){
		YamlConfiguration config = (YamlConfiguration) this.getConfig();
		Random generator = new Random();
		List<String> list = config.getStringList("Messages");
		int randomizer = generator.nextInt(list.size());
		String newMsg = list.get(randomizer);
		return newMsg;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onServerListPing(ServerListPingEvent event){
		String msg = newMsg();
		if (msg != null){
			event.setMotd(ChatColor.translateAlternateColorCodes("&".toCharArray()[0], msg));	
		}
	}
}
