package net.frozenblock.lightsOn.registry;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RegisterBlocks {
    public static final Block LIGHT_BEAM = new LightBeamBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape().lightLevel(b -> b.getValue(LightBeamBlock.POWERED) ? 10 : 0));
    public static final Block BLOCKNET_INTERFACE = new BNIBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape().lightLevel((b) -> 5));
    public static final Block BLOCKNET_LINK = new BNLBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape());
    public static final Block WORKLIGHT_STAND = new WorklightStandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).dynamicShape().lightLevel(b -> b.getValue(WorklightStandBlock.POWERED) ? 10 : 0));
    public static final NeonBlock BLUE_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock RED_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock YELLOW_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock GREEN_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   WHITE_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   LIGHT_GRAY_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   GRAY_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   BLACK_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   BROWN_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   ORANGE_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   LIME_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   CYAN_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   LIGHT_BLUE_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   PURPLE_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   MAGENTA_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));
    public static final NeonBlock   PINK_NEON = new NeonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).dynamicShape().lightLevel(b -> b.getValue(NeonBlock.POWERED) ? 14 : 0));

    public static void register() {
        registerBlockWithItem(LightsOnConstants.id("light_beam"), LIGHT_BEAM);
        registerBlockWithItem(LightsOnConstants.id("blocknet_interface"), BLOCKNET_INTERFACE);
        registerBlockWithItem(LightsOnConstants.id("blocknet_link"), BLOCKNET_LINK);
        registerBlockWithItem(LightsOnConstants.id("worklight_stand"), WORKLIGHT_STAND);
        registerBlockWithItem(LightsOnConstants.id("blue_neon"), BLUE_NEON);
        registerBlockWithItem(LightsOnConstants.id("red_neon"), RED_NEON);
        registerBlockWithItem(LightsOnConstants.id("yellow_neon"), YELLOW_NEON);
        registerBlockWithItem(LightsOnConstants.id("green_neon"), GREEN_NEON);
        registerBlockWithItem(LightsOnConstants.id("white_neon"), WHITE_NEON);
        registerBlockWithItem(LightsOnConstants.id("light_gray_neon"), LIGHT_GRAY_NEON);
        registerBlockWithItem(LightsOnConstants.id("gray_neon"), GRAY_NEON);
        registerBlockWithItem(LightsOnConstants.id("black_neon"), BLACK_NEON);
        registerBlockWithItem(LightsOnConstants.id("brown_neon"), BROWN_NEON);
        registerBlockWithItem(LightsOnConstants.id("orange_neon"), ORANGE_NEON);
        registerBlockWithItem(LightsOnConstants.id("lime_neon"), LIME_NEON);
        registerBlockWithItem(LightsOnConstants.id("cyan_neon"), CYAN_NEON);
        registerBlockWithItem(LightsOnConstants.id("light_blue_neon"), LIGHT_BLUE_NEON);
        registerBlockWithItem(LightsOnConstants.id("purple_neon"), PURPLE_NEON);
        registerBlockWithItem(LightsOnConstants.id("magenta_neon"), MAGENTA_NEON);
        registerBlockWithItem(LightsOnConstants.id("pink_neon"), PINK_NEON);
    }

    public static void registerBlockWithItem(ResourceLocation location, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, location, block);
        Registry.register(BuiltInRegistries.ITEM, location, new BlockItem(block, new Item.Properties()));
    }
}
