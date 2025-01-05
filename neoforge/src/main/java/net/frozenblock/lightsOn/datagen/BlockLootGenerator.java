package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootGenerator extends BlockLootSubProvider {
    protected BlockLootGenerator(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(RegisterBlocks.BLOCKNET_INTERFACE);
        dropSelf(RegisterBlocks.BLOCKNET_LINK);
        dropSelf(RegisterBlocks.LIGHT_BEAM);
        dropSelf(RegisterBlocks.WORKLIGHT_STAND);
        dropSelf(RegisterBlocks.BLUE_NEON);
        dropSelf(RegisterBlocks.RED_NEON);
        dropSelf(RegisterBlocks.YELLOW_NEON);
        dropSelf(RegisterBlocks.GREEN_NEON);
        dropSelf(RegisterBlocks.WHITE_NEON);
        dropSelf(RegisterBlocks.LIGHT_GRAY_NEON);
        dropSelf(RegisterBlocks.GRAY_NEON);
        dropSelf(RegisterBlocks.BLACK_NEON);
        dropSelf(RegisterBlocks.BROWN_NEON);
        dropSelf(RegisterBlocks.ORANGE_NEON);
        dropSelf(RegisterBlocks.LIME_NEON);
        dropSelf(RegisterBlocks.CYAN_NEON);
        dropSelf(RegisterBlocks.LIGHT_BLUE_NEON);
        dropSelf(RegisterBlocks.PURPLE_NEON);
        dropSelf(RegisterBlocks.MAGENTA_NEON);
        dropSelf(RegisterBlocks.PINK_NEON);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(LightsOnConstants.MOD_ID))::iterator;
    }
}
