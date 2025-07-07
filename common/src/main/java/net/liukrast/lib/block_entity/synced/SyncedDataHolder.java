package net.liukrast.lib.block_entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.function.Function;

public class SyncedDataHolder {
    private final HashMap<String, AbstractSyncedData<?>> map = new HashMap<>();
    private final BlockEntity blockEntity;

    public SyncedDataHolder(BlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public <T> AbstractSyncedData<T> addSyncedData(String key, Function<Level, AbstractSyncedData<T>> value) {
        var f = value.apply(blockEntity.getLevel());
        this.map.put(key, f);
        return f;
    }

    public void save(CompoundTag tag) {
        map.forEach((key, syncedData) -> {
            CompoundTag tag1 = new CompoundTag();
            syncedData.save(tag1);
            tag.putInt("Interpolation", syncedData.interpolation);
            tag.putLong("AnimationStart", syncedData.animationStart);
            tag.put(key, tag1);
        });
    }

    public void load(CompoundTag tag) {
        map.forEach((key, synchedData) -> {
            CompoundTag tag1 = tag.getCompound(key);
            synchedData.load(tag1);
            synchedData.interpolation = tag.getInt("Interpolation");
            synchedData.animationStart = tag.getLong("AnimationStart");
        });
    }
}
