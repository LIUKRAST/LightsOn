package net.frozenblock.lib.blocknet.setting;

import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

public class ColorBlockNetSetting extends BlockNetSetting<Integer> {
    public ColorBlockNetSetting(String key, Supplier<Integer> getter) {
        super(key, getter);
    }

    @Override
    public String getTitleTip() {
        return "";
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt(getKey(), getValue());
    }
}
