package net.frozenblock.lightsOn.screen.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ActionBarSubButton {
    private final Component text;
    private final Runnable runnable;

    public ActionBarSubButton(Component text, Runnable runnable) {
        this.text = text;
        this.runnable = runnable;
    }

    public int getWidth(Font font) {
        return font.width(text) + 3;
    }

    public void render(GuiGraphics guiGraphics, int x, int y, Font font, boolean hovered, int maxWidth) {
        if(hovered) guiGraphics.fill(x, y+1, x+maxWidth, y+13, -1);
        guiGraphics.drawString(font, text, x+2, y+3, hovered ? -16777216 : -1, false);
    }
}
