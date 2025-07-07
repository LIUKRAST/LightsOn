package net.liukrast.lights.on.blocknet;

import net.liukrast.lib.blocknet.BlockNetPole;
import net.liukrast.lights.LightsOnConstants;
import net.neoforged.neoforge.capabilities.BlockCapability;

public class BlockNetCapabilities {
    public static final BlockCapability<BlockNetPole, Void> BLOCKNET_POLE =
            BlockCapability.createVoid(LightsOnConstants.id("blocknet_pole"), BlockNetPole.class);
}
