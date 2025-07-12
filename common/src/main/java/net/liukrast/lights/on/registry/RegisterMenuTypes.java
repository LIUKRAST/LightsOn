package net.liukrast.lights.on.registry;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.world.inventory.BlockNetMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class RegisterMenuTypes {
    public static final MenuType<BlockNetMenu> BLOCK_NET = new MenuType<>(BlockNetMenu::new, FeatureFlags.DEFAULT_FLAGS);

    public static void register() {
        Registry.register(BuiltInRegistries.MENU, LightsOnConstants.id("block_net"), BLOCK_NET);
    }
}
