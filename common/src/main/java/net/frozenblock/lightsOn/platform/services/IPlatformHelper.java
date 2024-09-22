package net.frozenblock.lightsOn.platform.services;

import net.minecraft.world.item.CreativeModeTab;

@SuppressWarnings("unused")
public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }


    CreativeModeTab.Builder ghetCreativeTabBuilder();
}