package net.liukrast.lib.block_entity.synced;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ColorSyncedData extends AbstractInterpolatedData<Integer> {
    public ColorSyncedData(BlockEntity blockEntity) {
        super(16777216, blockEntity);
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

    @Override
    public Codec<Integer> codec() {
        return ExtraCodecs.intRange(0, 16777216);
    }
}
