package net.liukrast.lights.on.datagen;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LightsOnConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                RegisterBlocks.BLOCKNET_INTERFACE,
                RegisterBlocks.BLOCKNET_LINK,
                RegisterBlocks.SPOTLIGHT,
                RegisterBlocks.WORKLIGHT_STAND,
                RegisterBlocks.BLUE_NEON,
                RegisterBlocks.RED_NEON,
                RegisterBlocks.YELLOW_NEON,
                RegisterBlocks.GREEN_NEON
        );
    }
}
