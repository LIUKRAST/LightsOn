package net.frozenblock.lightsOn.packet;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block_entity.BNIBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public record EjectDiskPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<EjectDiskPacket> PACKET_TYPE = new Type<>(LightsOnConstants.id("eject_disk_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EjectDiskPacket> CODEC = StreamCodec.ofMember(EjectDiskPacket::write, EjectDiskPacket::new);

    public EjectDiskPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public static void handle(EjectDiskPacket packet, Player ctx) {
        Level world = ctx.level();
        if(world.getBlockEntity(packet.pos) instanceof BNIBlockEntity bni) {
            bni.ejectDisk();
        }
    }
}
