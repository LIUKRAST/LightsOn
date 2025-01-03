package net.frozenblock.lightsOn.blockentity;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WorklightStandBlockEntity extends ClientSyncedBlockEntity {
    public WorklightStandBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(RegisterBlockEntities.WORKLIGHT_STAND, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {

    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {

    }
}
