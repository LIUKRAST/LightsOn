package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.item.WrenchConnection;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class RegisterDataComponents {

    public static final DataComponentType<WrenchConnection> WRENCH_CONNECTION = DataComponentType.<WrenchConnection>builder()
            .persistent(WrenchConnection.CODEC)
            .networkSynchronized(WrenchConnection.STREAM_CODEC)
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, LightsOnConstants.id("wrench_connection"), WRENCH_CONNECTION);
    }
}
