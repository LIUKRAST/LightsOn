package net.frozenblock.lightsOn;

import net.fabricmc.api.ModInitializer;
import net.frozenblock.lightsOn.registry.*;

public class LightsOn implements ModInitializer {
    @Override
    public void onInitialize() {
        RegisterNetworking.register();
        RegisterNetworking.registerServer();
        RegisterBlocks.register();
        RegisterBlockEntities.register();
        RegisterCreativeModeTabs.register();
        RegisterItems.register();
        RegisterDataComponents.register();
    }
}
