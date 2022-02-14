package relampagorojo93.CakeTwitch.Bukkit.Events.PluginEvents;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Enums.EventType;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Data.Streamer;

public class CakeTwitchSubscriptionEvent extends CakeTwitchMessageEvent {
	private int months;
	public CakeTwitchSubscriptionEvent(UUID id, Streamer streamer, String user, String message, int months, Map<String, String> tags) {
		super(id, streamer, user, message, tags);
		this.eventtype = EventType.SUBSCRIPTION;
		this.months = months;
	}
	public int getMonths() { return months; }
	@Override
	public String processCommand(String command, String ign, UUID uuid) {
		return command.replace("%msg%", CakeTwitchAPI.getEmojis().applyEmojis(getMessage())).replace("%sub%", getUser().replace("\s", ""))
				.replace("%sub-uuid%", uuid != null ? uuid.toString() : "?").replace("%sub-ign%", ign)
				.replace("%months%", String.valueOf(getMonths())).replace("%ign%",
						Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
	}
}
