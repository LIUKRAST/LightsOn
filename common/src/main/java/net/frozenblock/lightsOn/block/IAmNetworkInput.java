package net.frozenblock.lightsOn.block;

import net.minecraft.core.BlockPos;

import java.util.List;

public interface IAmNetworkInput {

    boolean addOutput(BlockPos input);

    List<BlockPos> getOutputs();

    boolean removeOutput(BlockPos pos);
}
