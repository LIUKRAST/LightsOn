package net.liukrast.lights.on;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.registry.*;

public class LightsOn implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        RegisterNetworking.register();
        RegisterNetworking.registerServer();
        RegisterBlocks.register();
        RegisterBlockEntityTypes.register();
        RegisterCreativeModeTabs.register();
        RegisterItems.register();
        RegisterDataComponents.register();
    }

    @Override
    public void onInitializeClient() {
        RegisterNetworking.registerClient();
        RegisterBlockEntityRenderers.register();
        LightsOnConstants.initClient();
    }
}
