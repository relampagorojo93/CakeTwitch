package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands;

import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.HelpCommand;

public class HelpSubCommand extends HelpCommand {
	
	public HelpSubCommand(Command parent) {
		super(parent, SettingString.CAKETWITCH_HELP_NAME.toString(), SettingString.CAKETWITCH_HELP_PERMISSION.toString(),
				SettingString.CAKETWITCH_HELP_DESCRIPTION.toString(), SettingString.CAKETWITCH_HELP_PARAMETERS.toString(),
				SettingList.CAKETWITCH_HELP_ALIASES.toList());
		this.setHeader(() -> MessageList.HELP_HEADER.toList());
		this.setBody(() -> MessageList.HELP_BODY.toList());
		this.setFooter(() -> MessageList.HELP_FOOT.toList());
		this.setAvailableLeftArrow(() -> MessageString.HELP_LEFTARROWAVAILABLE.toString());
		this.setAvailableRightArrow(() -> MessageString.HELP_RIGHTARROWAVAILABLE.toString());
		this.setUnavailableLeftArrow(() -> MessageString.HELP_LEFTARROWUNAVAILABLE.toString());
		this.setUnavailableRightArrow(() -> MessageString.HELP_RIGHTARROWUNAVAILABLE.toString());
	}
	
}
