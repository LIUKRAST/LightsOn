package net.frozenblock.lightsOn;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@SuppressWarnings("unused")
@OnlyIn(Dist.DEDICATED_SERVER)
@EventBusSubscriber(modid = LightsOnConstants.MOD_ID, value = Dist.DEDICATED_SERVER, bus = EventBusSubscriber.Bus.MOD)
public class LightsOnServer {

    @SubscribeEvent
    public static void onInitializeServer(FMLDedicatedServerSetupEvent event) {}
}
