package net.frozenblock.lib.dataComponents;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtraByteBufCodecs {
    public static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS = new StreamCodec<>() {
        @SuppressWarnings("all")
        public BlockPos decode(@NotNull ByteBuf buf) {
            try {
                return new BlockPos(
                        buf.readInt(),
                        buf.readInt(),
                        buf.readInt()
                );
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        public void encode(@NotNull ByteBuf buf, @Nullable BlockPos pos) {
            if(pos != null) {
                buf.writeInt(pos.getX());
                buf.writeInt(pos.getY());
                buf.writeInt(pos.getZ());
            }
        }
    };
}
