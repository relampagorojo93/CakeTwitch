package relampagorojo93.CakeTwitch.Bukkit.Inventories.General;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import relampagorojo93.CakeTwitch.API.CakeTwitchAPI;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.BaseInventory;
import relampagorojo93.CakeTwitch.Bukkit.Inventories.Abstracts.PluginChestInventory;
import relampagorojo93.LibsCollection.SpigotMessages.MessagesUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.ItemStacksUtils;
import relampagorojo93.LibsCollection.Utils.Bukkit.Inventories.Objects.Button;

public class ConfirmationInventory extends PluginChestInventory {
		
	public ConfirmationInventory(Player player, Action action) {
		super(player);
		setName(MessagesUtils.getMessageBuilder().createMessage("&7&lAre you sure?").toString());
		setSize(9);
		setBackground(BaseInventory.getBase());
		ItemStack i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0=");
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&a&lYes!").toString());
		i.setItemMeta(im);
		setSlot(3, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				if (action != null)
					action.execute();
				goToPreviousHolder(CakeTwitchAPI.getPlugin());
			}
		});
		i = ItemStacksUtils.setSkin(ItemStacksUtils.getItemStack("SKULL_ITEM", (short) 3, "PLAYER_HEAD"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==");
		im = i.getItemMeta();
		im.setDisplayName(MessagesUtils.getMessageBuilder().createMessage("&c&lNo!").toString());
		i.setItemMeta(im);
		setSlot(5, new Button(i) {
			@Override
			public void onClick(InventoryClickEvent e) {
				goToPreviousHolder(CakeTwitchAPI.getPlugin());
			}
		});
	}
	
	public static interface Action {
		void execute();
	}
	
}
