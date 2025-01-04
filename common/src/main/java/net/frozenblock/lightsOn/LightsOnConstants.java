package net.frozenblock.lightsOn;

import net.frozenblock.lightsOn.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightsOnConstants {
    public static final String MOD_ID = "lights_on";
    public static final String MOD_NAME = "Lights On";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final String PACKET_VERSION = Services.PLATFORM.getModVersion();

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
