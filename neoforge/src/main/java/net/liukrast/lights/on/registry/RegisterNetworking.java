package net.liukrast.lights.on.registry;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.network.protocol.game.BNIDataUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BNIUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BlockNetConfigUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.EjectDiskPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class RegisterNetworking {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registry = event.registrar(LightsOnConstants.MOD_ID).versioned(LightsOnConstants.PACKET_VERSION);
        registry.playToServer(BlockNetConfigUpdatePacket.PACKET_TYPE, BlockNetConfigUpdatePacket.CODEC, (p, c) -> BlockNetConfigUpdatePacket.handle(p, c.player()));
        registry.playToServer(BNIUpdatePacket.PACKET_TYPE, BNIUpdatePacket.CODEC, (p, c) -> BNIUpdatePacket.handle(p, c.player()));
        registry.playToServer(BNIDataUpdatePacket.PACKET_TYPE, BNIDataUpdatePacket.CODEC, (p, c) -> BNIDataUpdatePacket.handle(p, c.player()));
        registry.playToServer(EjectDiskPacket.PACKET_TYPE, EjectDiskPacket.CODEC, (p, c) -> EjectDiskPacket.handle(p, c.player()));
    }
}
