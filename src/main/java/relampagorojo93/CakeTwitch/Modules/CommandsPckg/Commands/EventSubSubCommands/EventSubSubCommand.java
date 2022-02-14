package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.EventSubSubCommands;

import relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.HelpSubCommand;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;

public class EventSubSubCommand extends Command {
	public EventSubSubCommand(Command command) {
		super(command, "eventsub", SettingString.CAKETWITCHTWITCH_EVENTSUB_NAME.toString(), SettingString.CAKETWITCHTWITCH_EVENTSUB_PERMISSION.toString(),
				SettingString.CAKETWITCHTWITCH_EVENTSUB_DESCRIPTION.toString(), SettingString.CAKETWITCHTWITCH_EVENTSUB_PARAMETERS.toString(),
				SettingList.CAKETWITCH_EVENTSUB_ALIASES.toList());
		addCommand(new InformationSubCommand(this));
		addCommand(new ResetSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}