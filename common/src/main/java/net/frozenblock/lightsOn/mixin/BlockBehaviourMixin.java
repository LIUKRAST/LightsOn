package net.frozenblock.lightsOn.mixin;

import net.frozenblock.lightsOn.block.IAmNetworkInput;
import net.frozenblock.lightsOn.block.IAmNetworkOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(at = @At("HEAD"), method = "onRemove")
    private void inject(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        final var be = level.getBlockEntity(pos);
        if(be instanceof IAmNetworkInput input) {
            for(BlockPos tempPos : input.getOutputs()) {
                final var be2 = level.getBlockEntity(tempPos);
                if(be2 instanceof IAmNetworkOutput output) {
                    output.removeInput(pos);
                    be2.setChanged();
                    level.sendBlockUpdated(tempPos, state, state, 2);
                }
            }
        }
        if(be instanceof IAmNetworkOutput output) {
            for(BlockPos tempPos : output.getInputs()) {
                final var be2 = level.getBlockEntity(tempPos);
                if(be2 instanceof IAmNetworkInput input) {
                    input.removeOutput(pos);
                    be2.setChanged();
                    level.sendBlockUpdated(tempPos, state, state, 2);
                }
            }
        }

    }
}
