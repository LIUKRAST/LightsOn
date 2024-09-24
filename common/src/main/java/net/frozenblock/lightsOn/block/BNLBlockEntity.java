package net.frozenblock.lightsOn.block;

import net.frozenblock.lib.blockEntity.CoolBlockEntity;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BNLBlockEntity extends CoolBlockEntity implements IAmNetworkInput, IAmNetworkOutput {

    public static final String OUTPUT_KEY = "NetworkOutputs";
    public static final String INPUT_KEY = "NetworkInputs";

    @NotNull private final List<BlockPos> outputs = new ArrayList<>();
    @NotNull private final List<BlockPos> inputs = new ArrayList<>();

    public BNLBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.BLOCKNET_LINK, pos, state);
    }

    @Override
    public void save(CompoundTag tag) {
        saveBlockPosList(tag, this.outputs, OUTPUT_KEY);
        saveBlockPosList(tag, this.inputs, INPUT_KEY);
    }

    public static void saveBlockPosList(CompoundTag tag, List<BlockPos> blockPosList, String key) {
        ListTag outList = new ListTag();
        for(BlockPos pos : blockPosList) {
            final IntArrayTag posTag = new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()});
            outList.add(posTag);
        }
        tag.put(key, outList);
    }

    @Override
    public void load(CompoundTag tag) {
        loadBlockPosList(tag, this.outputs, OUTPUT_KEY);
        loadBlockPosList(tag, this.inputs, INPUT_KEY);
    }

    public static void loadBlockPosList(CompoundTag tag, List<BlockPos> blockPosList, String key) {
        blockPosList.clear();
        if(tag.contains(key)) {
            for(Tag iat : tag.getList(key, Tag.TAG_INT_ARRAY)) {
                final var asList = ((IntArrayTag)iat);
                BlockPos pos = new BlockPos(
                        asList.get(0).getAsInt(),
                        asList.get(1).getAsInt(),
                        asList.get(2).getAsInt()
                );
                blockPosList.add(pos);
            }
        }
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

    @Override
    public boolean addInput(BlockPos input) {
        if(input == this.getBlockPos())
            return false;
        if(!this.inputs.contains(input)) {
            this.inputs.add(input);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull List<BlockPos> getInputs() {
        return inputs;
    }

    @Override
    public boolean removeInput(BlockPos pos) {
        if(pos == this.getBlockPos())
            return false;
        if(this.inputs.contains(pos)) {
            this.inputs.remove(pos);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
