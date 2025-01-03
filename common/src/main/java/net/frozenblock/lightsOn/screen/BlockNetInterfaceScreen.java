package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.blockentity.BNIBlockEntity;
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
                            new ActionBarSubButton(Component.translatable("blocknet.help.issue"), () -> {})
                    }
            )
    };

    private boolean fullScreen = false;
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
        boolean yForWindowButtons = mouseY >= height/2-getHalfWindowHeight() && mouseY < height/2-getHalfWindowHeight()+10;
        boolean hoverEjectButton = mouseX >= width/2+getHalfWindowWidth()-30 && mouseX < width/2+getHalfWindowWidth()-20 && yForWindowButtons;
        boolean hoverFullScreenButton = mouseX >= width/2+getHalfWindowWidth()-20 && mouseX < width/2+getHalfWindowWidth()-10 && yForWindowButtons;
        boolean hoverExitButton = mouseX >= width/2+getHalfWindowWidth()-10 && mouseX < width/2+getHalfWindowWidth() && yForWindowButtons;
        guiGraphics.blit(TEXTURE, width/2+getHalfWindowWidth()-30, height/2-getHalfWindowHeight(), 20, hoverEjectButton ? 10 : 0, 10, 10);
        guiGraphics.blit(TEXTURE, width/2+getHalfWindowWidth()-20, height/2-getHalfWindowHeight(), 10, hoverFullScreenButton ? 10 : 0, 10, 10);
        guiGraphics.blit(TEXTURE, width/2+getHalfWindowWidth()-10, height/2-getHalfWindowHeight(), 0, hoverExitButton ? 10 : 0, 10, 10);

        boolean yForActionBar = mouseY >= height/2-getHalfWindowHeight() && mouseY < height/2-getHalfWindowHeight()+12;
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