package net.frozenblock.lightsOn.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BlockNetWrenchUtils {
    public static final TagKey<Item> WRENCH_TAG = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.parse("c:tools/wrenches"));

    public static final Component UNBINDABLE = Component.translatable("item.blocknet_wrench.unbindable");
    public static final Component FAILED = Component.translatable("item.blocknet_wrench.failed");
    public static final Component SUCCESS = Component.translatable("item.blocknet_wrench.success");
    public static final Component ALREADY_BOUND = Component.translatable("item.blocknet_wrench.already_bound");

    public static void update(Level world, BlockPos pos) {
        final var state = world.getBlockState(pos);
        final var be = world.getBlockEntity(pos);
        if(be != null) be.setChanged();
        world.sendBlockUpdated(pos, state, state, 2);
    }

    public static void sendMessage(Player player, Component component) {
        if(!(player instanceof ServerPlayer serverPlayer)) return;
        serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(component));
    }

    public static void sendBinding(Player player, BlockPos pos) {
        if(!(player instanceof ServerPlayer serverPlayer)) return;
        serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.blocknet_wrench.binding", pos.toShortString())));
    }
}
