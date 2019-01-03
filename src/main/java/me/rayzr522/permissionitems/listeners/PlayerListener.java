package me.rayzr522.permissionitems.listeners;

import me.rayzr522.permissionitems.PermissionItems;
import me.rayzr522.permissionitems.data.PreventOptions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Predicate;

public class PlayerListener implements Listener {
    private final PermissionItems plugin;

    public PlayerListener(PermissionItems plugin) {
        this.plugin = plugin;
    }

    private boolean isAir(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    private boolean isBypassed(Player player) {
        return plugin.getConfigManager().isBypassAllowed() && player.getPlayer().hasPermission("PermissionItems.bypass");
    }

    private boolean isPreventedFor(Player player, ItemStack item, Predicate<PreventOptions> option) {
        if (isAir(item)) {
            return false;
        }

        if (isBypassed(player)) {
            return false;
        }

        PreventOptions defaults = plugin.getConfigManager().getPreventOptions();

        return plugin.getItemManager().getPermissionItemList().stream()
                .filter(permissionItem -> option.test(defaults) || permissionItem.getPreventOverrides().map(option::test).orElse(false))
                .flatMap(permissionItem -> permissionItem.getFilterList().stream())
                .anyMatch(filter -> filter.isValidMatch(item));
    }

    private Optional<ArmorType> matchEquipmentSlot(ItemStack item) {
        String typeName = item.getType().name();

        if (item.getType() == Material.PUMPKIN || typeName.endsWith("_HELMET")) {
            return Optional.of(ArmorType.HELMET);
        } else if (typeName.endsWith("_CHESTPLATE")) {
            return Optional.of(ArmorType.CHESTPLATE);
        } else if (typeName.endsWith("_LEGGINGS")) {
            return Optional.of(ArmorType.LEGGINGS);
        } else if (typeName.endsWith("_BOOTS")) {
            return Optional.of(ArmorType.BOOTS);
        } else {
            return Optional.empty();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (isPreventedFor(e.getPlayer(), e.getItem(), PreventOptions::isInteractionPrevented)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorEquip(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        boolean shift = e.getInventory().getType() == InventoryType.PLAYER && (e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.SHIFT_LEFT);

        ItemStack item = shift ? e.getCurrentItem() : e.getCursor();
        if (isAir(item)) {
            return;
        }

        Optional<ArmorType> armorType = matchEquipmentSlot(item);
        if (!armorType.isPresent()) {
            return;
        }

        ItemStack currentArmor = player.getInventory().getItem(armorType.get().getSlot());

        if ((!shift || isAir(currentArmor)) && isPreventedFor(player, item, PreventOptions::isEquippingPrevented)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHotBarSwitch(PlayerItemHeldEvent e) {
        if (isPreventedFor(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()), PreventOptions::isHotbarPrevented)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (isPreventedFor((Player) e.getWhoClicked(), e.getCurrentItem(), PreventOptions::isInteractionPrevented)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (isPreventedFor(e.getPlayer(), e.getItemDrop().getItemStack(), PreventOptions::isDroppingPrevented)) {
            e.setCancelled(true);
        }
    }

    private enum ArmorType {
        HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8);

        private final int slot;

        ArmorType(int slot) {
            this.slot = slot;
        }

        public int getSlot() {
            return slot;
        }
    }
}
