package net.frozenblock.lightsOn.platform;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTab;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getModVersion() {
        return FabricLoader.getInstance().getModContainer(LightsOnConstants.MOD_ID)
                .orElseThrow().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public CreativeModeTab.Builder ghetCreativeTabBuilder() {
        return FabricItemGroup.builder();
    }
}
