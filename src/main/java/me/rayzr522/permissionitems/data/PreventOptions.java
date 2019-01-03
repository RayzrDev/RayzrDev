package me.rayzr522.permissionitems.data;

import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PreventOptions {
    private static Optional<Boolean> booleanOptional(ConfigurationSection config, String key) {
        if (config.isBoolean(key)) {
            return Optional.of(config.getBoolean(key));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Boolean> interactionPrevented;
    private Optional<Boolean> equippingPrevented;
    private Optional<Boolean> hotbarPrevented;
    private Optional<Boolean> inventoryPrevented;
    private Optional<Boolean> droppingPrevented;

    public PreventOptions(Optional<Boolean> interactionPrevented, Optional<Boolean> equippingPrevented, Optional<Boolean> hotbarPrevented, Optional<Boolean> inventoryPrevented, Optional<Boolean> droppingPrevented) {
        this.interactionPrevented = interactionPrevented;
        this.equippingPrevented = equippingPrevented;
        this.hotbarPrevented = hotbarPrevented;
        this.inventoryPrevented = inventoryPrevented;
        this.droppingPrevented = droppingPrevented;
    }

    public Optional<Boolean> isInteractionPrevented() {
        return interactionPrevented;
    }

    public Optional<Boolean> isEquippingPrevented() {
        return equippingPrevented;
    }

    public Optional<Boolean> isHotbarPrevented() {
        return hotbarPrevented;
    }

    public Optional<Boolean> isInventoryPrevented() {
        return inventoryPrevented;
    }

    public Optional<Boolean> isDroppingPrevented() {
        return droppingPrevented;
    }

    public static PreventOptions load(ConfigurationSection config) {
        return new PreventOptions(
                booleanOptional(config, "interaction"),
                booleanOptional(config, "equipping"),
                booleanOptional(config, "hotbar"),
                booleanOptional(config, "inventory"),
                booleanOptional(config, "dropping")
        );
    }
}
