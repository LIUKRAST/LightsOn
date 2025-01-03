package net.frozenblock.lightsOn.block;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lightsOn.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class BNLBlockEntity extends ClientSyncedBlockEntity implements BlockNetPole {

    private final List<BlockPos> poles = new ArrayList<>();

    public BNLBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.BLOCKNET_LINK, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        saveBlockPosList(tag, this.poles, POLE_KEY);
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        loadBlockPosList(tag, this.poles, POLE_KEY);
    }

    @Override
    public void addPole(BlockPos input) {
        if(input == this.getBlockPos())
            return;
        if(!this.poles.contains(input)) {
            this.poles.add(input);
        }
    }

    @Override
    public List<BlockPos> getPoles() {
        return poles;
    }

    @Override
    public void removePole(BlockPos pos) {
        if (pos == this.getBlockPos())
            return;
        if (this.poles.contains(pos)) {
            this.poles.remove(pos);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
