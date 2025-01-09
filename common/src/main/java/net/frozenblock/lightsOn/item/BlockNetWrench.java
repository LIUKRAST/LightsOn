package net.frozenblock.lightsOn.item;

import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.registry.RegisterDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

import static net.frozenblock.lightsOn.item.BlockNetWrenchUtils.*;

@NonnullDefault
public class BlockNetWrench extends Item {

    public BlockNetWrench(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        final var data = stack.get(RegisterDataComponents.WRENCH_CONNECTION);
        if(data != null) tooltipComponents.add(Component.translatable("item.blocknet_wrench.binding", data.toShortString()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final var pos = context.getClickedPos();
        final var world = context.getLevel();
        final var block = world.getBlockEntity(pos);
        final var player = context.getPlayer();
        if(player == null) return InteractionResult.FAIL;
        final var stack = context.getItemInHand();
        final var data = stack.get(RegisterDataComponents.WRENCH_CONNECTION);

        if(data == null) {
            if(block instanceof BlockNetPole) {
                stack.set(RegisterDataComponents.WRENCH_CONNECTION, pos);
                sendBinding(player, pos);
            } else sendMessage(player, UNBINDABLE);
        } else {
            final var block1 = world.getBlockEntity(data);
            if(!(block instanceof BlockNetPole pole2)) {
                if(player.isCrouching()) {
                    sendMessage(player, FAILED);
                    stack.remove(RegisterDataComponents.WRENCH_CONNECTION);
                    return InteractionResult.CONSUME;
                } else sendMessage(player, UNBINDABLE);
            } else if(block1 instanceof BlockNetPole pole1) {
                sendMessage(player, SUCCESS);
                if(pos.equals(data)) {
                    sendMessage(player, UNBINDABLE);
                    return InteractionResult.CONSUME;
                }
                if(pole1.hasPole(pos) || pole2.hasPole(data)) sendMessage(player, ALREADY_BOUND);
                pole1.addPole(pos);
                pole2.addPole(data);
                update(world, data);
                update(world, pos);
                stack.remove(RegisterDataComponents.WRENCH_CONNECTION);
            } else {
                sendMessage(player, FAILED);
                stack.remove(RegisterDataComponents.WRENCH_CONNECTION);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        final var data = stack.get(RegisterDataComponents.WRENCH_CONNECTION);
        return super.isFoil(stack) || data != null;
    }
}
