package net.liukrast.lib.blocknet.setting;

import com.mojang.serialization.Codec;
import net.liukrast.lib.blocknet.BlockNetSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A float setting including a text box
 * @since 1.0
 * @author LiukRast
 * */
public class FloatBlockNetSetting extends BlockNetSetting<Float> {

    public FloatBlockNetSetting(String key, Supplier<Float> getter, BiConsumer<Float, Integer> setter) {
        super(key, getter, setter);
    }

    @Override
    public String getTitleTip() {
        return "";
    }

    @Override
    public void initGui(List<AbstractWidget> widgets, int leftPos, int topPos) {
        var box = new EditBox(Minecraft.getInstance().font, leftPos, topPos, 80, getHeight(), Component.empty());
        box.setMaxLength(128);
        box.setValue(String.valueOf(getValue()));
        box.setResponder(text -> {
            try {
                setValue(Float.parseFloat(text));
                box.setTextColor(14737632);
                setValid(true);
            } catch (NumberFormatException e) {
                setValid(false);
                box.setTextColor(16733525);
            }
        });
        widgets.add(box);
    }

    @Override
    public Codec<Float> codec() {
        return Codec.FLOAT;
    }
}
