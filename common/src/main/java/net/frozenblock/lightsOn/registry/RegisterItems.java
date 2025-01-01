package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.item.BlockNetWrench;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class RegisterItems {
    public static final Item BLOCKNET_WRENCH = new BlockNetWrench(new Item.Properties().stacksTo(1));
    public static final Item BLUE_FLOPPY_DISK = new Item(new Item.Properties().stacksTo(16));
    public static final Item RED_FLOPPY_DISK = new Item(new Item.Properties().stacksTo(16));
    public static final Item YELLOW_FLOPPY_DISK = new Item(new Item.Properties().stacksTo(16));

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, LightsOnConstants.id("blocknet_wrench"), BLOCKNET_WRENCH);
        Registry.register(BuiltInRegistries.ITEM, LightsOnConstants.id("blue_floppy_disk"), BLUE_FLOPPY_DISK);
        Registry.register(BuiltInRegistries.ITEM, LightsOnConstants.id("red_floppy_disk"), RED_FLOPPY_DISK);
        Registry.register(BuiltInRegistries.ITEM, LightsOnConstants.id("yellow_floppy_disk"), YELLOW_FLOPPY_DISK);
    }
}
