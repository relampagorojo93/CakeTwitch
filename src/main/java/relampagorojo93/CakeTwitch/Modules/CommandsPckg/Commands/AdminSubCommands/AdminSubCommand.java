package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.AdminSubCommands;

import relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.HelpSubCommand;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;

public class AdminSubCommand extends Command {
	public AdminSubCommand(Command command) {
		super(command, "admin", SettingString.CAKETWITCHTWITCH_ADMIN_NAME.toString(), SettingString.CAKETWITCHTWITCH_ADMIN_PERMISSION.toString(),
				SettingString.CAKETWITCHTWITCH_ADMIN_DESCRIPTION.toString(), SettingString.CAKETWITCHTWITCH_ADMIN_PARAMETERS.toString(),
				SettingList.CAKETWITCH_ADMIN_ALIASES.toList());
		addCommand(new RegisterSubCommand(this));
		addCommand(new UnregisterSubCommand(this));
		addCommand(new ClearQueueSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}