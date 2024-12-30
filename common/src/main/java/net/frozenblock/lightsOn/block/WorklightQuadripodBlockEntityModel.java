package net.frozenblock.lightsOn.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.blockEntity.BlockEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class WorklightQuadripodBlockEntityModel extends BlockEntityModel<WorkLightQuadripodBlockEntity> {


    public WorklightQuadripodBlockEntityModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);

    }

    @Override
    public void setupAnim(WorkLightQuadripodBlockEntity entity, float ageInTicks) {

    }

    @Override
    public void render(WorkLightQuadripodBlockEntity entity, float ageInTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha) {

    }
}
