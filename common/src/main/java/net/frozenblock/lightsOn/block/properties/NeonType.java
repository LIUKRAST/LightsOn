package net.frozenblock.lightsOn.block.properties;

import net.minecraft.util.StringRepresentable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public enum NeonType implements StringRepresentable {
    DOUBLE("double"),
    BOTTOM("bottom"),
    TOP("top"),
    NONE("none");

    private final String name;

    NeonType(String name) {
        this.name = name;
    }

    public static NeonType fromTopAndBottom(boolean top, boolean bottom) {
        if(top && bottom) return NONE;
        if(top) return BOTTOM;
        return bottom ? TOP : DOUBLE;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
