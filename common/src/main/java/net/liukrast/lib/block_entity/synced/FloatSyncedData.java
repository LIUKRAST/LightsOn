package net.liukrast.lib.block_entity.synced;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FloatSyncedData extends AbstractInterpolatedData<Float> {
    public FloatSyncedData(BlockEntity blockEntity) {
        super(0.0f, blockEntity);
    }

    @Override
    public Codec<Float> codec() {
        return Codec.FLOAT;
    }

    @Override
    protected Float interpolatedValue(float ageInTicks, Float oldValue, Float newValue) {
        return interpolate(oldValue, newValue, ageInTicks);
    }
}
