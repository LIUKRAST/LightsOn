package net.liukrast.lib.block_entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class FloatSyncedData extends AbstractSyncedData<Float> {
    public FloatSyncedData() {
        super(0.0f);
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putFloat("Value", get());
    }

    @Override
    public void load(CompoundTag tag) {
        this.set(tag.getFloat("Value"));
    }

    @Override
    protected Float interpolatedValue(float ageInTicks, Float oldValue, Float newValue) {
        return interpolate(oldValue, newValue, ageInTicks);
    }
}
