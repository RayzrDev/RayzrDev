package me.rayzr522.permissionitems.types.filters;

import me.rayzr522.permissionitems.types.ItemFilter;
import org.bukkit.inventory.ItemStack;

public class NameFilter implements ItemFilter {
    private final String name;

    public NameFilter(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public boolean isValidMatch(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(name);
    }
}
