package net.frozenblock.lightsOn.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record WrenchConnection(BlockPos pole) {

    public static final Codec<WrenchConnection> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockPos.CODEC.fieldOf("input").forGetter(WrenchConnection::pole)
                    ).apply(instance, WrenchConnection::new));

    public static final StreamCodec<ByteBuf, WrenchConnection> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, WrenchConnection::pole,
            WrenchConnection::new
    );

    @Override
    public int hashCode() {
        return Objects.hash(pole);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else return obj instanceof WrenchConnection wc
                && this.pole == wc.pole;
    }

}
