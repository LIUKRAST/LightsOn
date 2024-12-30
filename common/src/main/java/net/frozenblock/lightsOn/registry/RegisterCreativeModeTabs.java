package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RegisterCreativeModeTabs {
    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), LightsOnConstants.id("creative_tab"));
    public static final CreativeModeTab TAB = Services.PLATFORM.ghetCreativeTabBuilder()
            .icon(() -> new ItemStack(RegisterBlocks.LIGHT_BEAM))
            .title(Component.translatable("itemGroup.lights_on_tab"))
            .displayItems((parameters, output) -> {
                output.accept(RegisterBlocks.LIGHT_BEAM);
                output.accept(RegisterBlocks.BLOCKNET_INTERFACE);
                output.accept(RegisterBlocks.BLOCKNET_LINK);
                output.accept(RegisterItems.BLOCKNET_WRENCH);
                output.accept(RegisterItems.FLOPPY_DISK);
            })
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, TAB);
    }
}