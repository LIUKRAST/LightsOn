package net.frozenblock.lightsOn.blockentity;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WorklightStandBlockEntity extends ClientSyncedBlockEntity {

    private float yaw;
    private float right_pitch,left_pitch;
    private float height;

    public WorklightStandBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(RegisterBlockEntities.WORKLIGHT_STAND, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putFloat("Yaw", yaw);
        tag.putFloat("RightPitch", right_pitch);
        tag.putFloat("LeftPitch", left_pitch);
        tag.putFloat("Height", height);
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        yaw = tag.getFloat("Yaw");
        right_pitch = tag.getFloat("RightPitch");
        left_pitch = tag.getFloat("LeftPitch");
        height = tag.getFloat("Height");
    }
}
