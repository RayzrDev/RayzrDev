package me.rayzr522.permissionitems.config;

import me.rayzr522.permissionitems.data.PreventOptions;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigManager {
    private boolean bypassAllowed = true;
    private boolean messagesEnabled = true;
    private int messageCooldown = 0;
    private PreventOptions preventOptions;

    public void load(ConfigurationSection config) {
        bypassAllowed = config.getBoolean("allow-bypass", true);
        messagesEnabled = config.getBoolean("send-messages");
        messageCooldown = config.getInt("message-cooldown");
        preventOptions = PreventOptions.load(config.getConfigurationSection("prevent"));
    }

    public boolean isBypassAllowed() {
        return bypassAllowed;
    }

    public boolean isMessagesEnabled() {
        return messagesEnabled;
    }

    public int getMessageCooldown() {
        return messageCooldown;
    }

    public PreventOptions getPreventOptions() {
        return preventOptions;
    }
}
