package net.frozenblock.lightsOn.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.frozenblock.lib.dataComponents.ExtraByteBufCodecs;
import net.frozenblock.lib.dataComponents.ExtraCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record WrenchConnection(BlockPos a) {

    public static final Codec<WrenchConnection> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ExtraCodec.BLOCK_POS.fieldOf("input").forGetter(WrenchConnection::a)
                    ).apply(instance, WrenchConnection::new));

    public static final StreamCodec<ByteBuf, WrenchConnection> STREAM_CODEC = StreamCodec.composite(
            ExtraByteBufCodecs.BLOCK_POS, WrenchConnection::a,
            WrenchConnection::new
    );

    //public static final StreamCodec<ByteBuf, WrenchConnection> UNIT_STREAM_CODEC = StreamCodec.unit(new WrenchConnection(BlockPos.ZERO, BlockPos.ZERO, false));

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else return obj instanceof WrenchConnection wc
                && this.a == wc.a;
    }

}
