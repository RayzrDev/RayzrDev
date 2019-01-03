package me.rayzr522.permissionitems.types.filters;

import me.rayzr522.permissionitems.types.ItemFilter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MaterialFilter implements ItemFilter {
    private final List<Material> materials;
    private final Mode mode;

    public MaterialFilter(List<Material> materials, Mode mode) {
        this.materials = materials;
        this.mode = mode;
    }

    @Override
    public String getName() {
        return "material";
    }

    @Override
    public boolean isValidMatch(ItemStack item) {
        return mode.isValid(materials, item.getType());
    }

    public enum Mode {
        WHITELIST(List::contains),
        BLACKLIST((list, item) -> !list.contains(item));

        private final BiPredicate<List<Material>, Material> comparator;

        Mode(BiPredicate<List<Material>, Material> comparator) {
            this.comparator = comparator;
        }

        public boolean isValid(List<Material> list, Material item) {
            return comparator.test(list, item);
        }

        public static Optional<Mode> getMode(String input) {
            try {
                return Optional.of(valueOf(input.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }
}
