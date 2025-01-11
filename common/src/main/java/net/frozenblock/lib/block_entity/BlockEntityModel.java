package net.frozenblock.lib.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class BlockEntityModel<T extends BlockEntity> extends Model {

    public BlockEntityModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }

    public abstract void setupAnim(T entity, float ageInTicks);

    public abstract void render(T entity, float ageInTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha);

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int i1, int i2) {}
}
