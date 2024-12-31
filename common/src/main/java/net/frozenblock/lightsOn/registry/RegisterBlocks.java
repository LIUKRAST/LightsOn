package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.BNIBlock;
import net.frozenblock.lightsOn.block.BNLBlock;
import net.frozenblock.lightsOn.block.LightBeamBlock;
import net.frozenblock.lightsOn.block.WorklightStandBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RegisterBlocks {
    public static final Block LIGHT_BEAM = new LightBeamBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape());
    public static final Block BLOCKNET_INTERFACE = new BNIBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape().lightLevel((b) -> 5));
    public static final Block BLOCKNET_LINK = new BNLBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape());
    public static final Block WORKLIGHT_STAND = new WorklightStandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape());

    public static void register() {
        registerBlockWithItem(LightsOnConstants.id("light_beam"), LIGHT_BEAM);
        registerBlockWithItem(LightsOnConstants.id("blocknet_interface"), BLOCKNET_INTERFACE);
        registerBlockWithItem(LightsOnConstants.id("blocknet_link"), BLOCKNET_LINK);
        registerBlockWithItem(LightsOnConstants.id("worklight_stand"), WORKLIGHT_STAND);
    }

    public static void registerBlockWithItem(ResourceLocation location, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, location, block);
        Registry.register(BuiltInRegistries.ITEM, location, new BlockItem(block, new Item.Properties()));
    }
}
