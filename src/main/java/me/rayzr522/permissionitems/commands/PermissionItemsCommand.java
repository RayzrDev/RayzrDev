package me.rayzr522.permissionitems.commands;

import me.rayzr522.permissionitems.PermissionItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PermissionItemsCommand implements CommandExecutor {
    private static final String PERMISSION = "PermissionItems.admin";

    private final PermissionItems plugin;

    public PermissionItemsCommand(PermissionItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(plugin.tr("no-permission", PERMISSION));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.tr("command.usage"));
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("version")) {
            sender.sendMessage(plugin.tr("command.version", plugin.getDescription().getVersion()));
        } else if (sub.equals("reload")) {
            plugin.reload();
            sender.sendMessage(plugin.tr("command.reloaded"));
        } else {
            sender.sendMessage(plugin.tr("command.usage"));
        }

        return true;
    }
}
