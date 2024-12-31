package net.frozenblock.lightsOn.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.blockEntity.BlockEntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class WorklightStandBlockEntityModel extends BlockEntityModel<WorklightStandBlockEntity> {


    public WorklightStandBlockEntityModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);

    }

    @Override
    public void setupAnim(WorklightStandBlockEntity entity, float ageInTicks) {

    }

    @Override
    public void render(WorklightStandBlockEntity entity, float ageInTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha) {

    }
}
