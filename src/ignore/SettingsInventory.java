package relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.BaseInventory;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Abstracts.ChestInventoryWithStreamer;
import relampagorojo93.CakeTwitch.ConfigPckg.Configurations.Configuration;
import relampagorojo93.CakeTwitch.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;

public class SettingsInventory extends ChestInventoryWithStreamer {

	// -------------------------------------------------//
	// Slot ID:
	// Configuration - (37)
	// Event Information - (38)
	// Chat Information - (39)
	// Channel Points - (40)
	// Subscriptions - (41)
	// Raids - (42)
	// Bits - (43)
	// Followers - (44)
	// Normal Messages - (45)
	// Subscriber Messages - (46)
	// Highlighted Messages - (47)
	// Ritual Messages - (48)
	// -------------------------------------------------//

	public SettingsInventory(Player player, Streamer user) {
		super(player, user);

		OfflinePlayer pl = Bukkit.getOfflinePlayer(getStreamer().getUniqueID());
		
		setAllowStorageExchange(false);
		setName(MessagesUtils.getMessageBuilder().createMessage("&r&e" + (pl != null ? pl.getName() : "???") + " &8&l> &r&aSettings").toString());
		setBackground(BaseInventory.getBase());
		setSize(36);

		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");
		
		ItemStack i = null;
		ItemMeta im = null;

		// Configuration item

		i = new ItemStack(Material.BOOK);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lConfiguration").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Select your configuration",
				"&7to manage your Twitch events", "&7and execute commands").getStrings());
		i.setItemMeta(im);
		setSlot(37, new Item(i));

		// Event information item

		i = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFjNjNkOWI5ZmQ4NzQyZWFlYjA0YzY5MjE3MmNiOWRhNDM3ODE2OThhNTc1Y2RhYmUxYzA0ZGYxMmMzZiJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEvent Settings").toString());
		i.setItemMeta(im);
		setSlot(38, new Item(i));

		// Chat information item

		i = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjQ4Y2UxY2YxOGFmMDVhNTc2ZDYwODEyMzAwMWI3OTFmZWRiNjIyOTExZWY4ZDM4YTMyMGRhM2JjYmY2ZmQyMCJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lChat Settings").toString());
		i.setItemMeta(im);
		setSlot(39, new Item(i));

		// Channel points event item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lChannel Points").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Enable or disable the trigger",
				"&7for &eChannel Points &7to track", "&7claimed rewards").getStrings());
		i.setItemMeta(im);
		setSlot(40, new Item(i));

		// Subscriptions event item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lSubscriptions").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Enable or disable the trigger",
				"&7for &eSubscriptions &7to track", "&7received or given subscriptions").getStrings());
		i.setItemMeta(im);
		setSlot(41, new Item(i));

		// Raids event item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lRaids").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Enable or disable the trigger",
				"&7for &eRaids &7to track", "&7raids on the channel").getStrings());
		i.setItemMeta(im);
		setSlot(42, new Item(i));

		// Bits event item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lBits").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Enable or disable the trigger", "&7for &eBits &7to track", "&7received bits")
				.getStrings());
		i.setItemMeta(im);
		setSlot(43, new Item(i));

		// Followers event item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lFollowers").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Enable or disable the trigger",
				"&7for &eFollowers &7to track", "&7new followers on your channel").getStrings());
		i.setItemMeta(im);
		setSlot(44, new Item(i));

		// Normal chat message item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lNormal Messages").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Enable or disable &eNormal", "&eMessages &7from being shown").getStrings());
		i.setItemMeta(im);
		setSlot(45, new Item(i));

		// Subscriber chat message item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lSubscriber Messages").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Enable or disable &eSubscriber", "&eMessages &7from being shown").getStrings());
		i.setItemMeta(im);
		setSlot(46, new Item(i));

		// Highlighted chat message item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lHighlighted Messages").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Enable or disable &eHighlighted", "&eMessages &7from being shown")
				.getStrings());
		i.setItemMeta(im);
		setSlot(47, new Item(i));

		// Ritual chat message item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lRitual Messages").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Enable or disable &eRitual", "&eMessages &7from being shown").getStrings());
		i.setItemMeta(im);
		setSlot(48, new Item(i));

		// Viewers item

		/*i = ItemStacksUtils.setSkin(defaulthead.clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY2OTJmOTljYzZkNzgyNDIzMDQxMTA1NTM1ODk0ODQyOThiMmU0YTAyMzNiNzY3NTNmODg4ZTIwN2VmNSJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lViewers").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Check the list of &eviewers", "&7on your twitch channel,", "&7streaming or not")
				.getStrings());
		i.setItemMeta(im);

		setSlot(49, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				new TwitchViewersInventory(getPlayer(), getStreamer()).setPreviousHolder(getHolder())
						.openInventory(CakeTwitchAPI.getPlugin());
			}
		});*/

		// Empty

		setSlot(24, NULL);
		setSlot(25, NULL);
	}

	@Override
	public void updateContent() {
		String cs = getStreamer().getSettings().getConfiguration();
		Configuration co = CakeTwitchAPI.getConfig().getConfig(cs);

		boolean dcp = getStreamer().getSettings().detectChannelPoints();
		boolean ds = getStreamer().getSettings().detectSubscriptions();
		boolean dr = getStreamer().getSettings().detectRaids();
		boolean db = getStreamer().getSettings().detectBits();
		boolean df = getStreamer().getSettings().detectFollowers();

		boolean snm = getStreamer().getSettings().showNormalMessages();
		boolean ssm = getStreamer().getSettings().showSubscriberMessages();
		boolean shm = getStreamer().getSettings().showHighlightedMessages();
		boolean srm = getStreamer().getSettings().showRitualMessages();
		
		ItemStack i = null;
		ItemMeta im = null;
		List<String> lore = null;

		// Bot button

		/*ItemStack i = ItemStacksUtils.setSkin(getSlot(55).getItemStack().clone(), status == Status.RUNNING
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE3MmZkMzNjZmJiMzVkZWVlZDNhZTU4ZjNlM2VkYjIzNWFhMmFmZjE1NzdiYWE4Mzk4ODlmZmU5MWQ2OWYyOSJ9fX0="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyMTZkMWY4MzA4NzJiOTgxNjUzMGFkYzFkNjZjNzA1ZTY4MGU3ZDZmMGRmMmE2ZDVjMmUzMGMwY2I0MTE5MSJ9fX0=");
		ItemMeta im = i.getItemMeta();
		List<String> lore = im.getLore();
		List<String> extralore = new ArrayList<>();
		if (lore == null)
			lore = new ArrayList<>();
		extralore.add("&0");
		extralore.add("&7Status: " + (status == Status.RUNNING ? "&a" : "&c") + status.name());
		//extralore.add("&7Enabled advanced events:"
				//+ (getStreamer().getSettings().getEnabledEvents().size() == 0 ? " &c[]" : ""));
		//if (getStreamer().getSettings().getEnabledEvents().size() != 0)
			//for (Event event : getStreamer().getSettings().getEnabledEvents().keySet())
				//extralore.add("  &8- &a" + event.toString());
		extralore.add("&7Last log: &f"
				+ (lastlog != null ? (lastlog.length() > 32 ? lastlog.substring(0, 32) + "..." : lastlog) : ""));
		lore.addAll(MessagesUtils.getMessageBuilder().createMessage(extralore.toArray(new String[extralore.size()]))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(13, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				switch (e.getClick()) {
				case LEFT:
					if (!getUser().isTwitchStreamer())
						return;
					TwitchStreamerData data = getStreamer().getSettings();
					if (status == Status.RUNNING)
						data.stopBot(true);
					else
						data.startBot(true);
					break;
				case RIGHT:
					MessagesUtils.getMessageBuilder().createMessage(
							new TextReplacement[] { new TextReplacement("{message}",
									() -> getStreamer().getSettings().getLastLog(),
									new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
											getStreamer().getSettings().getLastLog()),
									new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											MessagesUtils.getMessageBuilder().createMessage("Click me!").toString())) },
							true, "{message}").sendMessage(e.getWhoClicked());
					break;
				default:
					break;
				}
			}
		});*/

		// Event settings information

		i = getSlot(38).getItemStack().clone();
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Channel points: " + (dcp ? "&aEnabled" : "&cDisabled"),
						"&7Subscriptions: " + (ds ? "&aEnabled" : "&cDisabled"),
						"&7Raids: " + (dr ? "&aEnabled" : "&cDisabled"), "&7Bits: " + (db ? "&aEnabled" : "&cDisabled"),
						"&7Followers: " + (df ? "&aEnabled" : "&cDisabled"))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(2, new Item(i));

		// Chat settings information

		i = getSlot(39).getItemStack().clone();
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Normal messages: " + (snm ? "&aEnabled" : "&cDisabled"),
						"&7Subscriber messages: " + (ssm ? "&aEnabled" : "&cDisabled"),
						"&7Highlighted messages: " + (shm ? "&aEnabled" : "&cDisabled"),
						"&7Ritual messages: " + (srm ? "&aEnabled" : "&cDisabled"))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(6, new Item(i));

		// Configuration item

		i = getSlot(37).getItemStack().clone();
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Actual config: " + (co != null ? "&a" + cs : "&c!" + (cs != null ? cs : "")))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(31, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				new SettingsConfigInventory(getPlayer(), getStreamer()).setPreviousHolder(getHolder())
						.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		// Channel points button

		i = ItemStacksUtils.setSkin(getSlot(40).getItemStack().clone(), dcp
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (dcp ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(10, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setDetectChannelPoints(!getStreamer().getSettings().detectChannelPoints());
				updateInventory();
			}
		});

		// Subscriptions button

		i = ItemStacksUtils.setSkin(getSlot(41).getItemStack().clone(), ds
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (ds ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(11, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setDetectSubscriptions(!getStreamer().getSettings().detectSubscriptions());
				updateInventory();
			}
		});

		// Raids button

		i = ItemStacksUtils.setSkin(getSlot(42).getItemStack().clone(), dr
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (dr ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(12, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings().setDetectRaids(!getStreamer().getSettings().detectRaids());
				updateInventory();
			}
		});

		// Bits button

		i = ItemStacksUtils.setSkin(getSlot(43).getItemStack().clone(), db
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (db ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(19, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings().setDetectBits(!getStreamer().getSettings().detectBits());
				updateInventory();
			}
		});

		// Followers button

		i = ItemStacksUtils.setSkin(getSlot(44).getItemStack().clone(), df
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (df ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(20, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setDetectFollowers(!getStreamer().getSettings().detectFollowers());
				updateInventory();
			}
		});

		// Hype train button

		setSlot(21, NULL);

		// Normal messages

		i = ItemStacksUtils.setSkin(getSlot(45).getItemStack().clone(), snm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (snm ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(14, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setShowNormalMessages(!getStreamer().getSettings().showNormalMessages());
				updateInventory();
			}
		});

		// Subscriber messages

		i = ItemStacksUtils.setSkin(getSlot(46).getItemStack().clone(), ssm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (ssm ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(15, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setShowSubscriberMessages(!getStreamer().getSettings().showSubscriberMessages());
				updateInventory();
			}
		});

		// Highlighted messages

		i = ItemStacksUtils.setSkin(getSlot(47).getItemStack().clone(), shm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (shm ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(16, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setShowHighlightedMessages(!getStreamer().getSettings().showHighlightedMessages());
				updateInventory();
			}
		});

		// Ritual messages

		i = ItemStacksUtils.setSkin(getSlot(48).getItemStack().clone(), srm
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ=="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		if (lore == null)
			lore = new ArrayList<>();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (srm ? "&aEnabled" : "&cDisabled")).getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(23, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (!getStreamer().getSettings().isAuthorizedStreamer())
					return;
				getStreamer().getSettings()
						.setShowRitualMessages(!getStreamer().getSettings().showRitualMessages());
				updateInventory();
			}
		});

		// Back item

		if (getPreviousHolder() != null)
			setSlot(27, new Button(BaseInventory.getReturnItem()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					goToPreviousHolder(CakeTwitchAPI.getPlugin());
				}
			});
		else
			removeSlot(27);
	}

	public static void updateInventoryEveryone(Streamer user) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null
								&& inv.getHolder() instanceof SettingsInventory) {
							SettingsInventory tsi = (SettingsInventory) inv.getHolder();
							if (tsi.getStreamer().equals(user))
								TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(),
										() -> tsi.updateInventory());
						}
						break;
					default:
						break;
					}
				}
			}
		});
	}

}