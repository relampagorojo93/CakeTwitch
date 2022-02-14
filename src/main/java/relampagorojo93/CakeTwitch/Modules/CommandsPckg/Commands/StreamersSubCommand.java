package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch.StreamersInventory;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;

public class StreamersSubCommand extends SubCommand {

	public StreamersSubCommand(Command parent) {
		super(parent, "streamers", SettingString.CAKETWITCH_STREAMERS_NAME.toString(),
				SettingString.CAKETWITCH_STREAMERS_PERMISSION.toString(),
				SettingString.CAKETWITCH_STREAMERS_DESCRIPTION.toString(), "",
				SettingList.CAKETWITCH_STREAMERS_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player pl = sender instanceof Player ? (Player) sender : null;
		if (pl != null)
			new StreamersInventory(pl).openInventory(CakeTwitchAPI.getPlugin());
		return true;
	}
}
