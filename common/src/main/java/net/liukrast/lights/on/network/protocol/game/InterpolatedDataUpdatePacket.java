package net.liukrast.lights.on.network.protocol.game;

import net.liukrast.lib.blocknet.InterpolatedHolder;
import net.liukrast.lights.LightsOnConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public record InterpolatedDataUpdatePacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {
    public static final Type<InterpolatedDataUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("interpolated_data_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InterpolatedDataUpdatePacket> CODEC = StreamCodec.ofMember(InterpolatedDataUpdatePacket::write, InterpolatedDataUpdatePacket::new);

    private InterpolatedDataUpdatePacket(RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void handle(Player player) {
        if(player == null) return;
        if(player.level().getBlockEntity(pos) instanceof InterpolatedHolder holder) holder.getHolder().set(tag, player.level());
    }
}
