package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.BNIBlockEntity;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.NonnullDefault;

/**
 * Welcome to the BlockNetInterface Screen
 * @since 1.0
 * */
@NonnullDefault
public class BlockNetInterfaceScreen extends Screen {

    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_interface.png");

    private boolean fullScreen = false;

    public BlockNetInterfaceScreen(final BNIBlockEntity blockEntity) {
        super(GameNarrator.NO_TITLE);
        blockEntity.askForSync();
    }

    private int getHalfWindowWidth() {
        return fullScreen ? this.width/2 : 128;
    }

    private int getHalfWindowHeight() {
        return fullScreen ? this.height/2 : 72;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(width/2 - getHalfWindowWidth(), height/2 - getHalfWindowHeight(), width/2 + getHalfWindowWidth(), height/2 + getHalfWindowHeight(), -16777216);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        boolean yForFullAndExit = mouseY >= height/2-getHalfWindowHeight() && mouseY < height/2-getHalfWindowHeight()+10;
        boolean hoverFullScreenButton = mouseX >= width/2+getHalfWindowWidth()-20 && mouseX < width/2+getHalfWindowWidth()-10 && yForFullAndExit;
        boolean hoverExitButton = mouseX >= width/2+getHalfWindowWidth()-10 && mouseX < width/2+getHalfWindowWidth() && yForFullAndExit;
        guiGraphics.blit(TEXTURE, width/2+getHalfWindowWidth()-20, height/2-getHalfWindowHeight(), 0, hoverFullScreenButton ? 10 : 0, 10, 10);
        guiGraphics.blit(TEXTURE, width-10, height-10, 0, 0, 10, 10);
        guiGraphics.blit(TEXTURE, width/2+getHalfWindowWidth()-10, height/2-getHalfWindowHeight(), 10, hoverExitButton ? 10 : 0, 10, 10);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(mouseY >= height/2-getHalfWindowHeight() && mouseY < height/2-getHalfWindowHeight()+10) {
            if(mouseX >= width/2+getHalfWindowWidth()-20 && mouseX < width/2+getHalfWindowWidth()-10) {
                fullScreen = !fullScreen;
                return true;
            } else if(mouseX >= width/2+getHalfWindowWidth()-10 && mouseX < width/2+getHalfWindowWidth()) {
                this.onClose();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}