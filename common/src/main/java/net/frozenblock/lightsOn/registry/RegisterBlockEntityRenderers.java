package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.render.LightBeamBlockEntityRenderer;
import net.frozenblock.lightsOn.render.WorklightStandBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class RegisterBlockEntityRenderers {

    public static void register() {
        BlockEntityRenderers.register(RegisterBlockEntities.LIGHT_BEAM, LightBeamBlockEntityRenderer::new);
        BlockEntityRenderers.register(RegisterBlockEntities.WORKLIGHT_STAND, WorklightStandBlockEntityRenderer::new);
    }
}
