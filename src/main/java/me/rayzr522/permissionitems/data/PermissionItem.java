package me.rayzr522.permissionitems.data;

import me.rayzr522.permissionitems.PermissionItems;
import me.rayzr522.permissionitems.types.FilterRegistry;
import me.rayzr522.permissionitems.types.ItemFilter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PermissionItem {
    private String name;
    private String permission;
    private PreventOptions preventOverrides;
    private List<ItemFilter> filterList;

    public PermissionItem(String name, String permission, PreventOptions preventOverrides, List<ItemFilter> filterList) {
        this.name = name;
        this.permission = permission;
        this.preventOverrides = preventOverrides;
        this.filterList = filterList;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public Optional<PreventOptions> getPreventOverrides() {
        return Optional.ofNullable(preventOverrides);
    }

    public List<ItemFilter> getFilterList() {
        return filterList;
    }

    @SuppressWarnings("unchecked")
    public static PermissionItem load(String name, ConfigurationSection config) {
        String permission = config.getString("permission");
        List<Map<?, ?>> filters = config.getMapList("filters");
        PreventOptions preventOverrides = Optional.ofNullable(config.getConfigurationSection("prevent"))
                .map(PreventOptions::load)
                .orElse(null);

        Objects.requireNonNull(permission, "no permission provided!");
        Objects.requireNonNull(filters, "no filters provided!");

        if (filters.size() < 1) {
            throw new IllegalArgumentException("filters list is empty!");
        }

        return new PermissionItem(
                name,
                permission,
                preventOverrides,
                filters.stream()
                        .map(filterConfig -> {
                            try {
                                return FilterRegistry.parse((Map<String, Object>) filterConfig);
                            } catch (Exception e) {
                                PermissionItems.getInstance().getLogger().severe(String.format("Failed to load filter for permission item '%s': %s", name, e));
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }
}
