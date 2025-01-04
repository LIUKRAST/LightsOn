package net.frozenblock.lib.blocknet;

import net.minecraft.nbt.CompoundTag;

public interface BlockNetConfigurable {

    void defineSettings(BlockNetSettingBuilder builder);

    void updateData(CompoundTag tag);

    default boolean includeInterpolation() {
        return true;
    }
}
