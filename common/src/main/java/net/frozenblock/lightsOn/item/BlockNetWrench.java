package net.frozenblock.lightsOn.item;

import net.frozenblock.lightsOn.block.IAmNetworkInput;
import net.frozenblock.lightsOn.block.IAmNetworkOutput;
import net.frozenblock.lightsOn.registry.RegisterDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class BlockNetWrench extends Item {

    public BlockNetWrench(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
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
        return InteractionResult.SUCCESS;
    }

    private void update(Level world, BlockPos pos) {
        final var state = world.getBlockState(pos);
        final var be = world.getBlockEntity(pos);
        if(be != null) be.setChanged();
        world.sendBlockUpdated(pos, state, state, 2);
    }

    private Boolean already(final Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.blocknet_wrench.already_bound")));
        }
        return null;
    }

    private void unbindable(final Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.blocknet_wrench.unbindable")));
        }
    }

    private void reset(ItemStack stack, Boolean error, final Player player) {
        stack.set(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        if(error != null && player instanceof ServerPlayer serverPlayer)
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(
                    error ? Component.translatable("item.blocknet_wrench.connected")
                            : Component.translatable("item.blocknet_wrench.error")));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        if(data.a() != null) tooltipComponents.add(Component.translatable("item.blocknet_wrench.binding", data.a().toShortString()));
    }
}
