package net.liukrast.lights.on.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.liukrast.lights.on.network.protocol.game.BNIDataUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BNIUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.BlockNetConfigUpdatePacket;
import net.liukrast.lights.on.network.protocol.game.EjectDiskPacket;

public class RegisterNetworking {

    public static void register() {
        PayloadTypeRegistry.playC2S().register(BlockNetConfigUpdatePacket.PACKET_TYPE, BlockNetConfigUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(BNIUpdatePacket.PACKET_TYPE, BNIUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(BNIDataUpdatePacket.PACKET_TYPE, BNIDataUpdatePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(EjectDiskPacket.PACKET_TYPE, EjectDiskPacket.CODEC);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
    }

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(BlockNetConfigUpdatePacket.PACKET_TYPE, (packet, context) -> BlockNetConfigUpdatePacket.handle(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(BNIUpdatePacket.PACKET_TYPE, (packet, context) -> BNIUpdatePacket.handle(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(BNIDataUpdatePacket.PACKET_TYPE, (packet, context) -> BNIDataUpdatePacket.handle(packet, context.player()));
        ServerPlayNetworking.registerGlobalReceiver(EjectDiskPacket.PACKET_TYPE, (packet, context) -> EjectDiskPacket.handle(packet, context.player()));
    }
}
