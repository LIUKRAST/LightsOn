package net.liukrast.lights.on.project;

import io.netty.util.collection.LongObjectHashMap;
import net.minecraft.nbt.CompoundTag;

import java.util.Collections;

public class BlockNetProject extends LongObjectHashMap<BlockNetKeyframe> {
    private long duration;

    public CompoundTag saveAdditional() {
        CompoundTag tag = new CompoundTag();
        CompoundTag frames = new CompoundTag();
        forEach((time, frame) -> frames.put(time.toString(), frame.save()));
        tag.put("KeyFrames", frames);
        return tag;
    }

    public void loadAdditional(CompoundTag compound) {
        clear();
        var frames = compound.getCompound("KeyFrames");
        var strings = frames.getAllKeys();
        for(String key : strings) {
            var frame = new BlockNetKeyframe();
            put(Long.parseLong(key), frame);
            frame.load(frames.getCompound(key));
        }
        long max = Collections.max(keySet());
        duration = max + get(max).getMaxDuration();
    }

    public long getDuration() {
        return duration;
    }
}
