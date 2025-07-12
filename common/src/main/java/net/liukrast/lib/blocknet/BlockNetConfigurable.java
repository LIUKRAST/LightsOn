package net.liukrast.lib.blocknet;

import net.liukrast.lights.on.client.gui.screens.BlockNetConfigScreen;
import net.liukrast.lights.on.client.gui.screens.BlockNetScreen;
import net.liukrast.lights.on.world.item.BlockNetWrench;
import net.liukrast.lights.on.world.level.block.entity.WorklightStand;
import net.minecraft.nbt.CompoundTag;

/**
 * Defines the Block Entity is configurable through BlockNet System.
 * Adding this to your block entity automatically makes it
 * open a {@link BlockNetConfigScreen} when right-clicked with a {@link BlockNetWrench} in your off-hand.
 * Make your Block Entity also include {@link BlockNetPole}
 * if you want it to be configurable through a {@link BlockNetScreen}.
 * @since 1.0
 * @author LiukRast
 * */
public interface BlockNetConfigurable {
    BlockNetSettings getSettings();
    void updateSettings(CompoundTag tag);
}
