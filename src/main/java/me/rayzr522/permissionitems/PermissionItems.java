package me.rayzr522.permissionitems;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PermissionItems extends JavaPlugin {
    @Override
    public void onEnable() {
        reload();
    }

    @Override
    public void onDisable() {
        save();
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();
    }

    public void save() {

    }

    private File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.pathSeparatorChar));
    }

    private YamlConfiguration getConfig(String path) {
        File file = getFile(path);
        if (!file.exists()) {
            saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
