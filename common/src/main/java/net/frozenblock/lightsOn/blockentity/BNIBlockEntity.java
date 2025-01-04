package net.frozenblock.lightsOn.blockentity;

import net.frozenblock.lib.blockEntity.ClientSyncedBlockEntity;
import net.frozenblock.lightsOn.block.BNIBlock;
import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.packet.BNIUpdatePacket;
import net.frozenblock.lightsOn.platform.Services;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static net.frozenblock.lightsOn.block.BNIBlock.CONTAINS_FLOPPY;

public class BNIBlockEntity extends ClientSyncedBlockEntity implements BlockNetPole {

    public static final String DATA_KEY = "ProjectData";
    public static final String ITEM_KEY = "Item";

    @NotNull
    private CompoundTag data = new CompoundTag();
    private ItemStack stack = ItemStack.EMPTY;

    @NotNull
    private final Set<BlockPos> poles = new HashSet<>();

    public BNIBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.BLOCKNET_INTERFACE, pos, state);
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put(DATA_KEY, data);
        if(!stack.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            tag.put(ITEM_KEY, stack.save(registries, itemTag));
        }
        saveBlockPosList(tag, poles);
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider registries) {
        this.data = tag.contains(DATA_KEY) ? tag.getCompound(DATA_KEY) : new CompoundTag();
        if(tag.contains(ITEM_KEY)) {
            CompoundTag itemTag = tag.getCompound(ITEM_KEY);
            stack = ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY);
        }
        loadBlockPosList(tag, poles);
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
    public void addPole(BlockPos input) {
        this.poles.add(input);
    }

    @Override
    public @NotNull Set<BlockPos> getPoles() {
        return poles;
    }

    public void setItem(ItemStack stack) {
        ejectDisk();
        this.stack = stack;
        setChanged();
    }

    @Override
    public void removePole(BlockPos pos) {
        this.poles.remove(pos);
    }

    public void ejectDisk() {
        if(!this.stack.isEmpty()) {
            final var pos = this.getBlockPos().relative(getBlockState().getValue(BNIBlock.FACING)).getCenter();
            assert level != null;
            level.setBlock(getBlockPos(), getBlockState().setValue(CONTAINS_FLOPPY, true), 3);
            ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, this.stack);
            itemEntity.setPickUpDelay(10);
            level.addFreshEntity(itemEntity);
            this.stack = ItemStack.EMPTY;
            setChanged();
        }
    }
}
