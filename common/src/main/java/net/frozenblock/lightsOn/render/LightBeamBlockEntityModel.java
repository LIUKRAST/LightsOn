package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.frozenblock.lib.blockEntity.BlockEntityModel;
import net.frozenblock.lightsOn.block.LightBeamBlock;
import net.frozenblock.lightsOn.blockentity.LightBeamBlockEntity;
import net.frozenblock.lightsOn.screen.ColorMode;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import static net.frozenblock.lightsOn.render.LightBeamRenderer.BEAM;

public class LightBeamBlockEntityModel extends BlockEntityModel<LightBeamBlockEntity> {

    private final ModelPart main;
    private final ModelPart body;
    private final ModelPart head;

    public LightBeamBlockEntityModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.body = main.getChild("body");
        this.head = body.getChild("head");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 19).addBox(-8.0F, 5.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-6.0F, 2.0F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).addBox(-8.0F, -19.0F, -5.0F, 3.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).mirror().addBox(5.0F, -19.0F, -5.0F, 3.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(38, 43).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-9.0F, -2.0F, -2.0F, 18.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 19).addBox(-4.0F, -4.0F, -13.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void supplementary(LightBeamBlockEntity entity, float[] h) {
        body.yRot = (float)(Math.toRadians(entity.calculateYaw(h[2])));
        head.xRot = -lim((float)(Math.toRadians(entity.calculatePitch(h[1]))), 0, (float)Math.PI);
    }

    @Override
    public void setupAnim(LightBeamBlockEntity entity, float ageInTicks) {
        Direction facing = entity.getBlockState().getValue(LightBeamBlock.FACING);
        main.y = 16;
        if(facing == Direction.UP) {
            main.xRot = 0;
        } else if(facing == Direction.DOWN) {
            main.xRot = (float) Math.PI;
        } else {
            main.xRot = (float) Math.PI / 2.0F;
        }

        main.yRot = (float) ((float) Math.toRadians(facing.toYRot()) + Math.PI);
    }

    @Override
    public void render(LightBeamBlockEntity entity, float ageInTicks, @NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void renderLightBeam(LightBeamBlockEntity entity, float[] ageInTicks, MultiBufferSource bufferSource, PoseStack poseStack) {
        main.translateAndRotate(poseStack);
        body.translateAndRotate(poseStack);
        head.translateAndRotate(poseStack);
        int color = entity.calculateColor(ageInTicks[0]);
        int[] rgb = ColorMode.int2rgb(color);
        float a = (rgb[0]/255.0f + rgb[1]/255.0f + rgb[2]/255.0f)/3.0f;
        poseStack.translate(0, 0, -0.8125f);
        LightBeamRenderer.renderLightBeam(bufferSource.getBuffer(BEAM), poseStack, a, 3/16f, entity.calculateSize(ageInTicks[3]), entity.calculateLength(ageInTicks[4]), color);
    }

    public static float lim(float var, float min, float max) {
        if(var < min) return min;
        return Math.min(var, max);
    }
}
