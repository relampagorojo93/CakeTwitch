package relampagorojo93.CakeTwitch.Bukkit.Events.PluginEvents;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.Enums.EventType;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Data.Streamer;

public class CakeTwitchChannelRaidEvent extends CakeTwitchEvent {
	private int viewers;
	public CakeTwitchChannelRaidEvent(UUID id, Streamer streamer, String user, int viewers, Map<String, String> tags) {
		super(id, streamer, user, tags);
		this.eventtype = EventType.RAID;
		this.viewers = viewers;
	}
	public int getViewers() { return viewers; }
	@Override
	public String processCommand(String command, String ign, UUID uuid) {
		return command.replace("%user%", getUser().replace("\s", ""))
				.replace("%user-uuid%", uuid != null ? uuid.toString() : "?").replace("%user-ign%", ign)
				.replace("%raid%", String.valueOf(getViewers())).replace("%ign%",
						Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
	}
}
