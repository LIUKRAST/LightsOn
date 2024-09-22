package net.frozenblock.lightsOn;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.frozenblock.lightsOn.block.LightBeamBlockEntityRenderer;
import net.frozenblock.lightsOn.registry.RegisterBlockEntities;
import net.frozenblock.lightsOn.registry.RegisterNetworking;
import net.frozenblock.lightsOn.render.WrenchLinkRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.phys.Vec3;

public class LightsOnClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RegisterNetworking.registerClient();
        BlockEntityRenderers.register(RegisterBlockEntities.LIGHT_BEAM, LightBeamBlockEntityRenderer::new);

        WorldRenderEvents.LAST.register(context -> {
            Minecraft minecraft = Minecraft.getInstance();
            if(minecraft.level == null || minecraft.player == null) return;
            PoseStack poseStack = context.matrixStack();
            poseStack.pushPose();
            Vec3 view = context.camera().getPosition();
            //poseStack.translate(-view.x, -view.y, -view.z);
            float deltaTick = context.tickCounter().getGameTimeDeltaPartialTick(true);
            WrenchLinkRender.render(minecraft, poseStack, view, deltaTick);
            poseStack.popPose();
        });
    }
}
