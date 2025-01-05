package net.frozenblock.lightsOn.blocknet;

import net.frozenblock.lib.blocknet.BlockNetPole;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.neoforged.neoforge.capabilities.BlockCapability;

public class BlockNetCapabilities {
    public static final BlockCapability<BlockNetPole, Void> BLOCKNET_POLE =
            BlockCapability.createVoid(LightsOnConstants.id("blocknet_pole"), BlockNetPole.class);
}
