package me.Sunney;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener
{
	
	public BaseConfig conf;
	public static Main instance;
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Enabling Plugin");
	    Bukkit.getPluginManager().registerEvents(this, (Plugin)this);
	    instance = this;
	    this.conf = new BaseConfig("config", this);
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("Disabling Plugin");
	}
	
	public BaseConfig getConf() {
		return this.conf;
    }
	
	public static Main getInstance() {
		return instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (Main.getInstance().getConf().getConfig().getBoolean("JoinQuit.enabled")) {
			String title = Main.getInstance().getConf().getConfig().getString("JoinQuit.Title.title").replaceAll("&", "§").replace("%player%", p.getName());
			String subtitle = Main.getInstance().getConf().getConfig().getString("JoinQuit.Title.subTitle").replaceAll("&", "§").replace("%player%", p.getName());
			String msg1 = Main.getInstance().getConf().getConfig().getString("JoinQuit.Join.message").replaceAll("&", "§").replace("%player%", p.getName());
			e.setJoinMessage(msg1);
			p.sendTitle(translate(title), translate(subtitle));
			for (int i = 0; i < 200; i++)
				p.sendMessage(" ");
			p.sendMessage(Main.getInstance().getConf().getConfig().getString("JoinQuit.Motd.joinMotd").replaceAll("&", "§").replace("%player%", p.getName()));
		}
		if (Main.getInstance().getConf().getConfig().getBoolean("JoinQuit.Join.disableVanillaMsg")) {
			e.setJoinMessage(null);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Main.getInstance().getConf().getConfig().getBoolean("JoinQuit.enabled")) {
			String msg1 = Main.getInstance().getConf().getConfig().getString("JoinQuit.Quit.message").replaceAll("&", "§").replace("%player%", p.getName());
			e.setQuitMessage(msg1);
		}
		if (Main.getInstance().getConf().getConfig().getBoolean("JoinQuit.Quit.disableVanillaMsg")) {
			e.setQuitMessage(null);
		}
	}
	
	public static String translate(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
}
