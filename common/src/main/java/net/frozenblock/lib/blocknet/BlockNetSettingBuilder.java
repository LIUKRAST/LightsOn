package net.frozenblock.lib.blocknet;

import java.util.LinkedHashSet;
import java.util.Set;

public class BlockNetSettingBuilder {
    private final Set<BlockNetSetting<?>> settings = new LinkedHashSet<>();


    public BlockNetSettingBuilder add(BlockNetSetting<?> setting) {
        settings.add(setting);
        return this;
    }

    public Set<BlockNetSetting<?>> getSettings() {
        return this.settings;
    }
}
