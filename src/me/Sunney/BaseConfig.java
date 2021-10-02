package me.Sunney;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Sunney.Main;

public class BaseConfig 
{
	
	protected File file;
	protected FileConfiguration config;
	protected String name;
	protected Main plugin;
	
	public BaseConfig(String name, Main plugin) {
		this.plugin = plugin;
		this.name = name;
		this.file = new File(plugin.getDataFolder(), String.valueOf(this.name) + ".yml");
		checkConfig();
		reload();
	}
	
	public void reload() {
		this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
	}
	
	public FileConfiguration getConfig() {
		if (this.config == null)
			reload();
		return this.config;
	}
	
	private void checkConfig() {
		if (this.file.exists()) {
			YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(this.plugin.getResource(String.valueOf(this.name) + ".yml")));
			if (getConfig().getKeys(true).containsAll(yamlConfiguration.getKeys(true)))
				return;
		}
		this.plugin.saveResource(String.valueOf(this.name) + ".yml", true);
	}
	
	protected void saveToFile() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
