package net.liukrast.lib.block_entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SyncedDataHolder {
    private final HashMap<String, AbstractSyncedData<?>> map = new HashMap<>();

    public SyncedDataHolder() {}

    public <T> AbstractSyncedData<T> addSyncedData(String key, AbstractSyncedData<T> value) {
        this.map.put(key, value);
        return value;
    }

    public void setLevel(Level level) {
        map.forEach((key, sd) -> {
            sd.setLevel(level);
        });
    }

    public void save(CompoundTag tag) {
        map.forEach((key, synchedData) -> {
            CompoundTag tag1 = new CompoundTag();
            synchedData.save(tag1);
            tag1.putInt("Interpolation", synchedData.interpolation);
            tag1.putLong("AnimationStart", synchedData.animationStart);
            tag.put(key, tag1);
        });
    }

    public void load(CompoundTag tag) {
        map.forEach((key, synchedData) -> {
            CompoundTag tag1 = tag.getCompound(key);
            synchedData.load(tag1);
            synchedData.interpolation = tag1.getInt("Interpolation");
            synchedData.animationStart = tag1.getLong("AnimationStart");
        });
    }

    public void forEach(BiConsumer<String, AbstractSyncedData<?>> consumer) {
        map.forEach(consumer);
    }
}
