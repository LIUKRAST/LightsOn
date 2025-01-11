package net.frozenblock.lib.blocknet.setting;

import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.frozenblock.lightsOn.screen.ColorMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * A blocknet settings for color change. Exported as integer
 * @since 1.0
 * @author LiukRast
 * */
public class ColorBlockNetSetting extends BlockNetSetting<Integer> {
    public ColorBlockNetSetting(String key, Supplier<Integer> getter) {
        super(key, getter);
    }

    private EditBox channelA,channelB,channelC;
    private ColorMode colorMode = ColorMode.RGB;

    @Override
    public String getTitleTip() {
        return switch (colorMode) {
            case HEX -> "[0 -> FFFFFF]";
            case HSL -> "[H max 359; S, L max 100]";
            case INT -> "[0 -> 16777215]";
            case RGB -> "R,G,B max 255";
        };
    }

    @Override
    public void initGui(List<AbstractWidget> widgets, int leftPos, int topPos) {
        CycleButton<ColorMode> colorButton = CycleButton.builder(ColorMode::getTitle)
                .withValues(ColorMode.values()).withInitialValue(colorMode).displayOnlyValue()
                .create(leftPos, topPos, 30, 16, Component.empty(), this::switchColorMode);
        widgets.add(colorButton);
        var font = Minecraft.getInstance().font;
        channelA = new EditBox(font, leftPos + 32, topPos, 30, getHeight(), Component.empty());
        channelA.setMaxLength(3);
        channelA.setResponder(s -> setChanged());
        widgets.add(channelA);
        channelB = new EditBox(font, leftPos + 64, topPos, 30, getHeight(), Component.empty());
        channelB.setMaxLength(3);
        channelB.setResponder(s -> setChanged());
        widgets.add(channelB);
        channelC = new EditBox(font, leftPos + 96, topPos, 30, getHeight(), Component.empty());
        channelC.setMaxLength(3);
        channelC.setResponder(s -> setChanged());
        widgets.add(channelC);
        switchColorMode(null, colorMode);
    }

    private void switchColorMode(CycleButton<ColorMode> unused, ColorMode b) {
        int[] hsl = ColorMode.int2hsl(getValue());
        int[] rgb = ColorMode.int2rgb(getValue());
        this.colorMode = b;
        if(b == ColorMode.HEX || b == ColorMode.INT) {
            channelA.setWidth(60);
            channelA.setMaxLength(b == ColorMode.HEX ? 6 : 8);
            channelB.visible = false;
            channelC.visible = false;
        } else {
            channelA.setWidth(30);
            channelA.setMaxLength(3);
            channelB.visible = true;
            channelC.visible = true;
        }
        channelA.setValue(switch (colorMode) {
            case RGB -> String.valueOf(rgb[0]);
            case HSL -> String.valueOf(hsl[0]);
            case HEX -> Integer.toHexString(getValue());
            case INT -> getValue().toString();
        });
        channelB.setValue(switch (colorMode) {
            case RGB -> String.valueOf(rgb[1]);
            case HSL -> String.valueOf(hsl[1]);
            case HEX -> Integer.toHexString(getValue());
            case INT -> getValue().toString();
        });
        channelC.setValue(switch (colorMode) {
            case RGB -> String.valueOf(rgb[2]);
            case HSL -> String.valueOf(hsl[2]);
            case HEX -> Integer.toHexString(getValue());
            case INT -> getValue().toString();
        });
    }

    private void setChanged() {
        try {
            switch (colorMode) {
                case RGB -> {
                    int r = Integer.parseInt(channelA.getValue());
                    int g = Integer.parseInt(channelB.getValue());
                    int b = Integer.parseInt(channelC.getValue());
                    if(r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                        setValid(false);
                        return;
                    }
                    setValue(ColorMode.rgb2int(r,g,b));
                }
                case HSL -> {
                    int h = Integer.parseInt(channelA.getValue());
                    int s = Integer.parseInt(channelB.getValue());
                    int l = Integer.parseInt(channelC.getValue());
                    if(h < 0 || h > 359 || s < 0 || s > 100 || l < 0 || l > 100) {
                        setValid(false);
                        return;
                    }
                    setValue(ColorMode.hsl2int(h,s,l));
                }
                case INT -> {
                    int i = Integer.parseInt(channelA.getValue());
                    if(i < 0 || i > 16777215) {
                        setValid(false);
                        return;
                    }
                    setValue(i);
                }
                case HEX -> {
                    String hex = channelA.getValue();
                    if(hex.startsWith("#")) hex = hex.substring(1);
                    setValue(Integer.parseInt(hex, 16));
                }
            }
            setValid(true);
        } catch (NumberFormatException e) {
            setValid(false);
        }
    }

    @Override
    public void setValid(boolean valid) {
        int color = valid ? 14737632 : 16733525;
        channelA.setTextColor(color);
        channelB.setTextColor(color);
        channelC.setTextColor(color);
        super.setValid(valid);
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt(getKey(), getValue());
    }

    @Override
    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY) {
        super.render(graphics, leftPos, topPos, mouseX, mouseY);
        graphics.fill(leftPos + 132, topPos, leftPos + 132 + 16, topPos + 16, (255 << 24) | (getValue() & 0xFFFFFF));
        graphics.renderOutline(leftPos + 132, topPos, 16, 16, -16777216);
    }
}
