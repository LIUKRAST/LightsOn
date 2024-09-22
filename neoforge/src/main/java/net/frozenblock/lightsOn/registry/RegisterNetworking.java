package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.packet.BNIDataUpdatePacket;
import net.frozenblock.lightsOn.packet.BNIUpdatePacket;
import net.frozenblock.lightsOn.packet.LightBeamUpdatePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@SuppressWarnings("unused")
public class RegisterNetworking {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final var registry = event.registrar("1");
        registry.playToServer(LightBeamUpdatePacket.PACKET_TYPE, LightBeamUpdatePacket.CODEC, (p,c) -> LightBeamUpdatePacket.handle(p, c.player()));
        registry.playToServer(BNIUpdatePacket.PACKET_TYPE, BNIUpdatePacket.CODEC, (p, c) -> BNIUpdatePacket.handle(p, c.player()));
        registry.playToServer(BNIDataUpdatePacket.PACKET_TYPE, BNIDataUpdatePacket.CODEC, (p, c) -> BNIDataUpdatePacket.handle(p, c.player()));
    }
}
