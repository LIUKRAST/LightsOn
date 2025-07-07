package net.liukrast.lights.on;

import com.mojang.blaze3d.systems.RenderSystem;
import net.createmod.catnip.gui.UIRenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class NewBlockNetInterfaceScreen extends Screen {
    public NewBlockNetInterfaceScreen(Component title) {
        super(title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        var pose = guiGraphics.pose();
        pose.pushPose();
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        pose.pushPose();
        UIRenderHelper.flipForGuiRender(pose);

        pose.popPose();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        pose.popPose();
    }
}
