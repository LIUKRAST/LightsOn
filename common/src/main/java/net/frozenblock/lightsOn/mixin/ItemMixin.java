package net.frozenblock.lightsOn.mixin;

import net.frozenblock.lightsOn.block.IAmNetworkInput;
import net.frozenblock.lightsOn.block.IAmNetworkOutput;
import net.frozenblock.lightsOn.item.WrenchConnection;
import net.frozenblock.lightsOn.registry.RegisterDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.frozenblock.lightsOn.item.BlockNetWrenchUtils.*;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
    private void lights_on$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if(context.getItemInHand().is(WRENCH_TAG)) {
            final var pos = context.getClickedPos();
            final var world = context.getLevel();
            final var block = world.getBlockEntity(pos);
            final var player = context.getPlayer();
            final var stack = context.getItemInHand();
            final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));

            Optional<BlockPos> a = Optional.empty();
            if(data.a() == null) {
                if(block instanceof IAmNetworkOutput || block instanceof IAmNetworkInput) a = Optional.of(pos);
                else unbindable(player);
            } else {
                final var block1 = world.getBlockEntity(data.a());
                if(block1 != null && block != null) {
                    if(block instanceof IAmNetworkInput && block1 instanceof IAmNetworkInput && !(block instanceof IAmNetworkOutput || block1 instanceof IAmNetworkOutput)) {
                        reset(stack, false, player);
                    } else if(block instanceof IAmNetworkOutput && block1 instanceof IAmNetworkOutput && !(block instanceof IAmNetworkInput || block1 instanceof IAmNetworkInput)) {
                        reset(stack, false, player);
                    } else {
                        Boolean k = false;
                        if (block1 instanceof IAmNetworkOutput out) {
                            if (!out.addInput(pos)) k = already(player);
                            else k = true;
                        }
                        if (block1 instanceof IAmNetworkInput in) {
                            if (!in.addOutput(pos)) k = already(player);
                            else k = true;
                        }
                        if (block instanceof IAmNetworkOutput out) {
                            if (!out.addInput(data.a())) k = already(player);
                            else k = true;
                        }
                        if (block instanceof IAmNetworkInput in) {
                            if (!in.addOutput(data.a())) k = already(player);
                            else k = true;
                        }
                        if (block1 instanceof IAmNetworkOutput || block1 instanceof IAmNetworkInput)
                            update(world, data.a());
                        if (block instanceof IAmNetworkOutput || block instanceof IAmNetworkInput)
                            update(world, pos);
                        reset(stack, k, player);
                    }
                } else {
                    reset(stack, false, player);
                }
            }
            a.ifPresent(blockPos -> stack.set(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(blockPos)));
            cir.cancel();
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(at = @At("TAIL"), method = "appendHoverText")
    private void lights_on$appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if(stack.is(WRENCH_TAG)) {
            final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
            if(data.a() != null) tooltipComponents.add(Component.translatable("item.blocknet_wrench.binding", data.a().toShortString()));
        }
    }

    @Inject(at = @At("RETURN"), method = "isFoil", cancellable = true)
    private void lights_on$isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.is(WRENCH_TAG)) {
            final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
            cir.setReturnValue(cir.getReturnValueZ() || data.a() != null);
        }
    }

}
