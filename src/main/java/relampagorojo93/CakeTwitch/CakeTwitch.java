package relampagorojo93.CakeTwitch;

import org.bukkit.Bukkit;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.API.Hooks.EIOpenerAPI;
import relampagorojo93.CakeTwitch.API.Hooks.PlaceholderAPI;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEvents;
import relampagorojo93.CakeTwitch.Bukkit.Events.InventoryEvents;
import relampagorojo93.CakeTwitch.Bukkit.Events.PlayerEvents;
import relampagorojo93.CakeTwitch.Bukkit.Events.TwitchEvents;
import relampagorojo93.CakeTwitch.Modules.CommandsPckg.CommandsModule;
import relampagorojo93.CakeTwitch.Modules.CommandsQueuePckg.CommandsQueueModule;
import relampagorojo93.CakeTwitch.Modules.ConfigPckg.ConfigModule;
import relampagorojo93.CakeTwitch.Modules.DropsPckg.DropsModule;
import relampagorojo93.CakeTwitch.Modules.EmojisPckg.EmojisModule;
import relampagorojo93.CakeTwitch.Modules.EventSubPckg.EventSubModule;
import relampagorojo93.CakeTwitch.Modules.FilePckg.FileModule;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.CakeTwitch.Modules.HTTPPckg.HTTPModule;
import relampagorojo93.CakeTwitch.Modules.PendingCommandsPckg.PendingCommandsModule;
import relampagorojo93.CakeTwitch.Modules.ResourcePacksPckg.ResourcePacksModule;
import relampagorojo93.CakeTwitch.Modules.SQLPckg.SQLModule;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.StreamersModule;
import relampagorojo93.CakeTwitch.Modules.WebQueryPckg.BasicWebQueryModule;
import relampagorojo93.CakeTwitch.Modules.WebQueryPckg.WebQueryModule;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.MainClass;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;

public class CakeTwitch extends MainClass {

	// ---------------------------------------------------------------//
	// MainClass methods
	// ---------------------------------------------------------------//

	public CakeTwitch() {
		super(new FileModule(), new SQLModule(), new CommandsModule(), new ConfigModule(),
				new CommandsQueueModule(), new PendingCommandsModule(), new HTTPModule(), new BasicWebQueryModule(),
				new WebQueryModule(), new EventSubModule(), new ResourcePacksModule(),
				new EmojisModule(), new StreamersModule(), new DropsModule());
	}

	@Override
	public String getPrefix() {
		return MessageString.PREFIX.toString();
	}

	@Override
	public boolean canLoad() {
		if (CakeTwitchAPI.getSQL().isConnected() || CakeTwitchAPI.getSQL().connect(SettingBoolean.SQL.toBoolean()
				? new MySQLConnectionData(SettingString.PROTOCOL.toString(), SettingString.HOST.toString(),
						SettingInt.PORT.toInt(), SettingString.DATABASE.toString(), SettingString.USERNAME.toString(),
						SettingString.PASSWORD.toString(), SettingString.PARAMETERS.toString().split("&"))
				: new SQLiteConnectionData(
						String.valueOf(CakeTwitchAPI.getFile().PLUGIN_FOLDER.getPath()) + "/DB.sqlite")))
			return true;
		return false;
	}

	@Override
	public boolean canEnable() {
		return true;
	}

	@Override
	public boolean load() {
		new PlaceholderAPI();
		return true;
	}

	@Override
	public boolean enable() {
		if (isFirstTime()) {
			new EIOpenerAPI();
			Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
			Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);
			Bukkit.getPluginManager().registerEvents(new TwitchEvents(), this);
			Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
			new MetricsLite(this, 10083);
		}
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                     __                    ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                       _            |  |                   ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                     _| |___ ___ ___|  |                   ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                    | . | . |   | -_|__|                   ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                    |___|___|_|_|___|__|                   ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		return true;
	}

	@Override
	public boolean disable() {
		CakeTwitchAPI.setPlugin(null);
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                            __             ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                      _           _       _|  |            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "              _ _ ___| |___ ___ _| |___ _| |  |            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "             | | |   | | . | .'| . | -_| . |__|            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "             |___|_|_|_|___|__,|___|___|___|__|            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		return true;
	}

	@Override
	public boolean beforeLoad() {
		CakeTwitchAPI.setPlugin(this);
		return true;
	}

	@Override
	public boolean beforeEnable() {
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                  _       _         _ _       _             ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "          ___ ___| |_ ___| |_ _ _ _|_| |_ ___| |_           ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "         |  _| .'| '_| -_|  _| | | | |  _|  _|   |          ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "         |___|__,|_,_|___|_| |_____|_|_| |___|_|_|          ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                                            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "               By relampagorojo93/DarkPanda73               ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                                            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                 _           _ _                            ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                | |___ ___ _| |_|___ ___                    ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                | | . | .'| . | |   | . |_ _ _              ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                |_|___|__,|___|_|_|_|_  |_|_|_|             ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "                                    |___|                   ").sendMessage(Bukkit.getConsoleSender());
		MessagesUtils.getMessageBuilder().createMessage(getPrefix() + "").sendMessage(Bukkit.getConsoleSender());
		return true;
	}

	@Override
	public boolean beforeDisable() {
		return true;
	}

}
