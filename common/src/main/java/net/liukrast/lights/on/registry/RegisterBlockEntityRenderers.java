package net.liukrast.lights.on.registry;

import net.liukrast.lights.on.client.renderer.blockentity.SpotlightRenderer;
import net.liukrast.lights.on.client.renderer.blockentity.WorklightStandRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class RegisterBlockEntityRenderers {

    public static void register() {
        BlockEntityRenderers.register(RegisterBlockEntityTypes.SPOTLIGHT, SpotlightRenderer::new);
        BlockEntityRenderers.register(RegisterBlockEntityTypes.WORKLIGHT_STAND, WorklightStandRenderer::new);
    }
}
