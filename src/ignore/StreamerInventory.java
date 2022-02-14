package relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEvents;
import relampagorojo93.CakeTwitch.Bukkit.Events.ChatEventsObjects.SpecifyChannelInputData;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Abstracts.ChestInventoryWithStreamer;
import relampagorojo93.CakeTwitch.FilePckg.Messages.MessageString;
import relampagorojo93.CakeTwitch.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Slot;

public class StreamerInventory extends ChestInventoryWithStreamer {

	// -------------------------------------------------//
	// Slot ID:
	// Revoke Streamer Rights - (54)
	// Grant Streamer Rights - (55)
	// Settings inventory - (56)
	// Join chat - (57)
	// Leave chat - (58)
	// Register - (59)
	// Chat event currently - (60)
	// Streamer head - (61)
	// -------------------------------------------------//

	private static ItemStack[] base;

	public static ItemStack[] getBase() {
		return base;
	}

	static {
		ItemStack it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 7, "GRAY_STAINED_GLASS_PANE");
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		base = new ItemStack[54];
		for (int i = 0; i < base.length; i++)
			base[i] = it;

		it = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
		im = it.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		it.setItemMeta(im);
		for (int i = 28; i < 35; i++)
			base[i] = it;
	}

	public StreamerInventory(Player player, Streamer user) {
		super(player, user);

		OfflinePlayer pl = Bukkit.getOfflinePlayer(getStreamer().getUniqueID());

		setAllowStorageExchange(false);
		setName(MessagesUtils.getMessageBuilder().createMessage("&r&e" + (pl != null ? pl.getName() : "???"))
				.toString());
		setBackground(getBase());
		setSize(45);

		ItemStack i = null;
		ItemMeta im = null;

		// Revoke streamer rights item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eRevoke streamer rights").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7This user is a streamer now! But", "&7you can revoke his streamer rights at",
						"&7any time and make him a normal user", "&0", "&eClick to revoke his streamer rights")
				.getStrings());
		i.setItemMeta(im);
		setSlot(54, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				getStreamer().getSettings().setIsAuthorizedStreamer(false, true);
				StreamerInventory.updateInventoryEveryone(getStreamer());
			}
		});

		// Grant streamer rights item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eGrant streamer rights").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Here you have a normal user.", "&7You can grant streamer rights to allow",
						"&7him to interact with your MineCraft", "&7server throught his events and",
						"&7his commands' configuration", "&0", "&eClick to grant him streamer rights")
				.getStrings());
		i.setItemMeta(im);
		setSlot(55, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				getStreamer().getSettings().setIsAuthorizedStreamer(true, true);
				StreamerInventory.updateInventoryEveryone(getStreamer());
			}
		});

		// Settings item

		i = new ItemStack(Material.IRON_PICKAXE);
		im = i.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eSettings").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Use this inventory to edit your", "&7streamer settings and enable or",
						"&7disable interactions during your stream.", "&0", "&aClick to open the inventory")
				.getStrings());
		i.setItemMeta(im);
		setSlot(56, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
			}
		});

		// Join chat item

		i = new ItemStack(Material.PAPER);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eJoin the chat").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&aClick to visualize the", "&astreamer's chat").getStrings());
		i.setItemMeta(im);
		setSlot(57, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (getStreamer().getSettings().isAuthorizedStreamer())
					getStreamer().getViewersController().addViewer(getPlayer().getUniqueId());
			}
		});

		// Leave chat item

		i = new ItemStack(Material.PAPER);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eJoin the chat").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&aClick to stop visualizing", "&athe streamer's chat").getStrings());
		i.setItemMeta(im);
		setSlot(58, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (getStreamer().getSettings().isAuthorizedStreamer())
					getStreamer().getViewersController().removeViewer(getPlayer().getUniqueId());
			}
		});

		// Register item

		i = new ItemStack(Material.BOOK);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&6Register your acount").toString());
		im.setLore(MessagesUtils
				.getMessageBuilder().createMessage("&0", "&7Connect your account to link and",
						"&7use it with your MineCraft account", "&0", "&eClick to connect your Twitch account")
				.getStrings());
		i.setItemMeta(im);
		setSlot(59, new Button(i) {

			@Override
			public void onClick(InventoryClickEvent e) {
				if (getStreamer().getSettings().getChannel() == null
						&& !ChatEvents.isRegistered(getPlayer().getUniqueId())) {
					closeInventory(CakeTwitchAPI.getPlugin());
					MessagesUtils.getMessageBuilder().sendTitle(getPlayer(), true, "REGISTRATION",
							"Type the username of your channel\n Type 'cancel' to stop the registration", 20, 100, 20);
					ChatEvents.register(new SpecifyChannelInputData(getPlayer()));
				}
			}

		});

		// Chat event currently item

		i = new ItemStack(Material.BOOK);
		im = i.getItemMeta();
		im.setDisplayName(
				MessagesUtils.getMessageBuilder().createMessage("&cFirst finish your chat interaction!").toString());
		i.setItemMeta(im);
		setSlot(60, new Item(i));

		// EventSub item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eEvent Subscriptions").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Subscribe to advanced events", "&7on Twitch like Hype", "&7Trains or follows ", "&0",
						"&7Use this inventory to manage your", "&eevent subscriptions&7 and",
						"&7check if they are active.", "&0", "&aClick to open the inventory")
				.getStrings());
		i.setItemMeta(im);
		setSlot(61, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (getStreamer().getSettings().isAuthorizedStreamer()
						&& CakeTwitchAPI.getTwitchEventSub() != null)
					new EventSubInventory(getPlayer(), getStreamer()).setPreviousHolder(getHolder())
							.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		// Bot item

		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE3MmZkMzNjZmJiMzVkZWVlZDNhZTU4ZjNlM2VkYjIzNWFhMmFmZjE1NzdiYWE4Mzk4ODlmZmU5MWQ2OWYyOSJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&eBot").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Start or stop the bot and", "&7check its status and last", "&7messages", "&0",
						"&7Use this inventory to manager your", "&7Twitch bot and control the current",
						"&7interactions happening on your stream.", "&0", "&aClick to open the inventory")
				.getStrings());
		i.setItemMeta(im);
		setSlot(62, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (getStreamer().getSettings().isAuthorizedStreamer())
					new BotInventory(getPlayer(), getStreamer()).setPreviousHolder(getHolder())
							.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		i = new ItemStack(Material.BOOK);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&cCancel registration").toString());
		i.setItemMeta(im);
		setSlot(63, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				getStreamer().getSettings().setRegisterChannel(null);
				getStreamer().getBot().stopBot();
				MessagesUtils.getMessageBuilder().createMessage(MessageString.applyPrefix(MessageString.CANCELLED))
						.sendMessage(getPlayer());
				updateInventoryEveryone(getStreamer());
			}
		});

	}

	@Override
	public void updateContent() {
		boolean same = getPlayer().getUniqueId().compareTo(getStreamer().getUniqueID()) == 0;

		ItemStack i = null;
		ItemMeta im = null;

		// Information head

		i = getStreamer().getPlayerHead();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&5&lTwitch Account: &r&7[&e"
				+ (getStreamer().getSettings().getChannel() != null ? getStreamer().getSettings().getChannel() : "?")
				+ "&7]").toString());
		/*if (getStreamer().getSettings().getChannel() != null)
			im.setLore(MessagesUtils.getMessageBuilder()
					.createMessage("&0", "&7Spectators: &f" + getStreamer().getStats().getSpectators(),
							"&7Followers: &f" + getStreamer().getStats().getFollowers(),
							"&7Last follower: &f" + getStreamer().getStats().getLastFollower(),
							"&7Is streaming: &f" + (getStreamer().getStats().isStreaming() ? "&aYes" : "&cNo"))
					.getStrings());*/
		i.setItemMeta(im);
		setSlot(13, new Item(i));

		List<Slot> slots = new ArrayList<>();

		if (getStreamer().getSettings().getChannel() == null) {
			if (same) {
				if (!ChatEvents.isRegistered(getPlayer().getUniqueId())) {
					if (getStreamer().getSettings().getRegisterChannel() != null)
						slots.add(getSlot(63));
					else
						slots.add(getSlot(59));
				}
				else
					slots.add(getSlot(60));
			}
		} else {

			// Grant|Revoke rights

			if (getStreamer().getSettings().isAuthorizedStreamer()
					&& getPlayer().hasPermission("CakeTwitch.ToTwitchUser"))
				setSlot(16, getSlot(54));
			else if (!getStreamer().getSettings().isAuthorizedStreamer()
					&& getPlayer().hasPermission("CakeTwitch.ToTwitchStreamer"))
				setSlot(16, getSlot(55));
			else
				removeSlot(16);

			// Items for authorized streamers

			if (getStreamer().getSettings().isAuthorizedStreamer()) {
				if (same) {
					slots.add(getSlot(56));
					slots.add(getSlot(62));
					if (CakeTwitchAPI.getTwitchEventSub() != null)
						slots.add(getSlot(61));
				} else if (getStreamer().getViewersController().isViewing(getPlayer().getUniqueId()))
					slots.add(getSlot(57));
				else
					slots.add(getSlot(58));
			}

		}

		int[] slotsn = new int[] {};
		int[] emptyn = new int[] { 28, 29, 30, 31, 32, 33, 34 };

		switch (slots.size()) {
		case 1:
			slotsn = new int[] { 31 };
			emptyn = new int[] { 28, 29, 30, 32, 33, 34 };
			break;
		case 2:
			slotsn = new int[] { 29, 33 };
			emptyn = new int[] { 28, 30, 31, 32, 34 };
			break;
		case 3:
			slotsn = new int[] { 29, 31, 33 };
			emptyn = new int[] { 28, 30, 32, 34 };
			break;
		case 4:
			slotsn = new int[] { 28, 30, 32, 34 };
			emptyn = new int[] { 29, 31, 33 };
			break;
		case 5:
			slotsn = new int[] { 28, 29, 31, 33, 34 };
			emptyn = new int[] { 30, 32 };
			break;
		case 6:
			slotsn = new int[] { 28, 29, 30, 32, 33, 34 };
			emptyn = new int[] { 31 };
			break;
		case 7:
			slotsn = new int[] { 28, 29, 30, 31, 32, 33, 34 };
			emptyn = new int[] {};
			break;
		default:
			break;
		}

		for (int slot : slotsn)
			setSlot(slot, slots.remove(0));
		for (int empty : emptyn)
			removeSlot(empty);

	}

	public static Inventory getEzInventory(Player player) {
		return new StreamerInventory(player, CakeTwitchAPI.getStreamers().getStreamer(player.getUniqueId()))
				.getInventory();
	}

	public static void updateInventoryEveryone(Streamer user) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof StreamerInventory) {
							StreamerInventory ui = (StreamerInventory) inv.getHolder();
							if (ui.getStreamer().equals(user))
								TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(),
										() -> ui.updateInventory());
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