package net.frozenblock.lib.blocknet;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public abstract class BlockNetSetting<T> {
    private final String key;
    private final Supplier<T> getter;
    private Component title = null;

    private T value;

    private boolean isValid = true;

    public BlockNetSetting(String key, Supplier<T> getter) {
        this.key = key;
        this.getter = getter;
        this.value = getter.get();
    }

    public void initGui(List<AbstractWidget> widgets, int leftPos, int topPos, Font font) {}

    public abstract String getTitleTip();

    public abstract void save(CompoundTag tag);

    public int getHeight() {
        return 16;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int leftPos, int topPos) {
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int leftPos, int topPos) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, int leftPos, int topPos) {
        return false;
    }

    public void mouseMoved(double mouseX, double mouseY, int leftPos, int topPos) {}

    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY) {}

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void resetValue() {
        this.value = getter.get();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BlockNetSetting<?> other)) return false;
        return other.key.equals(this.key);
    }

    public Component getTitle() {
        if(title == null) title = Component.translatable("blocknet.settingType." + key).append(net.minecraft.network.chat.Component.literal(" " + getTitleTip()).withStyle(ChatFormatting.GRAY));
        return title;
    }
}
