package relampagorojo93.CakeTwitch.API;

import org.bukkit.plugin.java.JavaPlugin;

import relampagorojo93.CakeTwitch.CakeTwitch;
import relampagorojo93.CakeTwitch.Modules.CommandsPckg.CommandsModule;
import relampagorojo93.CakeTwitch.Modules.CommandsQueuePckg.CommandsQueueModule;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.ConfigModule;
import relampagorojo93.CakeTwitch.Modules.DropsPckg.DropsModule;
import relampagorojo93.CakeTwitch.Modules.EmojisPckg.EmojisModule;
import relampagorojo93.CakeTwitch.Modules.EventSubPckg.EventSubModule;
import relampagorojo93.CakeTwitch.Modules.FilePckg.FileModule;
import relampagorojo93.CakeTwitch.Modules.HTTPPckg.HTTPModule;
import relampagorojo93.CakeTwitch.Modules.PendingCommandsPckg.PendingCommandsModule;
import relampagorojo93.CakeTwitch.Modules.ResourcePacksPckg.ResourcePacksModule;
import relampagorojo93.CakeTwitch.Modules.SQLPckg.SQLModule;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.StreamersModule;
import relampagorojo93.CakeTwitch.Modules.WebQueryPckg.BasicWebQueryModule;
import relampagorojo93.CakeTwitch.Modules.WebQueryPckg.WebQueryModule;
import relampagorojo93.LibsCollection.SpigotThreads.ThreadManager;

public class CakeTwitchAPI {
	private static CakeTwitch plugin;
	
	public static void setPlugin(CakeTwitch plugin) {
		CakeTwitchAPI.plugin = plugin;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static ThreadManager getThreadManager() {
		return plugin.getThreadManager();
	}

	public static SQLModule getSQL() {
		return (SQLModule) plugin.getModule(SQLModule.class);
	}

	public static FileModule getFile() {
		return (FileModule) plugin.getModule(FileModule.class);
	}

	public static StreamersModule getStreamers() {
		return (StreamersModule) plugin.getModule(StreamersModule.class);
	}

	public static ConfigModule getConfig() {
		return (ConfigModule) plugin.getModule(ConfigModule.class);
	}

	public static BasicWebQueryModule getBasicWebQuery() {
		return (BasicWebQueryModule) plugin.getModule(BasicWebQueryModule.class);
	}

	public static WebQueryModule getWebQuery() {
		return (WebQueryModule) plugin.getModule(WebQueryModule.class);
	}

	public static EventSubModule getEventSub() {
		return (EventSubModule) plugin.getModule(EventSubModule.class);
	}

	public static EmojisModule getEmojis() {
		return (EmojisModule) plugin.getModule(EmojisModule.class);
	}
	
	public static HTTPModule getHTTP() {
		return (HTTPModule) plugin.getModule(HTTPModule.class);
	}

	public static ResourcePacksModule getResourcePacks() {
		return (ResourcePacksModule) plugin.getModule(ResourcePacksModule.class);
	}

	public static CommandsQueueModule getCommandsQueue() {
		return (CommandsQueueModule) plugin.getModule(CommandsQueueModule.class);
	}

	public static PendingCommandsModule getPendingCommands() {
		return (PendingCommandsModule) plugin.getModule(PendingCommandsModule.class);
	}

	public static CommandsModule getCommands() {
		return (CommandsModule) plugin.getModule(CommandsModule.class);
	}

	public static DropsModule getDrops() {
		return (DropsModule) plugin.getModule(DropsModule.class);
	}

	public static void reloadPlugin() {
		plugin.reloadPlugin();
	}

	public static int getSQLVersion() {
		return 7;
	}

}