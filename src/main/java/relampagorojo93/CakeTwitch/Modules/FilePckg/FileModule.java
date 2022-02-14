package relampagorojo93.CakeTwitch.Modules.FilePckg;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingBoolean;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingInt;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingList;
import relampagorojo93.CakeTwitch.Modules.FilePckg.Settings.SettingString;
import relampagorojo93.LibsCollection.SpigotPlugin.LoadOn;
import relampagorojo93.LibsCollection.SpigotPlugin.PluginModule;
import relampagorojo93.LibsCollection.YAMLLib.YAMLFile;
import relampagorojo93.LibsCollection.YAMLLib.YAMLUtils;
import relampagorojo93.LibsCollection.YAMLLib.Objects.Section;

public class FileModule extends PluginModule {

	public boolean load() {
		// File creation
		if (!PLUGIN_FOLDER.exists())
			PLUGIN_FOLDER.mkdir();
		if (!CONFIGS_FOLDER.exists()) {
			CONFIGS_FOLDER.mkdir();
			YAMLUtils.createYml(new File(CONFIGS_FOLDER.getPath() + "/Default.yml"),
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Default.yml"));
		}
		if (!SETTINGS_FILE.exists())
			YAMLUtils.createYml(SETTINGS_FILE,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Settings.yml"));

		if (!LANG_FILE.exists())
			YAMLUtils.createYml(LANG_FILE,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Lang.yml"));
		if (!EMOJIS_FILE.exists())
			YAMLUtils.createYml(EMOJIS_FILE,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Emojis.yml"));
		if (!DROPS_FILE.exists())
			YAMLUtils.createYml(DROPS_FILE,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Drops.yml"));
		if (!ACCESSTOKEN_FILE.exists())
			YAMLUtils.createYml(ACCESSTOKEN_FILE);
		// Config generation
		try {
			File newConfig = new File(SETTINGS_FILE.getPath() + ".new");
			YAMLUtils.createYml(newConfig,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Settings.yml"));
			HashMap<String, String> parsing = new HashMap<>();
			parsing.put("SSL.Callback-URL", "Twitch.EventSub.Callback-URL");
			parsing.put("SSL.Secret", "Twitch.EventSub.Secret");
			parsing.put("SSL.Private-key", "Twitch.EventSub.SSL.Private-key");
			parsing.put("SSL.Full-chain", "Twitch.EventSub.SSL.Full-chain");
			fileVersionCheck(SETTINGS_FILE, newConfig, parsing);
			YAMLFile settingsYaml = new YAMLFile(SETTINGS_FILE);
			for (SettingString str : SettingString.values()) {
				Section nw = settingsYaml.getSection(str.getPath());
				if (!str.getPath().equals(str.getOldPath())) {
					Section old = settingsYaml.getSection(str.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = settingsYaml.setSection(str.getPath(), old.getData());
						settingsYaml.removeSection(str.getOldPath());
					}
				}
				if (nw == null)
					nw = settingsYaml.setSection(str.getPath(), str.getDefaultContent());
				str.setContent(nw.getString(str.getDefaultContent()));
			}
			for (SettingBoolean bool : SettingBoolean.values()) {
				Section nw = settingsYaml.getSection(bool.getPath());
				if (!bool.getPath().equals(bool.getOldPath())) {
					Section old = settingsYaml.getSection(bool.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = settingsYaml.setSection(bool.getPath(), old.getData());
						settingsYaml.removeSection(bool.getOldPath());
					}
				}
				if (nw == null)
					nw = settingsYaml.setSection(bool.getPath(), bool.getDefaultContent());
				bool.setContent(nw.getBoolean(bool.getDefaultContent()));
			}
			for (SettingInt inte : SettingInt.values()) {
				Section nw = settingsYaml.getSection(inte.getPath());
				if (!inte.getPath().equals(inte.getOldPath())) {
					Section old = settingsYaml.getSection(inte.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = settingsYaml.setSection(inte.getPath(), old.getData());
						settingsYaml.removeSection(inte.getOldPath());
					}
				}
				if (nw == null)
					nw = settingsYaml.setSection(inte.getPath(), inte.getDefaultContent());
				inte.setContent(nw.getInteger(inte.getDefaultContent()));
			}
			for (SettingList list : SettingList.values()) {
				Section nw = settingsYaml.getSection(list.getPath());
				if (!list.getPath().equals(list.getOldPath())) {
					Section old = settingsYaml.getSection(list.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = settingsYaml.setSection(list.getPath(), old.getData());
						settingsYaml.removeSection(list.getOldPath());
					}
				}
				if (nw == null)
					nw = settingsYaml.setSection(list.getPath(), list.getDefaultContent());
				list.setContent(nw.getStringList(list.getDefaultContent()));
			}
			settingsYaml.saveYAML(SETTINGS_FILE);
			settingsYaml.reset();
			// Lang generation
			File newLang = new File(LANG_FILE.getPath() + ".new");
			YAMLUtils.createYml(newLang,
					CakeTwitchAPI.getPlugin().getResource("relampagorojo93/CakeTwitch/Resources/Lang.yml"));
			fileVersionCheck(LANG_FILE, newLang, new HashMap<>());
			YAMLFile langYml = new YAMLFile(LANG_FILE);
			for (MessageString ms : MessageString.values()) {
				Section nw = langYml.getSection(ms.getPath());
				if (!ms.getPath().equals(ms.getOldPath())) {
					Section old = langYml.getSection(ms.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = langYml.setSection(ms.getPath(), old.getData());
						langYml.removeSection(ms.getOldPath());
					}
				}
				if (nw == null)
					nw = langYml.setSection(ms.getPath(), ms.getDefaultContent());
				ms.setContent(nw.getString(ms.getDefaultContent()));
			}
			for (MessageList ml : MessageList.values()) {
				Section nw = langYml.getSection(ml.getPath());
				if (!ml.getPath().equals(ml.getOldPath())) {
					Section old = langYml.getSection(ml.getOldPath());
					if (old != null) {
						if (nw == null)
							nw = langYml.setSection(ml.getPath(), old.getData());
						langYml.removeSection(ml.getOldPath());
					}
				}
				if (nw == null)
					nw = langYml.setSection(ml.getPath(), ml.getDefaultContent());
				ml.setContent(nw.getStringList(ml.getDefaultContent()));
			}
			langYml.saveYAML(LANG_FILE);
			langYml.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public LoadOn loadOn() {
		return LoadOn.BEFORE_LOAD;
	}

	@Override
	public boolean optional() {
		return false;
	}

	@Override
	public boolean allowReload() {
		return true;
	}

	public final File PLUGIN_FOLDER = new File("plugins/CakeTwitch");
	public final File CONFIGS_FOLDER = new File(PLUGIN_FOLDER.getPath() + "/Configs");
	public final File LANG_FILE = new File(PLUGIN_FOLDER.getPath() + "/Lang.yml");
	public final File SETTINGS_FILE = new File(PLUGIN_FOLDER.getPath() + "/Settings.yml");
	public final File EMOJIS_FILE = new File(PLUGIN_FOLDER.getPath() + "/Emojis.yml");
	public final File DROPS_FILE = new File(PLUGIN_FOLDER.getPath() + "/Drops.yml");
	public final File PENDINGCOMMANDS_FILE = new File(PLUGIN_FOLDER.getPath() + "/PendingCommands.json");
	public final File ACCESSTOKEN_FILE = new File(PLUGIN_FOLDER.getPath() + "/AccessToken.txt");

	private void fileVersionCheck(File oldfile, File newfile, HashMap<String, String> oldtonew) {
		try {
			YAMLFile oldyaml = new YAMLFile(oldfile), newyaml = new YAMLFile(newfile);
			int oldversion = oldyaml.getSection("Version", new Section(0)).getInteger(),
					newversion = newyaml.getSection("Version", new Section(0)).getInteger();
			Section oldsection;
			if (oldversion < newversion) {
				for (Section newsection : newyaml.getSections())
					if ((oldsection = oldyaml.getSection(newsection.getPath())) != null)
						newsection.setData(oldsection.getData());
				for (Entry<String, String> entry : oldtonew.entrySet())
					if ((oldsection = oldyaml.getSection(entry.getKey())) != null)
						newyaml.setSection(entry.getValue(), oldsection.getData());
				newyaml.saveYAML(oldfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		newfile.delete();
	}
}
