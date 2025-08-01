package net.liukrast.lights.on.network.protocol.game;

import net.liukrast.lights.LightsOnConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record EditorUpdatePacket(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<EditorUpdatePacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("editor_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EditorUpdatePacket> CODEC = StreamCodec.ofMember(EditorUpdatePacket::write, EditorUpdatePacket::new);

    private EditorUpdatePacket(RegistryFriendlyByteBuf buf) {
        this(buf.readNbt());
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public void handle() {
        //TODO: if(Minecraft.getInstance().screen instanceof BlockNetScreen screen) screen.update(tag);
    }
}
