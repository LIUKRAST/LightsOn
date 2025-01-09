package net.frozenblock.lib.blocknet.setting;

import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.text.DecimalFormat;
import java.util.function.Supplier;

/**
 * A Ranged float Setting. Rendered as a slider in the GUI
 * @since 1.0
 * @author LiukRast
 * */
public class RangedBlockNetSetting extends BlockNetSetting<Float> {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_config.png");

    private final float min, max;

    boolean dragging = false;

    public RangedBlockNetSetting(String key, float max, Supplier<Float> getter) {
        this(key, 0, max, getter);
    }

    public RangedBlockNetSetting(String key, float min, float max, Supplier<Float> getter) {
        super(key, getter);
        if(min >= max) throw new IllegalStateException("Min should not be equal or larger than max");
        this.min = min;
        this.max = max;
    }

    @Override
    public String getTitleTip() {
        final DecimalFormat df = new DecimalFormat("0.######");
        return String.format("[%s -> %s]", df.format(this.min), df.format(this.max));
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putFloat(getKey(), getValue());
    }

    @Override
    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY) {

        super.render(graphics, leftPos, topPos, mouseX, mouseY);
        int progress = (int)((getValue()-min)/(max-min)*120);
        boolean hovered = dragging || mouseY >= 0 && mouseY < 11 && mouseX >= leftPos+4 && mouseX < leftPos+132;
        graphics.blit(TEXTURE, leftPos+4, topPos, 16, 160, 128, 16);
        graphics.blit(TEXTURE, leftPos+4 + progress, topPos, hovered ? 8 : 0, 160, 8, 11);
        final DecimalFormat df = new DecimalFormat("0.##");
        graphics.drawString(Minecraft.getInstance().font, df.format(getValue()), leftPos+133, topPos+2, -1);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, int leftPos, int topPos) {
        setValue((float) Math.clamp(getValue() + scrollY, min, max));
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int leftPos, int topPos) {
        this.dragging = true;
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int leftPos, int topPos) {
        this.dragging = false;
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY, int leftPos, int topPos) {
        if(!this.dragging) return;
        double fMouseX = (mouseX - leftPos - 4)/120;
        setValue((float) Math.clamp(fMouseX*(max - min)+min, min, max));
    }
}
