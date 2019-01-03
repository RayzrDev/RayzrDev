package me.rayzr522.permissionitems.config;

import me.rayzr522.permissionitems.data.PreventOptions;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigManager {
    private boolean bypassAllowed = true;
    private PreventOptions preventOptions;

    public void load(ConfigurationSection config) {
        bypassAllowed = config.getBoolean("allow-bypass", true);
        preventOptions = PreventOptions.load(config.getConfigurationSection("prevent"));
    }

    public boolean isBypassAllowed() {
        return bypassAllowed;
    }

    public PreventOptions getPreventOptions() {
        return preventOptions;
    }
}
