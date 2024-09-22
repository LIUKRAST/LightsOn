package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class RegisterItems {
    public static final Item BLOCKNET_WRENCH = new BlockNetWrench(new Item.Properties().stacksTo(1));

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, LightsOnConstants.id("blocknet_wrench"), BLOCKNET_WRENCH);
    }
}
