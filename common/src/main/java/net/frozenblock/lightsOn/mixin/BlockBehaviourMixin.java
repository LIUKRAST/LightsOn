package net.frozenblock.lightsOn.mixin;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.blocknet.BlockNetPole;
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
        if(movedByPiston || state.is(newState.getBlock())) return;
        if(level.getBlockEntity(pos) instanceof BlockNetPole element) {
            for(BlockPos pole : element.getPoles()) {
                if(level.getBlockEntity(pole) instanceof BlockNetPole element1) element1.removePole(pos);
                else LightsOnConstants.LOGGER.error("Tried to search for pole BlockNetElement, but found pole pole not instance of {}", BlockNetPole.class);
            }
        }
    }
}
