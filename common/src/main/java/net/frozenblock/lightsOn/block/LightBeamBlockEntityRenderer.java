package net.frozenblock.lightsOn.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LightBeamBlockEntityRenderer implements BlockEntityRenderer<LightBeamBlockEntity> {
    public static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/block/light_beam.png");
    public static final LightBeamBlockEntityModel MODEL = new LightBeamBlockEntityModel(LightBeamBlockEntityModel.create().bakeRoot());

    public LightBeamBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render(@NotNull LightBeamBlockEntity blockEntity, float partial, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float[] h = blockEntity.getProgress(partial);
        MODEL.supplementary(blockEntity, h);
        final float age = blockEntity.getLevel().getGameTime() + partial;
        poseStack.pushPose();
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.translate(-0.5D, -1.501F, 0.5D);
        final RenderType renderType = MODEL.renderType(TEXTURE);
        final VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        MODEL.setupAnim(blockEntity, age);
        MODEL.render(blockEntity, h[0], poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 16777216, 1.0F);
        if (blockEntity.getBlockState().getValue(LightBeamBlock.POWERED))
            MODEL.renderLightBeam(blockEntity, h, buffer, poseStack);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull LightBeamBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(@NotNull LightBeamBlockEntity blockEntity, @NotNull Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(cameraPos.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @SuppressWarnings("unused")
    public @NotNull AABB getRenderBoundingBox(@NotNull LightBeamBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        final float length = blockEntity.calculateLength(1);
        return new AABB(pos.getX() - length, pos.getY() - length, pos.getZ() - length,pos.getX() + length, pos.getY() + length,pos.getZ() + length);
    }
}
