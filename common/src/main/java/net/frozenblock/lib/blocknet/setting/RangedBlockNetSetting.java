package net.frozenblock.lib.blocknet.setting;

import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.text.DecimalFormat;
import java.util.function.Supplier;

public class RangedBlockNetSetting extends BlockNetSetting<Float> {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_config.png");

    private final float min, max;

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
    public void render(GuiGraphics graphics, int leftPos, int topPos) {
        // 0 -> 120
        int progress = (int)((getValue()-min)/(max-min)*120);
        graphics.blit(TEXTURE, leftPos+4, topPos, 16, 160, 128, 16);
        graphics.blit(TEXTURE, leftPos+4 + progress, topPos, 0, 160, 8, 11);
        final DecimalFormat df = new DecimalFormat("0.######");
        graphics.drawString(Minecraft.getInstance().font, df.format(getValue()), leftPos+133, topPos+2, -1);
    }
}
