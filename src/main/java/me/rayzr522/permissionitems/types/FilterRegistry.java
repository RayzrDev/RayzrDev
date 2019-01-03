package me.rayzr522.permissionitems.types;

import me.rayzr522.permissionitems.types.filters.NameFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class FilterRegistry {
    private static final Map<String, Function<Map<String, Object>, ItemFilter>> filters = new HashMap<>();

    static {
        registerItemFilter("name", config -> new NameFilter(Objects.toString(config.get("value"))));
    }

    public static void registerItemFilter(String name, Function<Map<String, Object>, ItemFilter> generator) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(generator);

        if (filters.containsKey(name)) {
            throw new IllegalArgumentException(String.format("An item filter with the name '%s' has already been added!", name));
        }

        filters.put(name, generator);
    }

    public static Optional<ItemFilter> getItemFilter(String name, Map<String, Object> config) {
        return Optional.ofNullable(filters.get(name))
                .map(generator -> generator.apply(config));
    }

    public static ItemFilter parse(Map<String, Object> config) {
        if (!config.containsKey("type") || !config.containsKey("value")) {
            throw new IllegalArgumentException("Item filter must have both a type and a value");
        }

        String type = Objects.toString(config.get("type"));

        return getItemFilter(type, config).orElseThrow(() -> new IllegalArgumentException(String.format("Invalid item filter type '%s'", type)));
    }
}
