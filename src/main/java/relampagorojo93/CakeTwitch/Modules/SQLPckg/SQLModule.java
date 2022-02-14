package relampagorojo93.CakeTwitch.Modules.SQLPckg;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.CakeTwitch.Modules.StreamersPckg.Objects.Data.Streamer;
import relampagorojo93.CakeTwitch.Modules.WebQueryPckg.Objects.Token;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.ConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Abstracts.SQLParser;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.ConditionType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Enums.SQLType;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Conditions.Condition;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.MySQLConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.ConnectionData.SQLiteConnectionData;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.Data;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Column;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Database;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.MySQLDatabase;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.SubDatabases.SQLiteDatabase;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.Objects.DataModel.Table;
import relampagorojo93.LibsCollection.Utils.Shared.SQL.SQLObject;

public class SQLModule extends PluginModule {
	public boolean load() {
		return true;
	}

	public boolean unload() {
		close();
		return true;
	}

	public LoadOn loadOn() {
		return LoadOn.BEFORE_LOAD;
	}

	public boolean optional() {
		return false;
	}

	public boolean allowReload() {
		return false;
	}

	protected final SQLObject sql = new SQLObject();

	protected String prefix = "";

	protected Database database;

	public SQLType getType() {
		return this.sql.getType();
	}

	public boolean connect(ConnectionData data) {
		MessagesUtils.getMessageBuilder()
				.createMessage(new String[] { MessageString.applyPrefix("Connecting to database...") })
				.sendMessage(new CommandSender[] { (CommandSender) Bukkit.getConsoleSender() });
		if (!this.sql.request(data))
			return false;
		if (data.getType() == SQLType.MYSQL) {
			this.database = (Database) new MySQLDatabase(SettingString.TABLEPREFIX.toString());
		} else {
			this.database = (Database) new SQLiteDatabase();
		}
		if (!generateDatabase(this.database).updateTables(String.valueOf(CakeTwitchAPI.getSQLVersion()), this.sql,
				new SQLParser[] { new SQLParser() {
					public boolean parse(SQLObject api, String version, String prefix) {
						if (version == null || version.isEmpty())
							return true;
						try {
							int ver = Integer.parseInt(version);
							if (ver > 4)
								return true;
							ResultSet channels = api.query("SELECT * FROM " + prefix + "twitch_users_old;",
									new Data[0]);
							while (channels.next()) {
								api.execute("INSERT INTO " + prefix + "users(uuid,channel,authorized) VALUES (?,?,?);",
										new Data[] { new Data(1, channels.getString("uuid")),
												new Data(12, channels.getString("channel")),
												new Data(16, Boolean.valueOf(channels.getBoolean("streamer"))) });
							}
							channels.close();
							ResultSet config = api.query("SELECT * FROM " + prefix + "twitch_settings_old;",
									new Data[0]);
							while (config.next()) {
								api.execute("UPDATE " + prefix
										+ "users SET configuration=?, subscriptions=?, bits=?, raids=?, channelpoints=?, followers=?, hypetrain=?, normal_messages=?, subscriber_messages=?, highlighted_messages=?, ritual_messages=? WHERE uuid=?;",
										new Data[] { new Data(12, config.getString("configuration")),
												new Data(16, config.getString("subscriptions")),
												new Data(16, config.getString("bits")),
												new Data(16, config.getString("raids")),
												new Data(16, config.getString("channelpoints")),
												new Data(16, config.getString("followers")),
												new Data(16, config.getString("hypetrain")),
												new Data(16, config.getString("normal_messages")),
												new Data(16, config.getString("subscriber_messages")),
												new Data(16, config.getString("highlighted_messages")),
												new Data(16, config.getString("ritual_messages")),
												new Data(12, config.getString("uuid")) });
							}
							config.close();
						} catch (Exception e) {}
						return true;
					}
				} }))
			return false;
		if (data.getType() == SQLType.MYSQL) {
			MySQLConnectionData mysqldata = (MySQLConnectionData) data;
			MessagesUtils.getMessageBuilder()
					.createMessage(new String[] { MessageString.applyPrefix("Connected successfully to "
							+ mysqldata.getHost() + ":" + mysqldata.getPort() + " for database "
							+ mysqldata.getDatabase() + " with username " + mysqldata.getUsername() + ".") })
					.sendMessage(new CommandSender[] { (CommandSender) Bukkit.getConsoleSender() });
		} else if (data.getType() == SQLType.SQLITE) {
			SQLiteConnectionData sqlitedata = (SQLiteConnectionData) data;
			MessagesUtils.getMessageBuilder()
					.createMessage(new String[] { MessageString
							.applyPrefix("Connected successfully to " + sqlitedata.toString() + " file.") })
					.sendMessage(new CommandSender[] { (CommandSender) Bukkit.getConsoleSender() });
		}
		return true;
	}

	private Database generateDatabase(Database database) {
		Table t;
		database.addTable((t = new Table(database, "users")).addColumns(
				(new Column(t, "uuid", "CHAR(36)", 1)).setPrimary(true).setNotNull(true),
				(new Column(t, "channel", "VARCHAR(25)", 1)).setUnique(true).setNotNull(true),
				(new Column(t, "channel_id", "VARCHAR(16)", 1)).setUnique(true),
				(new Column(t, "access_token", "VARCHAR(512)", 1)), (new Column(t, "refresh_token", "VARCHAR(512)", 1)),
				(new Column(t, "authorized", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "configuration", "VARCHAR(32)", 12)),
				(new Column(t, "subscriptions", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "bits", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "raids", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "channelpoints", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "followers", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "hypetrain", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "normal_messages", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "subscriber_messages", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "highlighted_messages", "BOOLEAN", 16, "0")).setNotNull(true),
				(new Column(t, "ritual_messages", "BOOLEAN", 16, "0")).setNotNull(true)));
		return database;
	}

