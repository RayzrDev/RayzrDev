package me.rayzr522.permissionitems;

import me.rayzr522.permissionitems.commands.PermissionItemsCommand;
import me.rayzr522.permissionitems.config.ConfigManager;
import me.rayzr522.permissionitems.config.Language;
import me.rayzr522.permissionitems.data.ItemManager;
import me.rayzr522.permissionitems.listeners.PlayerListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PermissionItems extends JavaPlugin {
    private static PermissionItems instance;

    private ConfigManager configManager = new ConfigManager();
    private Language language = new Language();
    private ItemManager itemManager = new ItemManager();

    public static PermissionItems getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("permissionitems").setExecutor(new PermissionItemsCommand(this));

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        reload();
    }

    @Override
    public void onDisable() {
//        save();

        instance = null;
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        configManager.load(getConfig());
        language.load(getConfig("messages.yml"));
        itemManager.load(getConfig("items.yml"));
    }

//    public void save() {
//
//    }

    private File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.pathSeparatorChar));
    }

    public YamlConfiguration getConfig(String path) {
        File file = getFile(path);
        if (!file.exists()) {
            saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public String trRaw(String key, Object... translationParameters) {
        return language.trRaw(key, translationParameters);
    }

    public String tr(String key, Object... translationParameters) {
        return language.tr(key, translationParameters);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Language getLanguage() {
        return language;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
