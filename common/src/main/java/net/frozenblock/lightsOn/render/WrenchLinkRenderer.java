package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.item.BlockNetWrenchUtils;
import net.frozenblock.lightsOn.data_component.WrenchConnection;
import net.frozenblock.lightsOn.registry.RegisterDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WrenchLinkRenderer {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/entity/wrench_link.png");
    private static final WrenchLinkModel MODEL = new WrenchLinkModel(WrenchLinkModel.create().bakeRoot());

    public static void render(BlockEntity blockEntity, @SuppressWarnings("unused") float v, PoseStack poseStack, MultiBufferSource multiBufferSource, @SuppressWarnings("unused") int packedLight, int packedOverlay) {
        if(!(blockEntity instanceof BlockNetPole blockNetPole)) return;
        var player = Minecraft.getInstance().player;
        if(player == null) return;
        final var mainHand = player.getMainHandItem();
        final var offHand = player.getOffhandItem();
        final var stack = mainHand.is(BlockNetWrenchUtils.WRENCH_TAG) ? mainHand :
                offHand.is(BlockNetWrenchUtils.WRENCH_TAG) ? offHand : null;
        if(stack == null) return;
        poseStack.pushPose();
        poseStack.scale(-1, -1, 1);
        poseStack.translate(-0.5d, -0.5d, 0.5d);
        final RenderType rt = MODEL.renderType(TEXTURE);
        final VertexConsumer vertexConsumer = multiBufferSource.getBuffer(rt);
        final BlockPos that = blockEntity.getBlockPos();
        var level = blockEntity.getLevel();
        if(level == null) return;
        for (BlockPos pos : blockNetPole.getPoles()) {
            if(level.getBlockEntity(pos) instanceof BlockNetPole) {
                if(hasPriority(that, pos)) continue;
                poseStack.translate(0, 0.01, 0);
                MODEL.animate(createVector(that), createVector(pos), WrenchLinkModel.State.FINE);
                MODEL.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay);
            } else LightsOnConstants.LOGGER.error("Tried to search for pole BlockNetElement, but found pole pole not instance of {}", BlockNetPole.class);
        }
        final var hitResult = Minecraft.getInstance().hitResult;
        final BlockPos blockPos = hitResult != null && hitResult.getType() != HitResult.Type.MISS
                && hitResult instanceof BlockHitResult ? ((BlockHitResult)hitResult).getBlockPos() : null;
        final Vec3 pos = blockPos == null ? player.position() : Vec3.atLowerCornerOf(blockPos);
        WrenchLinkModel.State state = blockPos != null ? (level.getBlockEntity(blockPos) instanceof BlockNetPole ? WrenchLinkModel.State.FINE : WrenchLinkModel.State.ERR) : WrenchLinkModel.State.WARN;
        renderPlayerBinding(stack, that, pos, state, poseStack, vertexConsumer, packedOverlay);
        poseStack.popPose();
    }

    public static void renderPlayerBinding(ItemStack stack, BlockPos pos, Vec3 playerPos, WrenchLinkModel.State state, PoseStack poseStack, VertexConsumer vertexConsumer, int packedOverlay) {
        final var data = stack.getOrDefault(RegisterDataComponents.WRENCH_CONNECTION, new WrenchConnection(null));
        if(pos.equals(data.pole())) {
            MODEL.animate(createVector(pos), playerPos, state);
            MODEL.renderToBuffer(poseStack, vertexConsumer, 15728880, packedOverlay);
        }
    }

    private static Vec3 createVector(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
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
}
