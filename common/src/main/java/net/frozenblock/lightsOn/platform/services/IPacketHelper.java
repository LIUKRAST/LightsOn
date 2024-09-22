package net.frozenblock.lightsOn.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface IPacketHelper {
    void send2S(CustomPacketPayload packet);

    void send2Player(ServerPlayer player, CustomPacketPayload packet);
}
