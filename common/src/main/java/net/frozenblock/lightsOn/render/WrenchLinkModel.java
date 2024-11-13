package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WrenchLinkModel extends Model {
    private final ModelPart main;
    private final ModelPart main_1;
    private final ModelPart main_2;
    public WrenchLinkModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.main_1 = main.getChild("main_1");
        this.main_2 = main_1.getChild("main_2");
    }

    public static LayerDefinition create() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0, 0, 0));
        final var main_1 = main.addOrReplaceChild("main_1", CubeListBuilder.create(), PartPose.offset(0, 0, 0));
        main_1.addOrReplaceChild("main_2", CubeListBuilder.create()
                .texOffs(0,0).addBox(-2,-2,-2,4,4,4)
                .texOffs(0,8).addBox(-3,-3,-2,6,6,4)
                ,PartPose.offset(0, 0, 0));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void animate(Vec3 input, Vec3 output) {
        float yaw = (float) ((float) Math.atan2(input.z - output.z, input.x - output.x) - Math.PI/2f);
        float hL = (float) Math.sqrt(Math.pow(input.x - output.x, 2) + Math.pow(input.z - output.z, 2));
        float length = (float) Math.sqrt(Math.pow(hL, 2) + Math.pow(input.y - output.y, 2)) * 4;
        float pitch = (float) Math.atan2(input.y - output.y, hL);
        main.yRot = yaw;
        main_1.xRot = pitch;
        main_2.z = 2-length*2;
        main_2.zScale = length;
    }
}
