package net.liukrast.lib.block_entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class ColorSyncedData extends AbstractSyncedData<Integer> {
    public ColorSyncedData(Level level) {
        super(16777216, level);
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("Value", this.get());
        tag.putInt("OldValue", this.getProgress(0));
    }

    @Override
    public void load(CompoundTag tag) {
        this.set(tag.getInt("Value"));
    }

    @Override
    public Integer interpolatedValue(float age, Integer oldValue, Integer newValue) {
        float r = ((newValue>>16)&0xFF);
        float g = ((newValue>>8)&0xFF);
        float b = ((newValue)&0xFF);
        float or = ((oldValue>>16)&0xFF);
        float og = ((oldValue>>8)&0xFF);
        float ob = ((oldValue)&0xFF);
        int fr = (int) interpolate(or, r, age);
        int fg = (int) interpolate(og, g, age);
        int fb = (int) interpolate(ob, b, age);
        return (fr << 16) | (fg << 8) | fb;
    }
}
