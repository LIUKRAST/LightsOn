package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.blockEntity.BlockEntityModel;
import net.frozenblock.lightsOn.blockentity.WorklightStandBlockEntity;
import net.frozenblock.lightsOn.screen.ColorMode;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import static net.frozenblock.lightsOn.render.LightBeamRenderer.BEAM;

public class WorklightStandBlockEntityModel extends BlockEntityModel<WorklightStandBlockEntity> {
    private final ModelPart middle;
    private final ModelPart right_head;
    private final ModelPart left_head;
    private final ModelPart legs;

    public WorklightStandBlockEntityModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.middle = root.getChild("middle");
        this.right_head = middle.getChild("right_head");
        this.left_head = middle.getChild("left_head");
        this.legs = root.getChild("legs");
    }

    public static LayerDefinition create() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition middle = partDefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 23.0F, 2.0F)
                .texOffs(0, 10).addBox(-6.5F, -3.0F, 0.0F, 13.0F, 3.0F, 0.0F), PartPose.offset(0.0F, 1.0F, 0.0F));
        middle.addOrReplaceChild("right_head", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -6.0F, -2.0F, 6.0F, 6.0F, 4.0F), PartPose.offset(0.0F, -3.0F, 0.0F));
        middle.addOrReplaceChild("left_head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -2.0F, 6.0F, 6.0F, 4.0F), PartPose.offset(0.0F, -3.0F, 0.0F));
        PartDefinition legs = partDefinition.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, -25.0F, -8.0F, 0.0F, 16.0F, 16.0F)
                .texOffs(16, 16).addBox(-8.0F, -25.0F, 0.0F, 16.0F, 16.0F, 0.0F), PartPose.offset(0.0F, 33.0F, 0.0F));
        legs.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -27.0F, -1.5F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(WorklightStandBlockEntity entity, float ageInTicks) {
        float yaw = (float) Math.toRadians(entity.getYaw());
        this.middle.y = -1-(entity.getHeight()*14F);
        this.middle.yRot = yaw;
        this.legs.yRot = (float) (yaw + Math.PI/4);
        this.right_head.xRot = (float) Math.toRadians(entity.getRightPitch());
        this.left_head.xRot = (float) Math.toRadians(entity.getLeftPitch());
    }

    @Override
    public void render(WorklightStandBlockEntity entity, float ageInTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha) {
        middle.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderLightBeam(MultiBufferSource bufferSource, PoseStack poseStack) {
        int color = 16777215;
        int[] rgb = ColorMode.int2rgb(color);
        float a = (rgb[0]/255.0f + rgb[1]/255.0f + rgb[2]/255.0f)/3.0f;
        poseStack.pushPose();
        middle.translateAndRotate(poseStack);
        right_head.translateAndRotate(poseStack);
        poseStack.translate(0.25f, -3/16f, -2/16f);
        LightBeamRenderer.renderLightBeam(bufferSource.getBuffer(BEAM), poseStack, a, 1/8f, 1, 1, color);
        poseStack.popPose();
        poseStack.pushPose();
        middle.translateAndRotate(poseStack);
        left_head.translateAndRotate(poseStack);
        poseStack.translate(-0.25f, -3/16f, -2/16f);
        LightBeamRenderer.renderLightBeam(bufferSource.getBuffer(BEAM), poseStack, a, 1/8f, 1, 1, color);
        poseStack.popPose();
    }
}
