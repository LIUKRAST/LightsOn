package net.liukrast.lights;

import net.liukrast.lights.on.datagen.*;
import net.liukrast.lights.on.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(LightsOnConstants.MOD_ID)
public class LightsOn {

    public LightsOn(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.register(this);
        modEventBus.register(RegisterNetworking.class);
    }

    @SubscribeEvent
    public void initClient(FMLClientSetupEvent event) {
        LightsOnConstants.initClient();
    }

    @SubscribeEvent
    public void registryEvent(RegisterEvent event) {
        event.register(BuiltInRegistries.BLOCK.key(), helper -> RegisterBlocks.register());
        event.register(BuiltInRegistries.BLOCK_ENTITY_TYPE.key(), helper -> RegisterBlockEntityTypes.register());
        event.register(BuiltInRegistries.CREATIVE_MODE_TAB.key(), helper -> RegisterCreativeModeTabs.register());
        event.register(BuiltInRegistries.ITEM.key(), helper -> RegisterItems.register());
        event.register(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), helper -> RegisterDataComponents.register());
    }

    @SubscribeEvent
    public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        RegisterBlockEntityRenderers.register();
    }

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        var fileHelper = event.getExistingFileHelper();
        var generator = event.getGenerator();
        var packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, fileHelper));

        generator.addProvider(event.includeServer(), new ENLangGenerator(packOutput));

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(BlockLootGenerator::new, LootContextParamSets.BLOCK)), lookupProvider));

        BlockTagsProvider blockTagsProvider = new BlockTagGenerator(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ItemTagGenerator(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), fileHelper));

        generator.addProvider(event.includeServer(), new RecipeGenerator(packOutput, lookupProvider));
    }
}
