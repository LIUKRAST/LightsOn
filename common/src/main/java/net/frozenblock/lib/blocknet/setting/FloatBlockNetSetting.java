package net.frozenblock.lib.blocknet.setting;

import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public class FloatBlockNetSetting extends BlockNetSetting<Float> {

    public FloatBlockNetSetting(String key, Supplier<Float> getter) {
        super(key, getter);
    }

    @Override
    public String getTitleTip() {
        return "";
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putFloat(getKey(), getValue());
    }

    @Override
    public void initGui(List<AbstractWidget> widgets, int leftPos, int topPos, Font font) {
        var box = new EditBox(font, leftPos, topPos, 80, getHeight(), Component.empty());
        box.setMaxLength(128);
        box.setValue(String.valueOf(getValue()));
        box.setResponder(text -> {
            try {
                setValue(Float.parseFloat(text));
                setValid(true);
            } catch (NumberFormatException e) {
                setValid(false);
            }
        });
        widgets.add(box);
    }
}
