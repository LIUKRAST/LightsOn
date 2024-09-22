package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.BNIBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

public class BlockNetInterfaceScreen extends Screen {
    private final int imageWidth = 256;
    private final int imageHeight = 16*9;
    private int leftPos;
    private int topPos;
    private final int loadingTime = 20;
    private int currentTime = 0;
    private static final ResourceLocation INTERFACE = LightsOnConstants.id("textures/gui/blocknet_interface.png");
    final BNIBlockEntity blockEntity;

    private BlockPos output = null;

    /* BUTTONS */

    private Button file;
    private boolean isFileOpen = false;
    private Button fileNew;
    private Button fileOpen;
    private Button fileImport;
    private Button fileSaveAs;

    public BlockNetInterfaceScreen(final BNIBlockEntity blockEntity) {
        super(GameNarrator.NO_TITLE);
        this.blockEntity = blockEntity;
        blockEntity.askForSync();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth)/2;
        this.topPos = (this.height - this.imageHeight)/2;

        /* FILE MENU */
        file = Button
                .builder(Component.translatable("blocknet.file"), b -> openFileMenu(!isFileOpen))
                .bounds(this.leftPos, topPos, 20, 10)
                .build();
        fileNew = Button
                .builder(Component.translatable("blocknet.file.new"), b -> {})
                .bounds(this.leftPos, topPos + 10, 50, 10)
                .build();
        fileOpen = Button
                .builder(Component.translatable("blocknet.file.open"), b -> {})
                .bounds(this.leftPos, topPos + 20, 50, 10)
                .build();
        fileImport = Button
                .builder(Component.translatable("blocknet.file.import"), b -> {})
                .bounds(this.leftPos, topPos + 30, 50, 10)
                .build();
        fileSaveAs = Button
                .builder(Component.translatable("blocknet.file.saveAs"), b -> {})
                .bounds(this.leftPos, topPos + 40, 50, 10)
                .build();

        this.addRenderableWidget(file);
        this.addRenderableWidget(fileNew);
        this.addRenderableWidget(fileOpen);
        this.addRenderableWidget(fileImport);
        this.addRenderableWidget(fileSaveAs);
        file.visible = currentTime >= loadingTime;
        fileNew.visible = isFileOpen;
        fileOpen.visible = isFileOpen;
        fileImport.visible = isFileOpen;
        fileSaveAs.visible = isFileOpen;
    }

    private void openFileMenu(boolean open) {
        isFileOpen = open;
        fileNew.visible = open;
        fileOpen.visible = open;
        fileImport.visible = open;
        fileSaveAs.visible = open;
    }

    @Override
    public void tick() {
        super.tick();
        currentTime++;
        if(currentTime == loadingTime)
            file.visible = true;
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        int i = this.leftPos;
        int j = topPos;
        if(currentTime < loadingTime) {
            for(int x = 0; x < 16; x++) {
                for(int y = 0; y < 9; y++) {
                    final int vOffset = 176 + (y == 0 ? 0 : y == 8 ? 32 : 16);
                    if(x == 0) guiGraphics.blit(INTERFACE, i, j + y * 16, 0, vOffset, 16, 16);
                    else if(x == 15) guiGraphics.blit(INTERFACE, i + x * 16, j + y * 16, 32, vOffset, 16, 16);
                    else if(y == 0) guiGraphics.blit(INTERFACE, i + x * 16, j, 16, 176, 16, 16);
                    else if(y == 8) guiGraphics.blit(INTERFACE, i + x * 16, j + y * 16, 16, 176 + 16 * 2, 16, 16);
                    else guiGraphics.blit(INTERFACE, i + x * 16, j + y * 16, 16, 176 + 16, 16, 16);
                }
            }
            guiGraphics.blit(INTERFACE, i + 96, j + 70, 0, 157, 64, 19);
            guiGraphics.blit(INTERFACE, i + 24, j + 96, 48, 192, 208, 16);
            int length = (int) ((((float)currentTime + partialTick)/loadingTime)*208);
            guiGraphics.blit(INTERFACE, i + 24, j + 96, 48, 192 + 16, length, 16);
        } else {
            guiGraphics.blit(INTERFACE, i, j, 0, 0, this.imageWidth, this.imageHeight);
            final var data = blockEntity.getData();
            if(!data.contains("ProjectID"))
                drawCenteredStringMultiLine(guiGraphics, Component.translatable("blocknet.no_project").getString(), i + 80, j + 75, 0x3e3e3e, false);
            if(output == null)
                drawCenteredStringMultiLine(guiGraphics, Component.translatable("blocknet.no_output").getString(), i+208, j + 77, 0x3e3e3e, false);
            if(isInBounds(mouseX - i, mouseY - j, 229, 1, 236, 8)) {
                guiGraphics.blit(INTERFACE, i + 228, j, 22, 144, 10, 10);
                guiGraphics.blit(INTERFACE, i + 188, j + 10, 64, 144, 112, 48);
                drawCenteredStringMultiLine(guiGraphics, Component.translatable("blocknet.warning").getString(), i + 244, j + 36, 0x3e3e3e, true);
            } else if(isInBounds(mouseX - i, mouseY - j, 238, 1, 245, 8)) {
                guiGraphics.blit(INTERFACE, i + 237, j, 31, 144, 10, 10);
                guiGraphics.blit(INTERFACE, i + 188, j + 10, 64, 144, 112, 48);
                drawCenteredStringMultiLine(guiGraphics, Component.translatable("blocknet.info").getString(), i + 244, j + 36, 0x3e3e3e, true);
            } else if(isInBounds(mouseX - i, mouseY - j, 247, 1, 254, 8)) {
                guiGraphics.blit(INTERFACE, i + 246, j, 40, 144, 10, 10);
            }
        }
    }

    private void drawCenteredStringMultiLine(GuiGraphics guiGraphics, String a, int x, int y, int color, boolean shadow) {
        final var b = a.split("\n");
        for(int k = 0; k < b.length; k++) {
            final var c = b[k];
            int h = this.font.lineHeight * k;
            guiGraphics.drawString(font, Component.literal(c), x - font.width(Component.literal(c)) / 2, y + h - ((this.font.lineHeight * b.length + 1)/2), color, shadow);
        }
    }

    private boolean isInBounds(int mouseX, int mouseY, int x, int y, int maxX, int maxY) {
        return mouseX >= x && mouseX <= maxX && mouseY >= y && mouseY <= maxY;
    }

    private void playButtonSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isInBounds((int) (mouseX - leftPos), (int) (mouseY - topPos), 238, 1, 245, 8)) {
            playButtonSound();
            Util.getPlatform().openUri("https://github.com/LIUKRAST/LightsOn/wiki/BlockNet-Interface");
        } else if(isInBounds((int) (mouseX - leftPos), (int) (mouseY - topPos), 247, 1, 254, 8)) {
            playButtonSound();
            this.onClose();
        } else return super.mouseClicked(mouseX, mouseY, button);
        return true;
    }
}