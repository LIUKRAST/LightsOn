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
                output.accept(RegisterItems.BLOCKNET_WRENCH);
                output.accept(RegisterItems.RED_FLOPPY_DISK);
                output.accept(RegisterItems.YELLOW_FLOPPY_DISK);
                output.accept(RegisterItems.BLUE_FLOPPY_DISK);
                output.accept(RegisterBlocks.BLOCKNET_LINK);
                output.accept(RegisterBlocks.LIGHT_BEAM);
                output.accept(RegisterBlocks.BLOCKNET_INTERFACE);
                output.accept(RegisterBlocks.WORKLIGHT_STAND);
                output.accept(RegisterBlocks.WHITE_NEON);
                output.accept(RegisterBlocks.LIGHT_GRAY_NEON);
                output.accept(RegisterBlocks.GRAY_NEON);
                output.accept(RegisterBlocks.BLACK_NEON);
                output.accept(RegisterBlocks.BROWN_NEON);
                output.accept(RegisterBlocks.RED_NEON);
                output.accept(RegisterBlocks.ORANGE_NEON);
                output.accept(RegisterBlocks.YELLOW_NEON);
                output.accept(RegisterBlocks.LIME_NEON);
                output.accept(RegisterBlocks.GREEN_NEON);
                output.accept(RegisterBlocks.CYAN_NEON);
                output.accept(RegisterBlocks.LIGHT_BLUE_NEON);
                output.accept(RegisterBlocks.BLUE_NEON);
                output.accept(RegisterBlocks.PURPLE_NEON);
                output.accept(RegisterBlocks.MAGENTA_NEON);
                output.accept(RegisterBlocks.PINK_NEON);
            })
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, TAB);
    }
}