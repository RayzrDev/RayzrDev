package me.rayzr522.permissionitems.types;

import me.rayzr522.permissionitems.PermissionItems;
import me.rayzr522.permissionitems.types.filters.DurabilityFilter;
import me.rayzr522.permissionitems.types.filters.LoreFilter;
import me.rayzr522.permissionitems.types.filters.MaterialFilter;
import me.rayzr522.permissionitems.types.filters.NameFilter;
import org.bukkit.Material;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class FilterRegistry {
    private static final Map<String, Function<Map<String, Object>, ItemFilter>> filters = new HashMap<>();

    static {
        registerItemFilter("name", config -> new NameFilter(Objects.toString(config.get("value"))));
        registerItemFilter("lore", config -> new LoreFilter(
                Objects.toString(config.get("value")),
                (Integer) config.getOrDefault("line", -1)
        ));
        registerItemFilter("durability", config -> new DurabilityFilter(
                Integer.parseInt(String.valueOf(config.get("value"))),
                DurabilityFilter.Mode.getMode(String.valueOf(config.getOrDefault("mode", "equals")))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid durability filter mode"))
        ));
        registerItemFilter("material", config -> new MaterialFilter(
                (config.get("value") instanceof List
                        ? (List<String>) config.get("value")
                        : Collections.singletonList(Objects.toString(config.get("value")))).stream()
                        .map(material -> {
                            try {
                                return Material.valueOf(material.toUpperCase());
                            } catch (IllegalArgumentException e) {
                                PermissionItems.getInstance().getLogger().warning(String.format("Invalid material type for material filter: %s", material));
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()),
                MaterialFilter.Mode.getMode(Objects.toString(config.getOrDefault("mode", "whitelist")))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid material filter mode"))
        ));
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
