package net.frozenblock.lib.blocknet;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A BlockNet setting Builder. Allows registering block settings.
 * @since 1.0
 * @author LiukRast
 * */
public class BlockNetSettingBuilder {
    private final Set<BlockNetSetting<?>> settings = new LinkedHashSet<>();

    /**
     * Allows you to add settings to your {@link BlockNetConfigurable}.
     * It's important to note that a setting cannot have the key {@code duration} which is used for interpolation
     * and two settings cannot have the same {@code key}.
     * This will result in a {@link IllegalStateException}
     * @param setting A new setting to be registered
     * @return The instance of the builder, so that it can be invoked multiple times.
     * @since 1.0
     * @author LiukRast
     * */
    public BlockNetSettingBuilder add(BlockNetSetting<?> setting) {
        if(setting.getKey().equals("duration")) throw new IllegalStateException("You cannot register a BlockNetSetting under the key \"duration\"");
        for(BlockNetSetting<?> setting1 : settings) {
            if(setting1.getKey().equals(setting.getKey())) throw new IllegalStateException("You cannot register two BlockNetSettings with the same key");
        }
        settings.add(setting);
        return this;
    }

    /**
     * @return The list of all settings registered
     * @since 1.0
     * @author LiukRast
     * */
    public Set<BlockNetSetting<?>> getSettings() {
        return this.settings;
    }
}
