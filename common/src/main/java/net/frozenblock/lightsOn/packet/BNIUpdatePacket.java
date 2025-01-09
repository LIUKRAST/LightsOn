package net.frozenblock.lightsOn.packet;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block_entity.BNIBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public record BNIUpdatePacket(BlockPos pos) implements CustomPacketPayload {

    public static final Type<BNIUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("send_bni_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BNIUpdatePacket> CODEC = StreamCodec.ofMember(BNIUpdatePacket::write, BNIUpdatePacket::new);

    public BNIUpdatePacket(@NotNull RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void handle(BNIUpdatePacket packet, Player ctx) {
        ServerPlayer player = (ServerPlayer) ctx;
        if(player != null) {
            @SuppressWarnings("all")
            ServerLevel world = (ServerLevel) player.level();
            BlockEntity be = world.getBlockEntity(packet.pos);
            if (be instanceof BNIBlockEntity blockNetInterface)
                blockNetInterface.askForSync();
        }
    }
}
