package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.block.BNIBlockEntity;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

/**
 * Welcome to the BlockNetInterface Screen
 * @since 1.0
 * */
public class BlockNetInterfaceScreen extends Screen {
    /**
     * Non-fullscreen width/height 16:9
     * */
    private static final int halfWindowedWidth= 128;
    private static final int halfWindowedHeight = 72;

    private boolean fullScreen = false;

    public BlockNetInterfaceScreen(final BNIBlockEntity blockEntity) {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        if(fullScreen) {
            guiGraphics.fill(0, 0, this.width, this.height, -16777216);
        } else
            guiGraphics.fill(width/2 - halfWindowedWidth, height/2 - halfWindowedHeight, width/2 + halfWindowedWidth, height/2 + halfWindowedHeight, -16777216);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}