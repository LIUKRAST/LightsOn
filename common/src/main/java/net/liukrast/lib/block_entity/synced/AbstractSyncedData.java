package net.liukrast.lib.block_entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public abstract class AbstractSyncedData<T> {
    private T value;
    private T oldValue;

    long animationStart;
    private Level level;
    int interpolation;

    public AbstractSyncedData(T defaultValue) {
        this.value = defaultValue;
        this.oldValue = defaultValue;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public T get() {
        return value;
    }

    protected void set(T value) {
        this.oldValue = this.value;
        this.value = value;
    }

    public abstract void save(CompoundTag tag);
    public abstract void load(CompoundTag tag);

    protected abstract T interpolatedValue(float ageInTicks, T oldValue, T newValue);

    public T getProgress(float partial) {
        float progress;
        int i = this.interpolation;
        if(i <= 0) progress = 1;
        else {
            float f = level == null ? 0 : level.getGameTime() - animationStart;
            float g = f + partial;
            progress = Math.clamp(getProgress(g, i), 0, 1);
        }
        return interpolatedValue(progress, oldValue, value);
    }

    private static float getProgress(float v, float e) {
        return (v - (float) 0.0) / (e - (float) 0.0);
    }

    protected static float interpolate(float min, float max, float x) {
        float size = max - min;
        float eq;
        if(x < 0.5) {
            eq = ease(x * 2)/2;
        } else {
            eq = (1 + (1 - ease(1-(x * 2 - 1))))/2;
        }
        return min + eq * size;
    }

    private static float ease(float x) {
        return (float) Math.pow(x, 2);
    }

    public int getInterpolation() {
        return interpolation;
    }
}
