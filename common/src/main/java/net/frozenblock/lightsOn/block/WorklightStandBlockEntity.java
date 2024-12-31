package net.frozenblock.lightsOn.block;

import net.frozenblock.lib.blockEntity.CoolBlockEntity;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorklightStandBlockEntity extends CoolBlockEntity implements IAmNetworkOutput {
    public WorklightStandBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(RegisterBlockEntities.WORKLIGHT_STAND, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {

    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {

    }

    @Override
    public boolean addInput(BlockPos input) {
        return false;
    }

    @Override
    public List<BlockPos> getInputs() {
        return List.of();
    }

    @Override
    public boolean removeInput(BlockPos pos) {
        return false;
    }
}
