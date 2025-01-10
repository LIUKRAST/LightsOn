package net.frozenblock.lib.blocknet;

import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

/**
 * Defines the Block Entity is configurable through BlockNet System.
 * Adding this to your block entity automatically makes it
 * open a {@link net.frozenblock.lightsOn.screen.BlockNetConfigScreen} when right-clicked with a {@link net.frozenblock.lightsOn.item.BlockNetWrench} in your off-hand.
 * Make your Block Entity also include {@link BlockNetPole}
 * if you want it to be configurable through a {@link net.frozenblock.lightsOn.screen.BlockNetInterfaceScreen}.
 * @since 1.0
 * @author LiukRast
 * */
public interface BlockNetConfigurable {
    /**
     * This method is required when implementing this interface and will be where you register your BlockNet setting,
     * so that they can be shown in the menus, and update their values.
     * See {@link net.frozenblock.lightsOn.block_entity.WorklightStandBlockEntity} for reference.
     * @param builder The BlockNet settings builder, which includes the possibility to add settings
     * @author LiukRast
     * */
    void defineSettings(BlockNetSettingBuilder builder);
    /**
     * The method involved in data update for your settings.
     * This will be invoked each time a BlockNet screen is sending new data for the block entity to update.
     * While during {@link #defineSettings(BlockNetSettingBuilder)} all your settings are defined using a key,
     * here they can be read using the same key.
     * If you're looking for how to read precise information,
     * all {@link BlockNetSetting} must extend a method
     * {@link BlockNetSetting#save(CompoundTag)} which shows how data is written in the tag.<br>
     * <strong>Very important</strong> An extra variable "duration" of type int is included in the update data for the interpolation.
     * @param tag The compound tag containing all updated information for the block entity
     * @since 1.0
     * @author LiukRast
     * */
    void updateData(CompoundTag tag);
    /**
     * @return The Block Entity's interpolation getter method. When null, the GUI will not include an "interpolate" button.
     * @since 1.0
     * @author LiukRast
     * */
    default Supplier<Integer> interpolationGetter() {
        return null;
    }
}
