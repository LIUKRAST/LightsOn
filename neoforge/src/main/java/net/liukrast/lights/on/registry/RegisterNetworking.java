package net.liukrast.lights.on.registry;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.network.protocol.game.EditorUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BlockNetSettingUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BlockNetDataSlotChangePacket;
import net.liukrast.lights.on.network.protocol.game.InterpolatedDataUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class RegisterNetworking {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registry = event.registrar(LightsOnConstants.MOD_ID).versioned(LightsOnConstants.PACKET_VERSION);
        registry.playToServer(BlockNetSettingUpdatePacket.PACKET_TYPE, BlockNetSettingUpdatePacket.CODEC, (p, c) -> p.handle((ServerPlayer) c.player()));
        registry.playToClient(InterpolatedDataUpdatePacket.PACKET_TYPE, InterpolatedDataUpdatePacket.CODEC, (p, c) -> p.handle(c.player()));
        registry.playToServer(BlockNetDataSlotChangePacket.PACKET_TYPE, BlockNetDataSlotChangePacket.CODEC, (p, c) -> p.handle(c.player()));
        registry.playToClient(EditorUpdatePacket.PACKET_TYPE, EditorUpdatePacket.CODEC, (p, c) -> p.handle());
    }
}
