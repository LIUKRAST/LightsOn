package net.frozenblock.lightsOn.block;

import net.minecraft.core.BlockPos;

import java.util.List;

public interface IAmNetworkOutput {

    boolean addInput(BlockPos input);

    List<BlockPos> getInputs();

    boolean removeInput(BlockPos pos);
}
