package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.SQLSubCommands;

import relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands.HelpSubCommand;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;

public class SQLCommand extends Command {
	public SQLCommand(Command command) {
		super(command, "sql", SettingString.CAKETWITCHSQL_NAME.toString(), SettingString.CAKETWITCHSQL_PERMISSION.toString(),
				SettingString.CAKETWITCHSQL_DESCRIPTION.toString(), SettingString.CAKETWITCHSQL_PARAMETERS.toString(),
				SettingList.CAKETWITCHSQL_ALIASES.toList());
		addCommand(new ParseFromSQLiteSubCommand(this));
		addCommand(new ParseFromMySQLSubCommand(this));
		sortCommands();
		addCommand(new HelpSubCommand(this), 0);
	}
}