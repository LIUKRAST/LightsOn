package net.liukrast.lights.on;

import net.liukrast.lights.LightsOnConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TestScreen extends Screen {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_interface.png");
    public TestScreen(Component title) {
        super(title);
    }

    @Override
    protected void renderBlurredBackground(float partialTick) {}
}
