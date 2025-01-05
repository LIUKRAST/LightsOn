package net.frozenblock.lightsOn.platform.services;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public interface IPlatformHelper {

    String getModVersion();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    void setRenderType(Block block, RenderType type);

    CreativeModeTab.Builder ghetCreativeTabBuilder();
}