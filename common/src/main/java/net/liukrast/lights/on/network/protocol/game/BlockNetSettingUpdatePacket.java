package net.liukrast.lights.on.network.protocol.game;

import net.liukrast.lib.blocknet.BlockNetConfigurable;
import net.liukrast.lights.LightsOnConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record BlockNetSettingUpdatePacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {
    public static final Type<BlockNetSettingUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("blocknet_setting_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockNetSettingUpdatePacket> CODEC = StreamCodec.ofMember(BlockNetSettingUpdatePacket::write, BlockNetSettingUpdatePacket::new);

    private BlockNetSettingUpdatePacket(@NotNull RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    private void write(@NotNull RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void handle(ServerPlayer player) {
        if(player == null) return;
        if(player.level().getBlockEntity(pos) instanceof BlockNetConfigurable bnc)
            bnc.updateSettings(tag);
    }
}
