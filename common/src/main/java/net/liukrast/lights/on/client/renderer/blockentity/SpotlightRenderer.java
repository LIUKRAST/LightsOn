package net.liukrast.lights.on.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.client.model.SpotlightModel;
import net.liukrast.lights.on.world.level.block.SpotlightBlock;
import net.liukrast.lights.on.world.level.block.entity.Spotlight;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SpotlightRenderer implements BlockEntityRenderer<Spotlight> {
    public static final ResourceLocation TEXTURE_ON = LightsOnConstants.id("textures/entity/spotlight/spotlight_on.png");
    public static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/spotlight/spotlight.png");
    public static final SpotlightModel MODEL = new SpotlightModel(SpotlightModel.create().bakeRoot());

    public SpotlightRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render(Spotlight blockEntity, float partial, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        MODEL.supplementary(blockEntity, partial);
        var level = blockEntity.getLevel();
        final float age = level == null ? 0 : level.getGameTime() + partial;
        boolean powered = blockEntity.getBlockState().getValue(SpotlightBlock.POWERED);
        poseStack.pushPose();
        poseStack.scale(1, -1, -1);
        poseStack.translate(0.5, -1.5, -0.5);
        final RenderType renderType = MODEL.renderType(powered ? TEXTURE_ON : TEXTURE);
        final VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        MODEL.setupAnim(blockEntity, age);
        MODEL.render(blockEntity, partial, poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 16777216, 1.0F);
        if (powered)
            MODEL.renderLightBeam(blockEntity, partial, buffer, poseStack);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(Spotlight blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox(Spotlight blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        final float length = blockEntity.length.getProgress(1);
        return new AABB(pos).inflate(length);
    }
}
