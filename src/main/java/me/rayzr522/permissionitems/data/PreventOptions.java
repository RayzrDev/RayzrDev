package me.rayzr522.permissionitems.data;

import org.bukkit.configuration.ConfigurationSection;

public class PreventOptions {
    private boolean interactionPrevented;
    private boolean hotbarPrevented;
    private boolean inventoryPrevented;
    private boolean droppingPrevented;

    public PreventOptions(boolean interactionPrevented, boolean hotbarPrevented, boolean inventoryPrevented, boolean droppingPrevented) {
        this.interactionPrevented = interactionPrevented;
        this.hotbarPrevented = hotbarPrevented;
        this.inventoryPrevented = inventoryPrevented;
        this.droppingPrevented = droppingPrevented;
    }

    public boolean isInteractionPrevented() {
        return interactionPrevented;
    }

    public boolean isHotbarPrevented() {
        return hotbarPrevented;
    }

    public boolean isInventoryPrevented() {
        return inventoryPrevented;
    }

    public boolean isDroppingPrevented() {
        return droppingPrevented;
    }

    public static PreventOptions load(ConfigurationSection config) {
        return new PreventOptions(
                config.getBoolean("interaction", true),
                config.getBoolean("hotbar", false),
                config.getBoolean("inventory", false),
                config.getBoolean("dropping", false)
        );
    }
}
