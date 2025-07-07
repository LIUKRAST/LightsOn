package net.liukrast.lights.on.platform;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.NewBlockNetInterfaceScreen;
import net.liukrast.lights.on.platform.services.IPlatformHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
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
    public CreativeModeTab.Builder getCreativeTabBuilder() {
        return CreativeModeTab.builder();
    }

    @Override
    public Screen getScreen() {
        return new NewBlockNetInterfaceScreen(Component.empty());
    }
}