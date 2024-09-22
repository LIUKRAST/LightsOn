package net.frozenblock.lightsOn.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.frozenblock.lightsOn.platform.services.IPacketHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketHelper implements IPacketHelper {
    @Override
    public void send2S(CustomPacketPayload packet) {
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void send2Player(ServerPlayer player, CustomPacketPayload packet) {
        ServerPlayNetworking.send(player, packet);
    }
}
