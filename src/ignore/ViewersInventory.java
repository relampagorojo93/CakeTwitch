package relampagorojo93.CakeTwitch.Bukkit.Inventories.Twitch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

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
import relampagorojo93.CakeTwitch.StreamersPckg.Objects.Streamer;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.TasksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Item;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Slot;

public class ViewersInventory extends ChestInventoryWithStreamer {
	
	private int page = 1;
	
	public ViewersInventory(Player player, Streamer user) {
		
		super(player, user);
		setName("Viewers");
		setSize(45);
		setBackground(BaseInventory.getBase());
		
	}
	
	@Override
	public void updateContent() {

		ItemStack defaulthead = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
		
		List<Slot> users = new ArrayList<>();
		
		/*if (getStreamer().getSettings().isAuthorizedStreamer()) {
			List<String> viewers = getStreamer().getStats().getStreamViewers();
			HashMap<String, UUID> map = CakeTwitchAPI.getSQL().getUUIDsByTwitchChannels(viewers.toArray(new String[viewers.size()]));
			for (Entry<String, UUID> entry:map.entrySet()) {
				OfflinePlayer oplayer = entry.getValue() != null ? Bukkit.getOfflinePlayer(entry.getValue()) : null;
				ItemStack i = null;
				if (oplayer != null) {
					i = ItemStacksUtils.getPlayerHead(oplayer);
					ItemMeta im = i.getItemMeta();
					im.setDisplayName(oplayer.getName());
					i.setItemMeta(im);
				}
				else {
					i = defaulthead.clone();
					ItemMeta im = i.getItemMeta();
					im.setDisplayName(entry.getKey());
					i.setItemMeta(im);
				}
				users.add(new Item(i));
			}
		}*/
		
		//Total viewers item
		
		ItemStack i = new ItemStack(Material.PAPER);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&e&lCurrent viewers: &r&7" + users.size()).toString());
		i.setItemMeta(im);
		setSlot(getSize() - 5, new Item(i));
		
		int m = (int) ((((double) users.size()) / 21D) + 0.99D);
		if (m != 0 && page > m) page = m;
		for (int j = 0; j < 21; j++) {
			int slot = 10 + j + ((j / 7) * 2);
			int hl = (21 * (page - 1)) + j;
			if (hl >= 0 && hl < users.size()) this.setSlot(slot, users.get(hl));
			else this.setSlot(slot, NULL);
		}
		if (page > 1)
			this.setSlot(this.getSize() - 6, new Button(BaseInventory.getLeftArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {page-=1;updateInventory();}
			});
		else removeSlot(getSize() - 6);
		if (page < m)
			this.setSlot(this.getSize() - 4, new Button(BaseInventory.getRightArrow()) {
				@Override
				public void onClick(InventoryClickEvent e) {page+=1;updateInventory();}
			});
		else removeSlot(getSize() - 4);
		
		//Back item
		
		if (getPreviousHolder() != null) setSlot(getSize() - 9, new Button(BaseInventory.getReturnItem()) {
			@Override
			public void onClick(InventoryClickEvent e) { goToPreviousHolder(CakeTwitchAPI.getPlugin()); }
		});
		else removeSlot(getSize() - 9);
	}
	
	public static void updateInventoryEveryone(Streamer user) {
		TasksUtils.execute(CakeTwitchAPI.getPlugin(), () -> {
			for (Player pl:Bukkit.getOnlinePlayers()) {
				if (pl.getOpenInventory() != null) {
					Inventory inv = pl.getOpenInventory().getTopInventory();
					switch (inv.getType()) {
						case CHEST:
							if (inv != null && inv.getHolder() != null && inv.getHolder() instanceof ViewersInventory) {
								ViewersInventory tvi = (ViewersInventory) inv.getHolder();
								if (tvi.getStreamer().equals(user)) TasksUtils.executeOnAsync(CakeTwitchAPI.getPlugin(), () -> tvi.updateInventory());
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
