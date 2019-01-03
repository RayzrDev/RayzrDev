package me.rayzr522.permissionitems.types.filters;

import me.rayzr522.permissionitems.types.ItemFilter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class LoreFilter implements ItemFilter {
    private final String lore;
    private final int line;

    public LoreFilter(String lore, int line) {
        Objects.requireNonNull(lore);

        this.lore = ChatColor.translateAlternateColorCodes('&', lore);
        this.line = line;
    }

    @Override
    public String getName() {
        return "lore";
    }

    @Override
    public boolean isValidMatch(ItemStack item) {
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) {
            return false;
        }

        List<String> lines = item.getItemMeta().getLore();

        return line < 0
                ? lore.contains(lore)
                : lines.size() > line && lines.get(line).equals(lore);
    }
}
