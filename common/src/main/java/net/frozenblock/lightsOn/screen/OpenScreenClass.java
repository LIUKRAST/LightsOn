package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.block.LightBeamBlockEntity;
import net.minecraft.client.Minecraft;

public class OpenScreenClass {

    public static void openMovingLightSetup(LightBeamBlockEntity blockEntity) {
        Minecraft.getInstance().setScreen(new LightBeamSetupScreen(blockEntity));
    }

}
