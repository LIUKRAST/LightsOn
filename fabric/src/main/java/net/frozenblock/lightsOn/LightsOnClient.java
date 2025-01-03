package net.frozenblock.lightsOn;

import net.fabricmc.api.ClientModInitializer;
import net.frozenblock.lightsOn.registry.RegisterBlockEntityRenderers;
import net.frozenblock.lightsOn.render.LightBeamBlockEntityRenderer;
import net.frozenblock.lightsOn.render.WrenchLinkRenderer;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.frozenblock.lightsOn.registry.RegisterNetworking;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class LightsOnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RegisterNetworking.registerClient();
        RegisterBlockEntityRenderers.register();
    }
}
