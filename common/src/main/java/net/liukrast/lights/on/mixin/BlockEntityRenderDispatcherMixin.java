package net.liukrast.lights.on.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.liukrast.lib.blocknet.BlockNetPole;
import net.liukrast.lights.on.client.renderer.blockentity.WrenchLinkRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This mixin allows block entities implementing
 * {@link BlockNetPole} to render a wrench connection by default.
 * @since 1.0
 * @author LiukRast
 * */
@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    @Shadow public Level level;

    @Inject(method = "render", at = @At("HEAD"))
    private <E extends BlockEntity> void render(E blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if(!(blockEntity instanceof BlockNetPole)) return;
        Level level = blockEntity.getLevel();
        int i;
        if(level != null) i = LevelRenderer.getLightColor(level, blockEntity.getBlockPos());
        else i = 15728880;
        WrenchLinkRenderer.render(blockEntity, partialTick, poseStack, bufferSource, i, OverlayTexture.NO_OVERLAY);
    }
}
