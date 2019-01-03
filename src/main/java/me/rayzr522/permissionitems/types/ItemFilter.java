package me.rayzr522.permissionitems.types;

import org.bukkit.inventory.ItemStack;

public interface ItemFilter {
    /**
     * @return The name of this filter type.
     */
    String getName();

    /**
     * @param item The {@link ItemStack} to check.
     * @return Whether or not the item matches this filter.
     */
    boolean isValidMatch(ItemStack item);
}
