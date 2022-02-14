package relampagorojo93.CakeTwitch.Modules.CommandsPckg;

import org.bukkit.command.Command;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.CakeTwitchCommand;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.LibsCollection.SpigotCommands.CommandsUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;

public class CommandsModule extends PluginModule {
	
	public boolean load() {
		if (SettingBoolean.COMMAND_CAKETWITCH_ENABLE.toBoolean())
			caketwitch = CommandsUtils.registerCommand(CakeTwitchAPI.getPlugin(), ccaketwitch = new CakeTwitchCommand());
		return true;
	}

	@Override
	public boolean unload() {
		if (this.caketwitch != null)
			CommandsUtils.unregisterCommand(CakeTwitchAPI.getPlugin(), this.caketwitch);
		caketwitch = null;
		ccaketwitch = null;
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.ENABLE;
	}

	@Override
	public boolean optional() {
		return true;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	private CakeTwitchCommand ccaketwitch;
	private Command caketwitch;
	
	public CakeTwitchCommand getCakeTwitchCommand() {
		return ccaketwitch;
	}
	
}
