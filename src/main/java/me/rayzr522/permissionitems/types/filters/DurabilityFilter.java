package me.rayzr522.permissionitems.types.filters;

import me.rayzr522.permissionitems.types.ItemFilter;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.BiPredicate;

public class DurabilityFilter implements ItemFilter {
    private final int target;
    private final Mode mode;

    public DurabilityFilter(int target, Mode mode) {
        this.target = target;
        this.mode = mode;
    }

    @Override
    public String getName() {
        return "durability";
    }

    @Override
    public boolean isValidMatch(ItemStack item) {
        return mode.isValid(item.getDurability(), target);
    }

    public enum Mode {
        EQUALS((input, target) -> input == target),
        LESS((input, target) -> input < target),
        GREATER((input, target) -> input > target);

        private final BiPredicate<Integer, Integer> comparator;

        Mode(BiPredicate<Integer, Integer> comparator) {
            this.comparator = comparator;
        }

        public boolean isValid(int input, int target) {
            return comparator.test(input, target);
        }

        public static Optional<Mode> getMode(String name) {
            try {
                return Optional.of(valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }
}
