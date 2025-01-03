package net.frozenblock.lightsOn;

import net.frozenblock.lightsOn.render.WrenchLinkRenderer;
import net.frozenblock.lightsOn.render.LightBeamBlockEntityRenderer;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = LightsOnConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class LightsOnClient {

    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {}

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntities.LIGHT_BEAM, LightBeamBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(RegisterBlockEntities.BLOCKNET_INTERFACE, WrenchLinkRenderer::new);
        event.registerBlockEntityRenderer(RegisterBlockEntities.BLOCKNET_LINK, WrenchLinkRenderer::new);
    }
}
