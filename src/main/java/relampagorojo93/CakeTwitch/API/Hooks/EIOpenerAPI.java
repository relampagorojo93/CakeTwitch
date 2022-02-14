package relampagorojo93.CakeTwitch.API.Hooks;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch.StreamerInventory;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch.StreamersInventory;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.EzInvOpener.API.EIOAPI;
import relampagorojo93.EzInvOpener.API.Objects.EzInventory;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;

public class EIOpenerAPI {
	private static boolean hooked = false;

	public EIOpenerAPI() {
		hooked = false;
		if (SettingBoolean.HOOKS_EIOPENER.toBoolean()) {
			MessagesUtils.getMessageBuilder()
					.createMessage(
							MessageString.applyPrefix("<EIOpener> Hook is enabled. Finding EzInvOpener."))
					.sendMessage(Bukkit.getConsoleSender());
			if (Bukkit.getPluginManager().getPlugin("EzInvOpener") != null) {
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString
								.applyPrefix("<EIOpener> EzInvOpener found. Creating ez inventories."))
						.sendMessage(Bukkit.getConsoleSender());
				EIOAPI.getInvAPI().registerInventory(new EzInventory(CakeTwitchAPI.getPlugin(), "streamer-menu",
						(player) -> StreamerInventory.getEzInventory(player)));
				EIOAPI.getInvAPI().registerInventory(new EzInventory(CakeTwitchAPI.getPlugin(), "streamers-menu",
						(player) -> StreamersInventory.getEzInventory(player)));
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString.applyPrefix("<EIOpener> Done!"))
						.sendMessage(Bukkit.getConsoleSender());
				hooked = true;
			} else
				MessagesUtils.getMessageBuilder()
						.createMessage(MessageString
								.applyPrefix("<EIOpener> EzInvOpener not found. Ignoring its implementation."))
						.sendMessage(Bukkit.getConsoleSender());
		} else
			MessagesUtils.getMessageBuilder()
					.createMessage(MessageString
							.applyPrefix("<EIOpener> Hook is disabled. Ignoring its implementation."))
					.sendMessage(Bukkit.getConsoleSender());
	}

	public static boolean isHooked() {
		return hooked;
	}
}
