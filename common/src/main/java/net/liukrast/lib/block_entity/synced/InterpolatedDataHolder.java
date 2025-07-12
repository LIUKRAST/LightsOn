package net.liukrast.lib.block_entity.synced;

import net.liukrast.lights.on.network.protocol.game.InterpolatedDataUpdatePacket;
import net.liukrast.lights.on.platform.Services;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.function.Function;

public class InterpolatedDataHolder {
    private final HashMap<String, AbstractInterpolatedData<?>> map = new HashMap<>();
    private final BlockEntity blockEntity;
    public InterpolatedDataHolder(BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public <T> AbstractInterpolatedData<T> register(String key, Function<BlockEntity, AbstractInterpolatedData<T>> factory) {
        var res = factory.apply(blockEntity);
        this.map.put(key, res);
        return res;
    }

    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        map.forEach((key, synchedData) -> {
            CompoundTag tag1 = new CompoundTag();
            synchedData.saveAdditional(tag1, provider);
            tag.put(key, tag1);
        });
    }

    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        map.forEach((key, synchedData) -> {
            CompoundTag tag1 = tag.getCompound(key);
            synchedData.loadAdditional(tag1, provider);
        });
    }

    public void set(CompoundTag tag, Level level) {
        if(!level.isClientSide) {
            for(Player player : level.players()) Services.PACKET_HELPER.send2Player((ServerPlayer) player, new InterpolatedDataUpdatePacket(blockEntity.getBlockPos(), tag));
        }
        map.forEach((key, interpolatedData) -> {
            if(!tag.contains(key)) return;
            interpolatedData.set(tag.getCompound(key));
        });
    }

    public AbstractInterpolatedData<?> get(String key) {
        return map.get(key);
    }
}
