package net.liukrast.lib.block_entity.synced;

import com.mojang.serialization.Codec;
import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.network.protocol.game.BlockNetSettingUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.InterpolatedDataUpdatePacket;
import net.liukrast.lights.on.platform.Services;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.lang.reflect.Method;

public abstract class AbstractInterpolatedData<T> {

    private T value;
    @OnlyIn(Dist.CLIENT)
    private T oldValue;
    private int interpolation;
    private final BlockEntity blockEntity;
    private long animationStart;

    public AbstractInterpolatedData(T defaultValue, BlockEntity blockEntity) {
        this.value = defaultValue;
        this.oldValue = defaultValue;
        this.blockEntity = blockEntity;
    }

    public T get() {
        return value;
    }

    public void set(T value, int interpolation) {

        if(blockEntity.getLevel() == null) return;
        if(blockEntity.getLevel().isClientSide) {
            this.oldValue = getProgress(0);
            this.animationStart = blockEntity.getLevel().getGameTime();
        }
        this.interpolation = interpolation;
        this.value = value;
    }

    public void set(CompoundTag tag) {
        var res = codec().parse(NbtOps.INSTANCE, tag.get("Value")).resultOrPartial(s -> LightsOnConstants.dataError(this, "set(...)", s, tag));
        res.ifPresent(t -> set(t, tag.getInt("Interpolation")));
    }

    public void saveAdditional(CompoundTag tag, @SuppressWarnings("unused") HolderLookup.Provider provider) {
        var tag1 = codec().encodeStart(NbtOps.INSTANCE, get()).resultOrPartial(s -> LightsOnConstants.dataError(this, "saveAdditional(...)", s, tag));
        tag1.ifPresent(tag2 -> tag.put("Value", tag2));
        tag.putInt("Interpolation", interpolation);
    }

    public void loadAdditional(CompoundTag tag, @SuppressWarnings("unused") HolderLookup.Provider provider) {
        var tag1 = codec().parse(NbtOps.INSTANCE, tag.get("Value")).resultOrPartial(s -> LightsOnConstants.dataError(this, "loadAdditional(...)", s, tag));
        tag1.ifPresent(t -> value = t);
        interpolation = tag.getInt("Interpolation");
    }

    public abstract Codec<T> codec();

    protected abstract T interpolatedValue(float ageInTicks, T oldValue, T newValue);

    public T getProgress(float partial) {
        float progress;
        int i = this.interpolation;
        if(i <= 0) progress = 1;
        else {
            float f = blockEntity.getLevel() == null ? 0 : blockEntity.getLevel().getGameTime() - animationStart;
            float g = f + partial;
            progress = Math.clamp(getProgress(g, i), 0, 1);
        }
        return interpolatedValue(progress, oldValue, value);
    }

    public int getInterpolation() {
        return interpolation;
    }

    // Utility methods

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
}
