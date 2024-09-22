package net.frozenblock.lightsOn.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.frozenblock.lightsOn.render.WrenchLinkModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WrenchLinkRenderer implements BlockEntityRenderer<BlockEntity> {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/wrench_link.png");
    private static final WrenchLinkModel MODEL = new WrenchLinkModel(WrenchLinkModel.create().bakeRoot());

    public WrenchLinkRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render(@NotNull BlockEntity blockEntity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(blockEntity instanceof IAmNetworkInput input) {
            final var stack = Minecraft.getInstance().player != null
                    && (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof BlockNetWrench
                    || Minecraft.getInstance().player.getOffhandItem().getItem() instanceof BlockNetWrench);
            if (stack) {
                poseStack.pushPose();
                poseStack.scale(-1, -1, 1);
                poseStack.translate(-0.5d, -0.5d, 0.5d);
                final RenderType rt = MODEL.renderType(TEXTURE);
                final VertexConsumer vertexConsumer = multiBufferSource.getBuffer(rt);
                //final BlockPos that = blockEntity.getBlockPos();
                for (BlockPos pos : input.getOutputs()) {
                    final BlockEntity be = blockEntity.getLevel().getBlockEntity(pos);
                    if ((!(blockEntity instanceof IAmNetworkOutput) && be instanceof IAmNetworkInput) || be instanceof IAmNetworkOutput) {
                        /* We want to avoid some duplicate, but i was too lazy to do it
                        TODO: Make it one day maybe (Very much maybe)
                        if (blockEntity instanceof IAmNetworkOutput)
                            if(pos.getX() >= that.getX() && pos.getY() >= that.getY() && pos.getZ() >= that.getZ())
                                continue;*/
                        poseStack.translate(0, 0.01, 0);
                        MODEL.animate(blockEntity.getBlockPos(), pos);
                        MODEL.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay);
                    }
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRender(@NotNull BlockEntity blockEntity, @NotNull Vec3 cameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull BlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @SuppressWarnings("unused")
    public @NotNull AABB getRenderBoundingBox(@NotNull BlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        final int length = 1024;
        return new AABB(pos.getX() - length, pos.getY() - length, pos.getZ() - length,pos.getX() + length, pos.getY() + length,pos.getZ() + length);
    }
}
