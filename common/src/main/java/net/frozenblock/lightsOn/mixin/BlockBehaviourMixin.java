package net.frozenblock.lightsOn.mixin;

import net.frozenblock.lib.blocknet.BlockNetConfigurable;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.frozenblock.lightsOn.screen.BlockNetConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(at = @At("HEAD"), method = "onRemove")
    private void lights_on$onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        if(movedByPiston || state.is(newState.getBlock())) return;
        if(level.getBlockEntity(pos) instanceof BlockNetPole element) {
            for(BlockPos pole : element.getPoles()) {
                if(level.getBlockEntity(pole) instanceof BlockNetPole element1) element1.removePole(pos);
                else LightsOnConstants.LOGGER.error("Tried to search for pole BlockNetElement, but found pole pole not instance of {}", BlockNetPole.class);
            }
        }
    }

    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void lights_on$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(!(level.getBlockEntity(pos) instanceof BlockNetConfigurable blockNetConfigurable)) return;
        if(!player.getOffhandItem().is(RegisterItems.BLOCKNET_WRENCH)) return;
        if(level.isClientSide) {
            Runnable onlyOnClient = () -> Minecraft.getInstance().setScreen(new BlockNetConfigScreen(blockNetConfigurable, pos));
            onlyOnClient.run();
        }
        cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
    }
}