	public boolean isRegistered(String channel) {
		try {
			Table t = this.database.getTable("users");
			ResultSet set = this.database.select(this.sql, Arrays.asList(t), new Condition(t.getColumn("channel"), new Data(Types.VARCHAR, channel.toLowerCase()), ConditionType.EQUAL));
			if (set.next())
				return true;
		} catch (Exception e) {}
		return false;
	}

	public List<Streamer> getStreamers() {
		List<Streamer> users = new ArrayList<>();
		try {
			Table t = this.database.getTable("users");
			ResultSet set = this.database.select(this.sql, Arrays.asList(t));
			while (set.next())
				try {
					users.add(getStreamer(set));
				} catch (Exception e) {}
		} catch (Exception e) {}
		return users;
	}

	public Streamer getStreamer(UUID uuid) {
		Streamer user = new Streamer(uuid);
		try {
			Table t = this.database.getTable("users");
			ResultSet set = this.database.select(this.sql, Arrays.asList(t),
					new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
			if (set.next())
				user = getStreamer(set);
		} catch (Exception e) {}
		return user;
	}

	private Streamer getStreamer(ResultSet set) throws Exception {
		UUID uuid = UUID.fromString(set.getString("uuid"));
		String accessToken = set.getString("access_token");
		String refreshToken = set.getString("refresh_token");
		Streamer user = new Streamer(uuid, set.getString("channel"), set.getString("channel_id"),
				accessToken != null && !accessToken.isEmpty() && refreshToken != null && !refreshToken.isEmpty()
						? new Token(accessToken, refreshToken, SettingString.TWITCH_API_CLIENTID.toString(),
								SettingString.TWITCH_API_CLIENTSECRET.toString())
						: null);
		user.getSettings().setIsAuthorizedStreamer(set.getBoolean("authorized"), false);
		user.getSettings().setConfiguration(set.getString("configuration"), false);
		user.getSettings().setDetectSubscriptions(set.getBoolean("subscriptions"), false);
		user.getSettings().setDetectBits(set.getBoolean("bits"), false);
		user.getSettings().setDetectRaids(set.getBoolean("raids"), false);
		user.getSettings().setDetectChannelPoints(set.getBoolean("channelpoints"), false);
		user.getSettings().setDetectFollowers(set.getBoolean("followers"), false);
		user.getSettings().setShowNormalMessages(set.getBoolean("normal_messages"), false);
		user.getSettings().setShowSubscriberMessages(set.getBoolean("subscriber_messages"), false);
		user.getSettings().setShowHighlightedMessages(set.getBoolean("highlighted_messages"), false);
		user.getSettings().setShowRitualMessages(set.getBoolean("ritual_messages"), false);
		return user;
	}

	public boolean registerStreamer(UUID uuid, String channel) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("channel"), new Data(12, channel.toLowerCase()));
		return this.database.insert(this.sql, t, values);
	}

	public boolean unregisterStreamer(UUID uuid) {
		Table t = this.database.getTable("users");
		return this.database.delete(this.sql, t,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setChannelId(UUID uuid, String id) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("channel_id"), new Data(12, id));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setToken(UUID uuid, Token token) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("access_token"), new Data(12, token.getAccessToken()));
		values.put(t.getColumn("refresh_token"), new Data(12, token.getRefreshToken()));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setIsAuthorizedStreamer(UUID uuid, boolean streamer) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("authorized"), new Data(16, Boolean.valueOf(streamer)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setTwitchConfiguration(UUID uuid, String config) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("configuration"), new Data(12, config));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setDetectTwitchSubscriptions(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("subscriptions"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setDetectTwitchBits(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("bits"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setDetectTwitchRaids(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("raids"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setDetectTwitchChannelPoints(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("channelpoints"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setDetectTwitchFollowers(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("followers"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setShowNormalMessages(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("normal_messages"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setShowSubscriberMessages(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("subscriber_messages"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setShowHighlightedMessages(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("highlighted_messages"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean setShowRitualMessages(UUID uuid, boolean value) {
		Table t = this.database.getTable("users");
		HashMap<Column, Data> values = new HashMap<>();
		values.put(t.getColumn("uuid"), new Data(1, uuid.toString()));
		values.put(t.getColumn("ritual_messages"), new Data(16, Boolean.valueOf(value)));
		return this.database.update(this.sql, t, values,
				new Condition(t.getColumn("uuid"), new Data(1, uuid.toString()), ConditionType.EQUAL));
	}

	public boolean isConnected() {
		return this.sql.isConnected();
	}

	public void close() {
		this.sql.close();
	}

	public boolean parseData(SQLObject dest, Database destdb) {
		boolean result = false;
		try {
			generateDatabase(destdb).updateTables(CakeTwitchAPI.getPlugin().getDescription().getVersion(), dest);
			destdb.setForeignKeyCheck(dest, false);
			for (Table table : destdb.getTables()) {
				if (!destdb.truncateTable(dest, table))
					throw new Exception();
			}
			for (Table table : this.database.getTables()) {
				Table desttable = destdb.getTable(table.getName());
				ResultSet set = this.database.select(this.sql, Arrays.asList(table));
				while (set.next()) {
					HashMap<Column, Data> map = new HashMap<>();
					for (Column column : table.getColumns())
						map.put(desttable.getColumn(column.getName()),
								new Data(column.getTypeId(), set.getObject(column.getName())));
					if (!destdb.insert(dest, desttable, map))
						throw new Exception();
				}
				set.close();
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destdb.setForeignKeyCheck(dest, true);
		}
		return result;
	}
}
