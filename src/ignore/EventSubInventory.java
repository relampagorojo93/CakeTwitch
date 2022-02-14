package relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.BaseInventory;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Abstracts.ChestInventoryWithStreamer;
import relampagorojo93.CakeTwitch.MiscModules.TwitchEventSubModule;
import relampagorojo93.CakeTwitch.MiscModules.TwitchEventSubModule.Event;
import relampagorojo93.CakeTwitch.MiscModules.TwitchEventSubModule.SubscriptionResponse;
import relampagorojo93.CakeTwitch.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;

public class EventSubInventory extends ChestInventoryWithStreamer {

	// -------------------------------------------------//
	// Slot ID:
	// Default head - (28)
	// -------------------------------------------------//
	
	private int page = 1;

	public EventSubInventory(Player player, Streamer user) {
		super(player, user);

		OfflinePlayer pl = Bukkit.getOfflinePlayer(getStreamer().getUniqueID());

		setAllowStorageExchange(false);
		setName(MessagesUtils.getMessageBuilder()
				.createMessage("&r&e" + (pl != null ? pl.getName() : "???") + " &8&l> &r&aEventSub").toString());
		setBackground(BaseInventory.getBase());
		setSize(45);
		
		// Default head item
		
		setSlot(46, new Item(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD")));
		
		ItemStack i = null;
		ItemMeta im = null;

		// Lime panes

		i = ItemStacksUtils.getItemStack("STAINED_GLASS_PANE", (short) 5, "LIME_STAINED_GLASS_PANE");
		im = i.getItemMeta();
		im.setDisplayName(ChatColor.BLACK.toString());
		i.setItemMeta(im);
		for (int slot = 11; slot < 16; slot++)
			setSlot(slot, new Item(i));

		// Event information item
		
		i = ItemStacksUtils.setSkin(getSlot(46).getItemStack().clone(),
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lEventSub status").toString());
		i.setItemMeta(im);
		setSlot(47, new Item(i));
		
		
	}

	@Override
	public void updateContent() {

		ItemStack i = null;
		ItemMeta im = null;
		List<String> lore = null;

		// EventSub information

		i = getSlot(47).getItemStack().clone();
		im = i.getItemMeta();
		lore = new ArrayList<>();
		lore.add(MessagesUtils.getMessageBuilder().createMessage("&0").toString());
		for (Event event:Event.values()) {
			SubscriptionResponse response = getStreamer().getEventSub().getResponse(event);
			lore.add(MessagesUtils.getMessageBuilder()
				.createMessage(
						"&7" + event.toString() +": " + (response == SubscriptionResponse.ACCEPTED ? "&a" : (response == SubscriptionResponse.REJECTED || response == SubscriptionResponse.REQUESTING ? "&c" : "&e")) + response.toString()
						)
				.toString());
		}
		im.setLore(lore);
		i.setItemMeta(im);
		setSlot(13, new Item(i));
		
		//EventSub status buttons
		
		for (int n = 0; n < TwitchEventSubModule.Event.values().length && n < 6; n++) {
			int selectedevent = n + ((page - 1) * 3), slot = 21 + n + (6 * (n/3));
			if (selectedevent < TwitchEventSubModule.Event.values().length) {
				TwitchEventSubModule.Event event = TwitchEventSubModule.Event.values()[selectedevent];
				TwitchEventSubModule.SubscriptionResponse response = getStreamer().getEventSub().getResponse(event);
				long lastrequest = getStreamer().getEventSub().getLastRequest(event), requiredtime = getStreamer().getEventSub().getRequiredTime(event);
				
				i = ItemStacksUtils.setSkin(getSlot(46).getItemStack().clone(), response == SubscriptionResponse.ACCEPTED || response == SubscriptionResponse.DUPLICATED ?
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM5ZTYwMWVkOTE5OGRiYjM0YzUxZGRmMzIzOTI5ZjAxYTVmOTU4YWIxMTEzM2UzZTA0MDdiNjk4MzkzYjNmIn19fQ==" :
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0=");
				im = i.getItemMeta();
				im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage((response == SubscriptionResponse.ACCEPTED || response == SubscriptionResponse.DUPLICATED ? "&a" : "&c") + event.toString()).toString());
				im.setLore(MessagesUtils.getMessageBuilder().createMessage(
						"&0",
						"&7Response: " + (response == SubscriptionResponse.ACCEPTED ? "&a" : (response == SubscriptionResponse.REJECTED || response == SubscriptionResponse.REQUESTING ? "&c" : "&e")) + response.toString(),
						"&7Required time: &e" + requiredtime + " ms",
						"&7Last request: &e" + (lastrequest == 0L ? "Never" : (lastrequest > 60 ? (lastrequest > 3600 ? (int) (lastrequest / 3600) + " hour/s ago" :  (int) (lastrequest / 60) + " minute/s ago") : (int) lastrequest + " second/s ago"))
						).getStrings());
				i.setItemMeta(im);
				
				setSlot(slot, new Button(i) {
					@Override
					public void onClick(InventoryClickEvent e) {
						if (System.currentTimeMillis() - getStreamer().getEventSub().getLastRequest(event) < 1000*60*5)
							return;
						TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> getStreamer().getEventSub().requestSubscription(event));
					}
				});
			}
			else setSlot(slot, NULL);
		}
		
		// Left button
		
		if (page < (int) (((double) TwitchEventSubModule.Event.values().length)/3D + 0.99) - 1) {
			Button button = new Button(BaseInventory.getDownArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page++; updateInventory();
				}
			};
			setSlot(29, button);
			setSlot(33, button);
		}
		else {
			removeSlot(29);
			removeSlot(33);
		}
		if (page > 1) {
			Button button = new Button(BaseInventory.getUpArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {
					page--; updateInventory();
				}
			};
			setSlot(20, button);
			setSlot(24, button);
		}
		else {
			removeSlot(20);
			removeSlot(24);
		}

		if (getPreviousHolder() != null) this.setSlot(this.getSize() - 9, new Button(BaseInventory.getReturnItem()) {
			@Override
			public void onClick(InventoryClickEvent e) { goToPreviousHolder(CakeTwitchAPI.getPlugin()); }
		});
		else removeSlot(this.getSize() - 9);

	}

	public static void updateInventoryEveryone(Streamer user) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
					case CHEST:
						if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof EventSubInventory) {
							EventSubInventory tsi = (EventSubInventory) inv.getHolder();
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
