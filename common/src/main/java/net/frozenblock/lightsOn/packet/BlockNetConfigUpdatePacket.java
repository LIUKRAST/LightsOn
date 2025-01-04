package net.frozenblock.lightsOn.packet;

import net.frozenblock.lib.blocknet.BlockNetConfigurable;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public record BlockNetConfigUpdatePacket(BlockPos pos, CompoundTag data) implements CustomPacketPayload {
    public static final Type<BlockNetConfigUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("blocknet_config_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockNetConfigUpdatePacket> CODEC = StreamCodec.ofMember(BlockNetConfigUpdatePacket::write, BlockNetConfigUpdatePacket::new);

    public BlockNetConfigUpdatePacket(@NotNull RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    public void write(@NotNull RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(data);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void handle(BlockNetConfigUpdatePacket packet, Player ctx) {
        ServerPlayer player = (ServerPlayer) ctx;
        if(player == null) return;
        ServerLevel world = (ServerLevel) player.level();
        BlockEntity be = world.getBlockEntity(packet.pos);
        if(be instanceof BlockNetConfigurable configurable) configurable.updateData(packet.data);
        else LightsOnConstants.LOGGER.error("Received {} from {}, but BlockEntity at {} is not an instance of {}", BlockNetConfigUpdatePacket.class, ctx.getScoreboardName(), packet.pos, BlockNetConfigurable.class);
    }
}
