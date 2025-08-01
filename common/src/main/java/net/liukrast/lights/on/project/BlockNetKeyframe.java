package net.liukrast.lights.on.project;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

public class BlockNetKeyframe extends HashMap<BlockPos, CompoundTag> {

    private int maxDuration;

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        forEach((pos, tag1) -> tag.put(pos.toShortString(), tag1));
        return tag;
    }

    public void load(CompoundTag compound) {
        maxDuration = 0;
        clear();
        for(String key : compound.getAllKeys()) {
            String[] p = key.split(", ");
            BlockPos pos = new BlockPos(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            put(pos, compound.getCompound(key));
            int d = compound.getCompound(key).getInt("Interpolation");
            if(maxDuration < d) maxDuration = d;
        }
    }

    public int getMaxDuration() {
        return maxDuration;
    }
}
