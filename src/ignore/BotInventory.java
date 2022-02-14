package relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Debug.DebugInventory;
import relampagorojo93.CakeTwitch.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.IRCBot.Status;
import relampagorojo93.LibsCollection.SpigotDebug.DebugData;
import relampagorojo93.LibsCollection.SpigotDebug.DebugType;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;

public class BotInventory extends ChestInventoryWithStreamer {

	// -------------------------------------------------//
	// Slot ID:
	// Bot - (28)
	// Debug - (29)
	// -------------------------------------------------//

	public BotInventory(Player player, Streamer user) {
		super(player, user);

		OfflinePlayer pl = Bukkit.getOfflinePlayer(getStreamer().getUniqueID());

		setAllowStorageExchange(false);
		setName(MessagesUtils.getMessageBuilder()
				.createMessage("&r&e" + (pl != null ? pl.getName() : "???") + " &8&l> &r&aBot").toString());
		setBackground(BaseInventory.getBase());
		setSize(27);

		ItemStack defaulthead = ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD");

		ItemStack i = null;
		ItemMeta im = null;

		// Bot item

		i = defaulthead.clone();
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lTwitch Bot").toString());
		im.setLore(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Start or stop the bot and", "&7check its status and last", "&7messages")
				.getStrings());
		i.setItemMeta(im);
		setSlot(28, new Item(i));

		// Debug item

		i = new ItemStack(Material.BOOK);
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lRegistry").toString());
		im.setLore(MessagesUtils.getMessageBuilder().createMessage("&0", "&7Get a registry with all",
				"&7interactions from the bot and", "&7the twitch community", "&0", "&eClick me to check all the logs")
				.getStrings());
		i.setItemMeta(im);
		setSlot(29, new Item(i));
	}

	@Override
	public void updateContent() {
		Status status = getStreamer().getBot().getStatus();
		DebugData lastdata = getStreamer().getBot().getDebugController().getDebugData(0);

		ItemStack i = null;
		ItemMeta im = null;
		List<String> lore = null;

		// Lime panes

		i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
		im = i.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		i.setItemMeta(im);
		for (int slot = 11; slot < 16; slot++)
			setSlot(slot, new Item(i));

		// Bot button

		i = ItemStacksUtils.setSkin(getSlot(28).getItemStack().clone(), status == Status.RUNNING
				? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE3MmZkMzNjZmJiMzVkZWVlZDNhZTU4ZjNlM2VkYjIzNWFhMmFmZjE1NzdiYWE4Mzk4ODlmZmU5MWQ2OWYyOSJ9fX0="
				: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyMTZkMWY4MzA4NzJiOTgxNjUzMGFkYzFkNjZjNzA1ZTY4MGU3ZDZmMGRmMmE2ZDVjMmUzMGMwY2I0MTE5MSJ9fX0=");
		im = i.getItemMeta();
		lore = im.getLore();
		lore.addAll(MessagesUtils.getMessageBuilder()
				.createMessage("&0", "&7Status: " + (status == Status.RUNNING ? "&a" : "&c") + status.name())
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(12, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				switch (e.getClick()) {
				case LEFT:
					if (!getStreamer().getSettings().isAuthorizedStreamer())
						return;
					if (status == Status.RUNNING)
						TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> getStreamer().getBot().stopBot());
					else
						TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> getStreamer().getBot().startBot());
					break;
				default:
					break;
				}
			}
		});

		// Debug button

		i = getSlot(29).getItemStack().clone();
		im = i.getItemMeta();
		lore = im.getLore();
		lore.addAll(MessagesUtils.getMessageBuilder().createMessage("&0",
				"&7Last log: " + (lastdata != null ? (lastdata.getDebugType() == DebugType.LOG
						? "&a"
						: lastdata.getDebugType() == DebugType.ALERT ? "&e" : "&a")
						+ (lastdata.getMessage().length() < 33 ? lastdata.getMessage()
								: lastdata.getMessage().substring(0, 32) + "...")
						: ""))
				.getStrings());
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(14, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				new DebugInventory(
						MessagesUtils.getMessageBuilder().createMessage("&r&aBot &8&l> &r&cDebug").toString(),
						getPlayer(), getStreamer().getBot().getDebugController()).setPreviousHolder(getHolder())
								.openInventory(CakeTwitchAPI.getPlugin());
			}
		});

		// Back item

		if (getPreviousHolder() != null)
			setSlot(18, new Button(BaseInventory.getReturnItem()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					goToPreviousHolder(CakeTwitchAPI.getPlugin());
				}
			});
		else
			removeSlot(18);

	}

	public static void updateInventoryEveryone(Streamer user) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof BotInventory) {
							BotInventory tsi = (BotInventory) inv.getHolder();
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