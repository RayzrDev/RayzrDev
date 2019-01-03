package me.rayzr522.permissionitems.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Language {
    private Map<String, String> messages = new HashMap<>();

    private static String getBaseKeyFor(String path) {
        if (path.indexOf('.') < 0) {
            return "";
        } else {
            return path.substring(0, path.lastIndexOf('.'));
        }
    }

    public void load(ConfigurationSection config) {
        messages = config.getKeys(true).stream()
                .filter(key -> config.isString(key) || config.isList(key))
                .collect(Collectors.toMap(
                        key -> key,
                        key -> config.isList(key)
                                ? config.getList(key).stream().map(Objects::toString).collect(Collectors.joining("\n"))
                                : config.getString(key)
                ));
    }

    public String trRaw(String key, Object... translationParameters) {
        return ChatColor.translateAlternateColorCodes('&', String.format(messages.getOrDefault(key, key), translationParameters));
    }

    private String getPrefixFor(String key) {
        return ChatColor.translateAlternateColorCodes(
                '&',
                messages.getOrDefault(String.format("%s.prefix", getBaseKeyFor(key)), messages.getOrDefault("prefix", ""))
                        + messages.getOrDefault(String.format("%s.prefix-addon", getBaseKeyFor(key)), "")
        );
    }

    public String tr(String key, Object... translationParameters) {
        return String.format("%s%s", getPrefixFor(key), trRaw(key, translationParameters));
    }
}
