package net.liukrast.lights.on.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.liukrast.lib.block_entity.BlockEntityModel;
import net.liukrast.lights.on.client.renderer.blockentity.BeamRenderType;
import net.liukrast.lights.on.world.level.block.SpotlightBlock;
import net.liukrast.lights.on.world.level.block.entity.Spotlight;
import net.liukrast.lights.on.client.gui.screens.ColorMode;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;

import static net.liukrast.lights.on.client.renderer.blockentity.BeamRenderType.BEAM;

public class SpotlightModel extends BlockEntityModel<Spotlight> {

    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart top_panel;
    private final ModelPart right_panel;
    private final ModelPart bottom_panel;
    private final ModelPart left_panel;

    public SpotlightModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.body = this.main.getChild("body");
        this.head = this.body.getChild("head");
        this.top_panel = this.head.getChild("top_panel");
        this.right_panel = this.head.getChild("right_panel");
        this.bottom_panel = this.head.getChild("bottom_panel");
        this.left_panel = this.head.getChild("left_panel");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 37).addBox(-5.0F, 7.0F, -4.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -6.0F, -1.5F, 8.0F, 6.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(0, 20).addBox(-5.0F, -10.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        head.addOrReplaceChild("top_panel", CubeListBuilder.create().texOffs(23, 2).addBox(0.0F, 0.0F, -5.0F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.01f)), PartPose.offset(-4.0F, -4.0F, -6.0F));

        head.addOrReplaceChild("right_panel", CubeListBuilder.create().texOffs(28, 15).addBox(0.0F, 0.0F, -5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(-4.0F, -4.0F, -6.0F));

        head.addOrReplaceChild("bottom_panel", CubeListBuilder.create().texOffs(23, 7).addBox(-8.0F, 0.0F, -5.0F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(4.0F, 4.0F, -6.0F));

        head.addOrReplaceChild("left_panel", CubeListBuilder.create().texOffs(0, -5).addBox(0.0F, -8.0F, -5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(4.0F, 4.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    public void supplementary(Spotlight entity, float partial) {
        body.yRot = (float)(Math.toRadians(entity.yaw.getProgress(partial)));
        head.xRot = -lim((float)(Math.toRadians(entity.pitch.getProgress(partial))), 0, (float)Math.PI);

        float open = (float) (Math.sqrt((entity.size.getProgress(partial) - 1) / 100) * Math.PI / 2);
        top_panel.xRot = -open;
        bottom_panel.xRot = open;
        right_panel.yRot = open;
        left_panel.yRot = -open;
    }

    @Override
    public void setupAnim(Spotlight entity, float ageInTicks) {
        Direction facing = entity.getBlockState().getValue(SpotlightBlock.FACING);
        if(facing == Direction.UP) {
            main.xRot = 0;
        } else if(facing == Direction.DOWN) {
            main.xRot = (float) Math.PI;
        } else {
            main.xRot = (float) Math.PI / 2.0F;
        }

        main.yRot = (float) ((float) Math.toRadians(facing.toYRot()));
    }

    @Override
    public void render(Spotlight entity, float ageInTicks, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderLightBeam(Spotlight entity, float partial, MultiBufferSource bufferSource, PoseStack poseStack) {
        main.translateAndRotate(poseStack);
        body.translateAndRotate(poseStack);
        head.translateAndRotate(poseStack);
        int color = entity.color.getProgress(partial);
        int[] rgb = ColorMode.int2rgb(color);
        float a = (rgb[0]/255.0f + rgb[1]/255.0f + rgb[2]/255.0f)/3.0f;
        poseStack.translate(0, 0, -6/16f);
        BeamRenderType.renderLightBeam(bufferSource.getBuffer(BEAM), poseStack, a, 3/16f, entity.size.getProgress(partial), entity.length.getProgress(partial), color);
    }

    public static float lim(float var, float min, float max) {
        if(var < min) return min;
        return Math.min(var, max);
    }
}
