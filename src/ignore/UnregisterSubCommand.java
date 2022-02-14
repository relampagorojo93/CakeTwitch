package relampagorojo93.CakeTwitch.Modules.CommandsPckg.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.General.ConfirmationInventory;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.Command;
import relampagorojo93.LibsCollection.SpigotCommands.Objects.SubCommand;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;

public class UnregisterSubCommand extends SubCommand {

	public UnregisterSubCommand(Command parent) {
		super(parent, "unregister", SettingString.CAKETWITCH_UNREGISTER_NAME.toString(),
				SettingString.CAKETWITCH_UNREGISTER_PERMISSION.toString(),
				SettingString.CAKETWITCH_UNREGISTER_DESCRIPTION.toString(),
				SettingString.CAKETWITCH_UNREGISTER_PARAMETERS.toString(),
				SettingList.CAKETWITCH_UNREGISTER_ALIASES.toList());
	}

	@Override
	public List<String> tabComplete(Command cmd, CommandSender sender, String[] args) {
		Player p = sender instanceof Player ? (Player) sender : null;
		List<String> list = new ArrayList<>();
		if (p != null) {
			switch (args.length) {
			case 1:
				if (sender.hasPermission("CakeTwitch.Twitch.Register.Admin"))
					for (Player pl : Bukkit.getOnlinePlayers())
						list.add(pl.getName());
				break;
			default:
				break;
			}
		}
		return list;
	}

	@Override
	public boolean execute(Command cmd, CommandSender sender, String[] args, boolean useids) {
		if (args.length > 1) {
			if (sender.hasPermission("CakeTwitch.Twitch.Unregister.Admin")) {
				@SuppressWarnings("deprecation")
				OfflinePlayer dest = Bukkit.getOfflinePlayer(args[1]);
				if (dest == null) {
					MessagesUtils.getMessageBuilder()
							.createMessage(MessageString.applyPrefix("This player doesn't exists!"))
							.sendMessage(sender);
					return true;
				}
				TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
					if (CakeTwitchAPI.getStreamers().getStreamer(dest.getUniqueId()).getSettings().setChannel(null))
						MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("Account unlinked successfully!"))
						.sendMessage(sender);
					else
						MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("Error!"))
								.sendMessage(sender);
				});
			} else
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("You don't have permissions for this!"))
						.sendMessage(sender);
		} else {
			Player pl = sender instanceof Player ? (Player) sender : null;
			if (pl != null) {
				Streamer streamer = CakeTwitchAPI.getStreamers().getStreamer(pl.getUniqueId());
				if (streamer != null) {
					if (streamer.getSettings().getChannel() != null) {
						new ConfirmationInventory(pl, () -> TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> {
							if (streamer.getSettings().setChannel(null))
								MessagesUtils.getMessageBuilder()
										.createMessage(
												MessageString.applyPrefix("Account unlinked successfully!"))
										.sendMessage(sender);
							else
								MessagesUtils.getMessageBuilder()
										.createMessage(MessageString.applyPrefix("Error!"))
										.sendMessage(sender);
						})).openInventory(CakeTwitchAPI.getPlugin());
					} else
						MessagesUtils.getMessageBuilder()
								.createMessage(MessageString.applyPrefix("You don't have a Twitch account!"))
								.sendMessage(sender);
				} else
					MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix("Error!"))
							.sendMessage(sender);
			}
		}
		return true;
	}
}
