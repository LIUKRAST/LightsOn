package net.liukrast.lights.on.client.gui.screens;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.world.inventory.BlockNetMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.NonnullDefault;

import java.util.ArrayList;
import java.util.List;

@NonnullDefault
public class BlockNetScreen extends Screen implements MenuAccess<BlockNetMenu> {
    /* All data is stored statically so that it can be updated through packets */
    public static List<String> PROJECTS = new ArrayList<>();
    public static String CURRENT_PROJECT = null;

    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_interface.png");
    private final BlockNetMenu menu;

    /**/
    private int hbt,hbb,wbl,wbr;

    public BlockNetScreen(BlockNetMenu menu, Inventory ignored, Component title) {
        super(title);
        this.menu = menu;
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        int top = 16;
        int right = 7;
        int left = 7;
        int bottom = 4;



        hbt = height/top;
        hbb = height/bottom;
        wbl = width/left;
        wbr = width/right;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        int sep = 2;

        guiGraphics.fill(0,0, width, hbt, 0xFF575757);
        makeArea(guiGraphics, 0, hbt, wbl, hbt + (height - hbt - hbb)/sep, Component.literal("Projects"));
        makeArea(guiGraphics, 0, (height - hbt - hbb)/sep, wbl, height, Component.literal("Element"));
        makeArea(guiGraphics, width-wbr, hbt, width, height, Component.literal("Poles"));
        makeArea(guiGraphics, wbl, height-hbb, width-wbr, height, Component.literal("Timeline"));

        boolean hovered = mouseX > wbl-10 && mouseX < wbl && mouseY > hbt && mouseY < hbt+10;
        guiGraphics.blit(TEXTURE, wbl-10, hbt, 0, hovered?10:0, 10, 10);
    }

    private void makeArea(GuiGraphics guiGraphics, int x, int y, int maxX, int maxY, Component title) {
        guiGraphics.fill(x, y, maxX, y+10, 0xFF383838);
        guiGraphics.fill(x, y+10, maxX, maxY, 0xFF575757);
        guiGraphics.drawString(this.font, title,x+1,y+1, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderMenuBackground(GuiGraphics partialTick) {}

    @Override
    protected void renderBlurredBackground(float partialTick) {}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public BlockNetMenu getMenu() {
        return menu;
    }

    @Override
    public void onClose() {
        if(this.minecraft == null || this.minecraft.player == null) return;
        this.minecraft.player.closeContainer();
        super.onClose();
    }
}
