package net.frozenblock.lightsOn.item;

import net.frozenblock.lightsOn.blocknet.BlockNetPole;
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
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        if(data.pole() != null) tooltipComponents.add(Component.translatable("item.blocknet_wrench.binding", data.pole().toShortString()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final var pos = context.getClickedPos();
        final var world = context.getLevel();
        final var block = world.getBlockEntity(pos);
        final var player = context.getPlayer();
        final var stack = context.getItemInHand();
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));

        if(data.pole() == null) {
            if(block instanceof BlockNetPole) {
                stack.set(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(pos));
                sendMessage(player, BINDING);
            } else sendMessage(player, UNBINDABLE);
        } else {
            final var block1 = world.getBlockEntity(data.pole());
            if(!(block1 instanceof BlockNetPole pole2)) sendMessage(player, UNBINDABLE);
            else if(block instanceof BlockNetPole pole1) {
                sendMessage(player, SUCCESS);
                if(pole1.hasPole(data.pole()) || pole2.hasPole(pos)) sendMessage(player, ALREADY_BOUND);
                pole1.addPole(data.pole());
                pole2.addPole(pos);
                stack.set(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
            } else {
                sendMessage(player, FAILED);
                stack.set(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        return super.isFoil(stack) || data.pole() != null;
    }
}
