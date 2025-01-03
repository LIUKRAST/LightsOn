package net.frozenblock.lib.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class ClientSyncedBlockEntity extends BlockEntity {
    public ClientSyncedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        save(tag, registries);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        save(tag, registries);
        return tag;
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        load(tag, registries);
    }

    @SuppressWarnings("unused")
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        load(tag, lookupProvider);
    }

    public abstract void save(CompoundTag tag, HolderLookup.Provider registries);
    public abstract void load(CompoundTag tag, HolderLookup.Provider registries);
}
