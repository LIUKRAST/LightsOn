package net.frozenblock.lib.dataComponents;

import com.mojang.serialization.*;
import net.minecraft.core.BlockPos;

public class ExtraCodec {
    public static final Codec<BlockPos> BLOCK_POS = new BlockPosCodec();
}
