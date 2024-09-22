package net.frozenblock.lightsOn.packet;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.LightBeamBlockEntity;
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

public record LightBeamUpdatePacket(BlockPos pos, CompoundTag data) implements CustomPacketPayload {
    public static final Type<LightBeamUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("send_light_beam_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LightBeamUpdatePacket> CODEC = StreamCodec.ofMember(LightBeamUpdatePacket::write, LightBeamUpdatePacket::new);

    public LightBeamUpdatePacket(@NotNull RegistryFriendlyByteBuf buf) {
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

    public static void handle(LightBeamUpdatePacket packet, Player ctx) {
        ServerPlayer player = (ServerPlayer) ctx;
        if(player != null) {
            if (player.canUseGameMasterBlocks()) {
                @SuppressWarnings("all")
                ServerLevel world = (ServerLevel) player.level();
                BlockEntity be = world.getBlockEntity(packet.pos);
                if (be instanceof LightBeamBlockEntity moving) {
                    moving.update(packet.data);
                }
            }
        }
    }
}
