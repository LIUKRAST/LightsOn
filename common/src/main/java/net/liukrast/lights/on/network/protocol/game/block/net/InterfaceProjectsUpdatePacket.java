package net.liukrast.lights.on.network.protocol.game.block.net;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public record InterfaceProjectsUpdatePacket(List<String> projects) implements CustomPacketPayload {

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
