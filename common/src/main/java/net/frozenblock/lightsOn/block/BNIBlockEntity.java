package net.frozenblock.lightsOn.block;

import net.frozenblock.lib.blockEntity.CoolBlockEntity;
import net.frozenblock.lightsOn.packet.BNIUpdatePacket;
import net.frozenblock.lightsOn.platform.Services;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.lightsOn.block.BNLBlockEntity.OUTPUT_KEY;

public class BNIBlockEntity extends CoolBlockEntity implements IAmNetworkInput {

    public static final String DATA_KEY = "ProjectData";
    @NotNull
    private CompoundTag data = new CompoundTag();

    @NotNull
    private final List<BlockPos> outputs = new ArrayList<>();

    public BNIBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.BLOCKNET_INTERFACE, pos, state);
    }

    @Override
    public void save(CompoundTag tag) {
        tag.put(DATA_KEY, data);
        BNLBlockEntity.saveBlockPosList(tag, outputs, OUTPUT_KEY);
    }

    @Override
    public void load(CompoundTag tag) {
        this.data = tag.contains(DATA_KEY) ? tag.getCompound(DATA_KEY) : new CompoundTag();
        BNLBlockEntity.loadBlockPosList(tag, outputs, OUTPUT_KEY);
    }

    public void askForSync() {
        if(level != null && level.isClientSide) {
            Services.PACKET_HELPER.send2S(new BNIUpdatePacket(this.getBlockPos()));
        } else if(level != null) this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
    }

    public @NotNull CompoundTag getData() {
        return data;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public boolean addOutput(BlockPos input) {
        if(input == this.getBlockPos())
            return false;
        if(!this.outputs.contains(input)) {
            this.outputs.add(input);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull List<BlockPos> getOutputs() {
        return outputs;
    }

    @Override
    public boolean removeOutput(BlockPos pos) {
        if(pos == this.getBlockPos())
            return false;
        if(this.outputs.contains(pos)) {
            this.outputs.remove(pos);
            return true;
        }
        return false;
    }
}
