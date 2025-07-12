package net.liukrast.lights.on.network.protocol.game;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.world.inventory.BlockNetMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public record BlockNetDataSlotChangePacket(int action, int value) implements CustomPacketPayload {
    public static final Type<BlockNetDataSlotChangePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("blocknet_data_slot_change"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockNetDataSlotChangePacket> CODEC = StreamCodec.ofMember(BlockNetDataSlotChangePacket::write, BlockNetDataSlotChangePacket::new);

    private BlockNetDataSlotChangePacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(action);
        buf.writeInt(value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void handle(Player player) {
        if(player == null) return;
        if(player.containerMenu instanceof BlockNetMenu blockNetMenu) blockNetMenu.setData(action, value);
    }
}
