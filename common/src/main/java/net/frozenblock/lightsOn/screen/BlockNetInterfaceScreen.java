package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block_entity.BNIBlockEntity;
import net.frozenblock.lightsOn.packet.EjectDiskPacket;
import net.frozenblock.lightsOn.platform.Services;
import net.frozenblock.lightsOn.screen.util.ActionBarButton;
import net.frozenblock.lightsOn.screen.util.ActionBarSubButton;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lwjgl.system.NonnullDefault;

/**
 * Welcome to the BlockNetInterface Screen
 * @since 1.0
 * */
@NonnullDefault
public class BlockNetInterfaceScreen extends Screen {

    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_interface.png");

    private final ActionBarButton[] actionBar = {
            new ActionBarButton("File",
                    new ActionBarSubButton[]{
                            new ActionBarSubButton(Component.translatable("blocknet.file.save"), () -> {}),
                            new ActionBarSubButton(Component.translatable("blocknet.file.project"), () -> {}),
                            new ActionBarSubButton(Component.translatable("blocknet.file.settings"), () -> {})
                    }
            ),
            new ActionBarButton("Edit",
                    new ActionBarSubButton[]{
                            new ActionBarSubButton(Component.translatable("blocknet.edit.undo"), () -> {}),
                            new ActionBarSubButton(Component.translatable("blocknet.edit.redo"), () -> {})
                    }
            ),
            new ActionBarButton("Select",
                    new ActionBarSubButton[]{
                            new ActionBarSubButton(Component.translatable("blocknet.select.all"), () -> {}),
                            new ActionBarSubButton(Component.translatable("blocknet.select.deselect"), () -> {})
                    }
            ),
            new ActionBarButton("Tools", new ActionBarSubButton[]{}),
            new ActionBarButton("View",
                    new ActionBarSubButton[]{
                            new ActionBarSubButton(Component.translatable("blocknet.view.fullscreen"), () -> this.fullScreen = !this.fullScreen)
                    }
            ),
            new ActionBarButton("Help",
                    new ActionBarSubButton[]{
                            new ActionBarSubButton(Component.translatable("blocknet.help.wiki"), () -> Util.getPlatform().openUri("https://github.com/LIUKRAST/LightsOn/wiki/BlockNet-Interface")),
                            new ActionBarSubButton(Component.translatable("blocknet.help.issue"), () -> Util.getPlatform().openUri("https://github.com/LIUKRAST/LightsOn/issues"))
                    }
            )
    };

    private boolean fullScreen = false;
    @SuppressWarnings("all")
    //TODO: Make this modifiable
    private final float splitAreaPercentage = 0.6f;
    private final BlockEntity blockEntity;
    public BlockNetInterfaceScreen(final BNIBlockEntity blockEntity) {
        super(GameNarrator.NO_TITLE);
        this.blockEntity = blockEntity;
        blockEntity.askForSync();
    }

    public int getHalfWindowWidth() {
        return fullScreen ? this.width/2 : 128;
    }

    public int getHalfWindowHeight() {
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
        int rightPos = (width>>1)+getHalfWindowWidth();
        int leftPos = (width>>1)-getHalfWindowWidth();
        int topPos = (height>>1)-getHalfWindowHeight();
        int bottomPos = (height>>1)+getHalfWindowHeight();
        boolean yForWindowButtons = mouseY >= topPos && mouseY < topPos+10;
        boolean hoverEjectButton = mouseX >= rightPos-30 && mouseX < rightPos-20 && yForWindowButtons;
        boolean hoverFullScreenButton = mouseX >= rightPos-20 && mouseX < rightPos-10 && yForWindowButtons;
        boolean hoverExitButton = mouseX >= rightPos-10 && mouseX < rightPos && yForWindowButtons;
        guiGraphics.blit(TEXTURE, rightPos-30, topPos, 20, hoverEjectButton ? 10 : 0, 10, 10);
        guiGraphics.blit(TEXTURE, rightPos-20, topPos, 10, hoverFullScreenButton ? 10 : 0, 10, 10);
        guiGraphics.blit(TEXTURE, rightPos-10, topPos, 0, hoverExitButton ? 10 : 0, 10, 10);

        guiGraphics.renderOutline(leftPos + 1, topPos + 14, (int)(getHalfWindowWidth()*2*splitAreaPercentage) - 1, getHalfWindowHeight()*2 - 28, -1);
        guiGraphics.renderOutline(leftPos + 1 + (int)(getHalfWindowWidth()*2*splitAreaPercentage), topPos + 14, (int)(getHalfWindowWidth()*2*(1-splitAreaPercentage)) - 1, getHalfWindowHeight()*2 - 28, -1);
        guiGraphics.blit(TEXTURE, leftPos + 1, bottomPos - 13, 54, 0, 12, 12);
        guiGraphics.blit(TEXTURE, leftPos + 14, bottomPos - 13, 30, 0, 12, 12);
        guiGraphics.blit(TEXTURE, leftPos + 27, bottomPos - 13, 66, 0, 12, 12);
        guiGraphics.renderOutline(leftPos + 40, bottomPos - 13, getHalfWindowWidth()*2 - 41, 12, -1);

        int columns = ((int)(getHalfWindowWidth()*2*splitAreaPercentage) -3)/19;
        int rows = (getHalfWindowHeight()*2-32)/19;
        guiGraphics.blit(TEXTURE, leftPos + 3, topPos + 16, 81, 0, 18,18);
        guiGraphics.blit(TEXTURE, leftPos + 3 + (columns > 1 ? 19 : 0), topPos + 16 + (columns > 1 ? 0 : 19), 99, 0, 18,18);
        /*for(int x = 0; x < columns; x++) {
            for(int y = 0; y < rows; y++) {
                guiGraphics.renderOutline(leftPos + 3 + x*19, topPos + 16 + y*19, 18, 18, -1);
            }
        }*/

        boolean yForActionBar = mouseY >= topPos && mouseY < topPos+12;
        int xOffset = 0;
        for(ActionBarButton button : actionBar) {
            button.render(guiGraphics, mouseX, mouseY, xOffset, this, this.font, yForActionBar);
            xOffset+=button.getWidth(this.font)+1;
        }
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(mouseY >= height/2-getHalfWindowHeight() && mouseY < height/2-getHalfWindowHeight()+10) {
            if(mouseX >= width/2+getHalfWindowWidth()-30 && mouseX < width/2+getHalfWindowWidth()-20) {
                Services.PACKET_HELPER.send2S(new EjectDiskPacket(blockEntity.getBlockPos()));
                this.onClose();
                return true;
            } else if(mouseX >= width/2+getHalfWindowWidth()-20 && mouseX < width/2+getHalfWindowWidth()-10) {
                fullScreen = !fullScreen;
                return true;
            } else if(mouseX >= width/2+getHalfWindowWidth()-10 && mouseX < width/2+getHalfWindowWidth()) {
                this.onClose();
                return true;
            }
        }

        for(ActionBarButton actionBarButton : actionBar) {
            if(!actionBarButton.isHovered()) continue;
            for(ActionBarSubButton subButton : actionBarButton.getSubs()) {
                if(!subButton.isHovered()) continue;
                subButton.onClicked();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}