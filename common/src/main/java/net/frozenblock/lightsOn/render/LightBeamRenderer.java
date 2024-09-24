package net.frozenblock.lightsOn.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

public class LightBeamRenderer extends RenderType {

    public static final RenderType BEAM = create("LightsonBeam",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256,
            CompositeState.builder().setLayeringState(RenderStateShard.POLYGON_OFFSET_LAYERING)
                    .setTextureState(NO_TEXTURE)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(RenderStateShard.CULL)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setWriteMaskState(WriteMaskStateShard.COLOR_WRITE)
                    .setShaderState(RenderStateShard.RENDERTYPE_LIGHTNING_SHADER)
                    .createCompositeState(false));

    public LightBeamRenderer(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, vertexFormat, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static void renderLightBeam(VertexConsumer builder, PoseStack stack, float alpha, float beamSize, float beamEnd, float beamLength, int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (int) (alpha * 255);
        Matrix4f m = stack.last().pose();
        float endMultiplier = beamEnd * 2.65f;
        float length = beamLength * 0.77f;

        builder.addVertex(m, beamSize, beamSize, 0).setColor(r,g,b,a);
        builder.addVertex(m, beamSize, -beamSize, 0).setColor(r,g,b,a);
        builder.addVertex(m, -beamSize, -beamSize, 0).setColor(r,g,b,a);
        builder.addVertex(m, -beamSize, beamSize, 0).setColor(r,g,b,a);

        builder.addVertex(m, beamSize * endMultiplier, beamSize * endMultiplier, -length).setColor(r, g, b,0);
        builder.addVertex(m, beamSize, beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, beamSize, -beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, beamSize * endMultiplier, -beamSize * endMultiplier, -length).setColor(r, g, b,0);

        builder.addVertex(m, -beamSize * endMultiplier, -beamSize * endMultiplier, -length).setColor(r, g, b,0);
        builder.addVertex(m, -beamSize, -beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, -beamSize, beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, -beamSize * endMultiplier, beamSize * endMultiplier, -length).setColor(r, g, b,0);

        builder.addVertex(m, -beamSize * endMultiplier, beamSize * endMultiplier, -length).setColor(r, g, b,0);
        builder.addVertex(m, -beamSize, beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, beamSize, beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, beamSize * endMultiplier, beamSize * endMultiplier, -length).setColor(r, g, b,0);

        builder.addVertex(m, beamSize * endMultiplier, -beamSize * endMultiplier, -length).setColor(r, g, b,0);
        builder.addVertex(m, beamSize, -beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, -beamSize, -beamSize, 0).setColor(r, g, b, a);
        builder.addVertex(m, -beamSize * endMultiplier, -beamSize * endMultiplier, -length).setColor(r, g, b,0);
    }
}
