package net.frozenblock.lightsOn.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.frozenblock.lightsOn.item.BlockNetWrenchUtils;
import net.frozenblock.lightsOn.item.WrenchConnection;
import net.frozenblock.lightsOn.registry.RegisterDataComponents;
import net.frozenblock.lightsOn.render.WrenchLinkModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class WrenchLinkRenderer implements BlockEntityRenderer<BlockEntity> {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/wrench_link.png");
    private static final WrenchLinkModel MODEL = new WrenchLinkModel(WrenchLinkModel.create().bakeRoot());

    public WrenchLinkRenderer(BlockEntityRendererProvider.Context ignored) {}

    @Override
    public void render(@NotNull BlockEntity blockEntity, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(blockEntity instanceof IAmNetworkInput input) {
            var player = Minecraft.getInstance().player;
            if(player != null) {
                final var mainHand = (player.getMainHandItem());
                final var offHand = (player.getOffhandItem());
                final var stack = mainHand.is(BlockNetWrenchUtils.WRENCH_TAG) ? mainHand :
                        offHand.is(BlockNetWrenchUtils.WRENCH_TAG) ? offHand : null;
                if(stack != null) {
                    poseStack.pushPose();
                    poseStack.scale(-1, -1, 1);
                    poseStack.translate(-0.5d, -0.5d, 0.5d);
                    final RenderType rt = MODEL.renderType(TEXTURE);
                    final VertexConsumer vertexConsumer = multiBufferSource.getBuffer(rt);
                    final BlockPos that = blockEntity.getBlockPos();
                    for (BlockPos pos : input.getOutputs()) {
                        final BlockEntity be = blockEntity.getLevel().getBlockEntity(pos);
                        if ((!(blockEntity instanceof IAmNetworkOutput) && be instanceof IAmNetworkInput) || be instanceof IAmNetworkOutput) {
                            if(hasPriority(that, pos)) continue;
                            poseStack.translate(0, 0.01, 0);
                            MODEL.animate(createVector(that), createVector(pos));
                            MODEL.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay);
                        }
                    }
                    renderPlayerBinding(stack, that, player, poseStack, vertexConsumer, packedOverlay);
                    poseStack.popPose();
                }
            }
        }
    }

    public static void renderPlayerBinding(ItemStack stack, BlockPos pos, Player player, PoseStack poseStack, VertexConsumer vertexConsumer, int packedOverlay) {
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        if(pos.equals(data.a())) {
            MODEL.animate(createVector(pos), player.position());
            MODEL.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay);
        }
    }

    private static boolean hasPriority(BlockPos a, BlockPos b) {
        if(a.getX() > b.getX()) return true;
        if(a.getX() == b.getX()) {
            if(a.getY() > b.getY()) return true;
            if(a.getY() == b.getY()) {
                return a.getZ() > b.getZ();
            }
        }
        return false;
    }

    private static Vec3 createVector(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
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
