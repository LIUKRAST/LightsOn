package net.frozenblock.lightsOn.platform;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.platform.services.IPlatformHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getModVersion() {
        return ModList.get().getModContainerById(LightsOnConstants.MOD_ID)
                .map(container -> container.getModInfo().getVersion().toString()).orElseThrow();
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setRenderType(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }

    @Override
    public CreativeModeTab.Builder ghetCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }
}