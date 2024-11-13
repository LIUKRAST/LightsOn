package net.frozenblock.lightsOn;

import net.frozenblock.lightsOn.registry.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@SuppressWarnings("unused")
@Mod(LightsOnConstants.MOD_ID)
public class LightsOn {

    public LightsOn(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.register(this);
        modEventBus.register(RegisterNetworking.class);
    }

    @SubscribeEvent
    public void onInitialize(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void registryEvent(RegisterEvent event) {
        event.register(BuiltInRegistries.BLOCK.key(), helper -> RegisterBlocks.register());
        event.register(BuiltInRegistries.BLOCK_ENTITY_TYPE.key(), helper -> RegisterBlockEntities.register());
        event.register(BuiltInRegistries.CREATIVE_MODE_TAB.key(), helper -> RegisterCreativeModeTabs.register());
        event.register(BuiltInRegistries.ITEM.key(), helper -> RegisterItems.register());
        event.register(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), helper -> RegisterDataComponents.register());
    }
}
