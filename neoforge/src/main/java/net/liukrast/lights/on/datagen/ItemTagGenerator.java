package net.liukrast.lights.on.datagen;

import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.world.item.BlockNetWrenchUtils;
import net.liukrast.lights.on.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper fileHelper) {
        super(output, lookupProvider, blockTags, LightsOnConstants.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockNetWrenchUtils.WRENCH_TAG)
                .add(RegisterItems.BLOCKNET_WRENCH);
    }
}
