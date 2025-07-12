package net.liukrast.lights.on.platform;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.platform.services.IPlatformHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModList;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getModVersion() {
        return ModList.get().getModContainerById(LightsOnConstants.MOD_ID)
                .map(container -> container.getModInfo().getVersion().toString()).orElseThrow();
    }

    @Override
    public CreativeModeTab.Builder getCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }
}