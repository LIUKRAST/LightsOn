package net.frozenblock.lightsOn.packet;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.BNIBlockEntity;
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

public record BNIDataUpdatePacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {

    public static final Type<BNIDataUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("send_bni_data_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BNIDataUpdatePacket> CODEC = StreamCodec.ofMember(BNIDataUpdatePacket::write, BNIDataUpdatePacket::new);

    public BNIDataUpdatePacket(@NotNull RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void handle(BNIDataUpdatePacket packet, Player ctx) {
        ServerPlayer player = (ServerPlayer) ctx;
        if(player != null) {
            ServerLevel world = (ServerLevel) player.level();
            BlockEntity be = world.getBlockEntity(packet.pos);
            if (be instanceof BNIBlockEntity blockNetInterface)
                blockNetInterface.askForSync();
        }
    }
}
