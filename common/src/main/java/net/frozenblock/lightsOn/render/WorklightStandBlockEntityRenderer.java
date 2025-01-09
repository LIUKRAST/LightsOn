package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.WorklightStandBlock;
import net.frozenblock.lightsOn.block_entity.WorklightStandBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class WorklightStandBlockEntityRenderer implements BlockEntityRenderer<WorklightStandBlockEntity> {
    public static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/worklight_stand.png");
    public static final WorklightStandBlockEntityModel MODEL = new WorklightStandBlockEntityModel(WorklightStandBlockEntityModel.create().bakeRoot());

    public WorklightStandBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render(WorklightStandBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(-1, -1, 1);
        poseStack.translate(-0.5, -1.5, 0.5);
        final RenderType type = MODEL.renderType(TEXTURE);
        final VertexConsumer consumer = bufferSource.getBuffer(type);
        MODEL.setupAnim(blockEntity, partialTick);
        MODEL.render(blockEntity, partialTick, poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 16777216, 1.0F);
        if(blockEntity.getBlockState().getValue(WorklightStandBlock.POWERED))
            MODEL.renderLightBeam(bufferSource, poseStack);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(WorklightStandBlockEntity blockEntity) {
        return true;
    }

    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox(WorklightStandBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos().above()).inflate(0, 1,0);
    }
}
