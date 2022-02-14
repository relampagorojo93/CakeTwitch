package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEvents;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEventsObjects.InputData;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEventsObjects.SpecifyChannelInputData;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch.RegistrationMethodInventory;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Data.Streamer;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;

public class RegisterSubCommand extends SubCommand {

	public RegisterSubCommand(Command parent) {
		super(parent, "register", SettingString.CAKETWITCH_REGISTER_NAME.toString(),
				SettingString.CAKETWITCH_REGISTER_PERMISSION.toString(),
				SettingString.CAKETWITCH_REGISTER_DESCRIPTION.toString(), "",
				SettingList.CAKETWITCH_REGISTER_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		Player pl = sender instanceof Player ? (Player) sender : null;
		if (pl != null) {
			Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(pl.getUniqueId());
			if (!streamer.isRegistered()) {
				if (CakeTwitchAPI.getWebQuery() == null || CakeTwitchAPI.getHTTP() == null) {
					MessagesUtils.getMessageBuilder().sendTitle(pl, true, "REGISTRATION",
							"Type the username of your channel\n Type 'cancel' to stop the registration", 20, 100, 20);
					ChatEvents.register((InputData) new SpecifyChannelInputData(pl));
				} else
					new RegistrationMethodInventory(pl).openInventory(CakeTwitchAPI.getPlugin());
			} else
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("You already have a Twitch account!"))
						.sendMessage(sender);
		} else
			MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.CONSOLEDENIED))
					.sendMessage(sender);
		return true;
	}
}
