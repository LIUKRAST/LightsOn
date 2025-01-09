package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.LightBeamBlock;
import net.frozenblock.lightsOn.block_entity.LightBeamBlockEntity;
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
public class LightBeamBlockEntityRenderer implements BlockEntityRenderer<LightBeamBlockEntity> {
    public static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/light_beam.png");
    public static final LightBeamBlockEntityModel MODEL = new LightBeamBlockEntityModel(LightBeamBlockEntityModel.create().bakeRoot());

    public LightBeamBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render( LightBeamBlockEntity blockEntity, float partial, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float h = blockEntity.getProgress(partial);
        MODEL.supplementary(blockEntity, h);
        var level = blockEntity.getLevel();
        final float age = level == null ? 0 : level.getGameTime() + partial;
        poseStack.pushPose();
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.translate(-0.5D, -1.501F, 0.5D);
        final RenderType renderType = MODEL.renderType(TEXTURE);
        final VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        MODEL.setupAnim(blockEntity, age);
        MODEL.render(blockEntity, h, poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 16777216, 1.0F);
        if (blockEntity.getBlockState().getValue(LightBeamBlock.POWERED))
            MODEL.renderLightBeam(blockEntity, h, buffer, poseStack);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(LightBeamBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox(LightBeamBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        final float length = blockEntity.calculateLength(1);
        return new AABB(pos.getX() - length, pos.getY() - length, pos.getZ() - length,pos.getX() + length, pos.getY() + length,pos.getZ() + length);
    }
}
