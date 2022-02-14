package relampagorojo93.CakeTwitch.Modules.ConfigPckg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.Configurations.Configuration;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;

public class ConfigModule extends PluginModule {

	public boolean load() {
		for (File f : CakeTwitchAPI.getFile().CONFIGS_FOLDER.listFiles())
			try {
				configs.put(f.getName().replace(".yml", ""), new Configuration(f));
			} catch (Exception e) {
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("Configuration " + f.getName() + " is not valid!"))
						.sendMessage(Bukkit.getConsoleSender());
			}
		return true;
	}

	@Override
	public boolean unload() {
		configs.clear();
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	private HashMap<String, Configuration> configs = new HashMap<>();

	public Configuration getConfig(String config) {
		return configs.get(config);
	}

	public List<Configuration> getConfigs() {
		return new ArrayList<>(configs.values());
	}

}
