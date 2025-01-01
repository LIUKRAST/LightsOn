package net.frozenblock.lightsOn.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FloppyDisksUtils {
    public static final TagKey<Item> FLOPPY_TAG = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.parse("c:tools/floppy_disks"));
}
