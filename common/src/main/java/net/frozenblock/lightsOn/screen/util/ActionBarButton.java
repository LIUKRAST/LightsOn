package net.frozenblock.lightsOn.screen.util;

import net.frozenblock.lightsOn.screen.BlockNetInterfaceScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class ActionBarButton {
    private final String text;
    private final ActionBarSubButton[] subButtons;
    private boolean hovered = false;

    public ActionBarButton(String text, ActionBarSubButton[] subButtons) {
        this.text = text;
        this.subButtons = subButtons;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, int xOffset, BlockNetInterfaceScreen screen, Font font, boolean isHoveredY) {
        int w = getWidth(font);
        boolean isHoveredX = mouseX >= screen.width/2 - screen.getHalfWindowWidth() + xOffset && mouseX < screen.width/2 - screen.getHalfWindowWidth() + w + xOffset;
        boolean isHovered = hovered || isHoveredX && isHoveredY;
        if(!hovered && isHovered) hovered = true;

        int x = screen.width/2 - screen.getHalfWindowWidth()+1+xOffset;
        int y = screen.height/2 - screen.getHalfWindowHeight()+1;
        if(isHovered) guiGraphics.fill(x, y, x+w, y+12, -1);
        else guiGraphics.renderOutline(x, y, w, 12, -1);
        guiGraphics.drawString(font, text, x+2, y+2, isHovered ? -16777216 : -1, false);

        if(isHovered) {
            int maxWidth = 0;
            for(ActionBarSubButton subButton : subButtons) {
                int t_w = subButton.getWidth(font);
                if(maxWidth < t_w) maxWidth = t_w;
            }
            boolean subHovered = mouseX > screen.width/2 - screen.getHalfWindowWidth() + xOffset && mouseX < screen.width/2 - screen.getHalfWindowWidth() + maxWidth + xOffset
                    & mouseY >= screen.height/2-screen.getHalfWindowHeight()+11 && mouseY < screen.height/2-screen.getHalfWindowHeight()+((subButtons.length+1)*12);
            if(!subHovered) hovered = false;
            guiGraphics.fill(x, y+12+1, x+maxWidth, y+((subButtons.length+1)*12)+1, -16777216);
            guiGraphics.renderOutline(x, y+12+1, maxWidth, (subButtons.length*12), -1);
            int yOffset = 0;
            for(ActionBarSubButton subButton : subButtons) {
                boolean subButtonHovered = mouseY >= y+yOffset+12 && mouseY < y+yOffset+24;
                subButton.render(guiGraphics, x, y+yOffset+12, font, subButtonHovered, maxWidth);
                yOffset+=12;
            }
        }
    }

    public int getWidth(Font font) {
        return font.width(text) + 3;
    }
}
