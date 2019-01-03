package me.rayzr522.permissionitems.data;

import me.rayzr522.permissionitems.PermissionItems;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemManager {
    private List<PermissionItem> permissionItemList = new ArrayList<>();

    public void load(ConfigurationSection config) {
        permissionItemList = config.getKeys(false).stream()
                .filter(config::isConfigurationSection)
                .map(name -> {
                    try {
                        return PermissionItem.load(name, config.getConfigurationSection(name));
                    } catch (Exception e) {
                        PermissionItems.getInstance().getLogger().severe(String.format("Failed to load permission item '%s':", name));
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<PermissionItem> getPermissionItemList() {
        return permissionItemList;
    }
}
