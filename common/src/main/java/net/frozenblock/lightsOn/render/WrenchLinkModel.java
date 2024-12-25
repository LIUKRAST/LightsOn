package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WrenchLinkModel extends Model {
    private final ModelPart main;
    private final ModelPart subMain;
    private final ModelPart subSubMain;
    private final ModelPart fine;
    private final ModelPart warn;
    private final ModelPart error;

    public WrenchLinkModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.subMain = main.getChild("subMain");
        this.subSubMain = subMain.getChild("subSubMain");
        this.fine = subSubMain.getChild("fine");
        this.warn = subSubMain.getChild("warn");
        this.error = subSubMain.getChild("error");
    }

    public static LayerDefinition create() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0, 0, 0));
        final var subMain = main.addOrReplaceChild("subMain", CubeListBuilder.create(), PartPose.offset(0, 0, 0));
        final var subSubMain = subMain.addOrReplaceChild("subSubMain", CubeListBuilder.create()
                .texOffs(0,8).addBox(-3,-3,-2,6,6,4)
                ,PartPose.ZERO);
        subSubMain.addOrReplaceChild("fine", CubeListBuilder.create().texOffs(16,0).addBox(-2,-2,-2,4,4,4), PartPose.ZERO);
        subSubMain.addOrReplaceChild("warn", CubeListBuilder.create().texOffs(0,0).addBox(-2,-2,-2,4,4,4), PartPose.ZERO);
        subSubMain.addOrReplaceChild("error", CubeListBuilder.create().texOffs(16,8).addBox(-2,-2,-2,4,4,4), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }

    public void animate(Vec3 input, Vec3 output, State state) {
        float yaw = (float) (Math.atan2(input.z - output.z, input.x - output.x) - Math.PI/2f);
        float hL = (float) Math.sqrt(Math.pow(input.x - output.x, 2) + Math.pow(input.z - output.z, 2));
        float length = (float) Math.sqrt(Math.pow(hL, 2) + Math.pow(input.y - output.y, 2)) * 4;
        float pitch = (float) Math.atan2(input.y - output.y, hL);
        fine.visible = false;
        warn.visible = false;
        error.visible = false;
        switch (state) {
            case FINE -> fine.visible = true;
            case WARN -> warn.visible = true;
            case ERR -> error.visible = true;
        }
        main.yRot = yaw;
        subMain.xRot = pitch;
        subSubMain.z = 2-length*2;
        subSubMain.zScale = length;
    }

    public enum State {
        FINE, WARN, ERR
    }
}
