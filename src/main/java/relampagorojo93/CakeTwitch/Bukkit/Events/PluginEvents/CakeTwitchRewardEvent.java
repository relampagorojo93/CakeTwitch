package relampagorojo93.CakeTwitch.Bukkit.Events.PluginEvents;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Enums.EventType;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Data.Streamer;

public class CakeTwitchRewardEvent extends CakeTwitchMessageEvent {
	private UUID reward;
	public CakeTwitchRewardEvent(UUID id, Streamer streamer, String user, String message, UUID reward, Map<String, String> tags) {
		super(id, streamer, user, message, tags);
		this.eventtype = EventType.REWARD;
		this.reward = reward;
	}
	public UUID getReward() { return reward; }
	@Override
	public String processCommand(String command, String ign, UUID uuid) {
		return command.replace("%msg%", CakeTwitchAPI.getEmojis().applyEmojis(getMessage())).replace("%user%", getUser().replace("\s", ""))
				.replace("%user-uuid%", uuid != null ? uuid.toString() : "?").replace("%user-ign%", ign).replace("%ign%",
						Bukkit.getOfflinePlayer(getStreamer().getUniqueID()).getName());
	}
}
