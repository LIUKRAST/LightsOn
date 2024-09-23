package net.frozenblock.lightsOn;

import net.fabricmc.api.ClientModInitializer;
import net.frozenblock.lightsOn.block.LightBeamBlockEntityRenderer;
import net.frozenblock.lightsOn.block.WrenchLinkRenderer;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.frozenblock.lightsOn.registry.RegisterNetworking;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class LightsOnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RegisterNetworking.registerClient();
        BlockEntityRenderers.register(RegisterBlockEntities.LIGHT_BEAM, LightBeamBlockEntityRenderer::new);
        BlockEntityRenderers.register(RegisterBlockEntities.BLOCKNET_INTERFACE, WrenchLinkRenderer::new);
        BlockEntityRenderers.register(RegisterBlockEntities.BLOCKNET_LINK, WrenchLinkRenderer::new);
    }
}
