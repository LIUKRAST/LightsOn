package net.frozenblock.lightsOn.block_entity;

import net.frozenblock.lib.block_entity.ClientSyncedBlockEntity;
import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

public class BNLBlockEntity extends ClientSyncedBlockEntity implements BlockNetPole {

    private final Set<BlockPos> poles = new HashSet<>();

    public BNLBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.BLOCKNET_LINK, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        saveBlockPosList(tag);
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        loadBlockPosList(tag);
    }

    @Override
    public void addPole(BlockPos input) {
        this.poles.add(input);
    }

    @Override
    public Set<BlockPos> getPoles() {
        return poles;
    }

    @Override
    public void removePole(BlockPos pos) {
        this.poles.remove(pos);
    }
}
