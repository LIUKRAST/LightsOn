package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.block.NeonBlock;
import net.frozenblock.lightsOn.block.properties.NeonType;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LightsOnConstants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        models().singleTexture(
                "neon_off",
                LightsOnConstants.id("block/template_neon"),
                "all",
                LightsOnConstants.id("block/neon_off")
        );
        createNeon(RegisterBlocks.BLUE_NEON);
        createNeon(RegisterBlocks.RED_NEON);
        createNeon(RegisterBlocks.YELLOW_NEON);
        createNeon(RegisterBlocks.GREEN_NEON);
        createNeon(RegisterBlocks.WHITE_NEON);
        createNeon(RegisterBlocks.LIGHT_GRAY_NEON);
        createNeon(RegisterBlocks.GRAY_NEON);
        createNeon(RegisterBlocks.BLACK_NEON);
        createNeon(RegisterBlocks.BROWN_NEON);
        createNeon(RegisterBlocks.ORANGE_NEON);
        createNeon(RegisterBlocks.LIME_NEON);
        createNeon(RegisterBlocks.CYAN_NEON);
        createNeon(RegisterBlocks.LIGHT_BLUE_NEON);
        createNeon(RegisterBlocks.PURPLE_NEON);
        createNeon(RegisterBlocks.MAGENTA_NEON);
        createNeon(RegisterBlocks.PINK_NEON);
    }

    private void createNeon(Block block) {
        final var id = BuiltInRegistries.BLOCK.getKey(block);
        models().singleTexture(
                id.getPath(),
                LightsOnConstants.id("block/template_neon"),
                "all",
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath())
        );
        itemModels().singleTexture(
                id.getPath(),
                LightsOnConstants.id("item/template_neon"),
                "all",
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath())
        );
        getMultipartBuilder(block)
                .part()
                .modelFile(models().getExistingFile(modLoc("block/" + id.getPath())))
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Y)
                .condition(BlockStateProperties.POWERED, true)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/" + id.getPath())))
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.X)
                .condition(BlockStateProperties.POWERED, true)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/" + id.getPath())))
                .rotationX(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Z)
                .condition(BlockStateProperties.POWERED, true)
                .end()




                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_off")))
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Y)
                .condition(BlockStateProperties.POWERED, false)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_off")))
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.X)
                .condition(BlockStateProperties.POWERED, false)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_off")))
                .rotationX(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Z)
                .condition(BlockStateProperties.POWERED, false)
                .end()


                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Y)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.BOTTOM)
                .end()
                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .rotationX(180)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Y)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.TOP)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.X)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.BOTTOM)
                .end()
                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .rotationX(-90)
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.X)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.TOP)
                .end()

                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .rotationX(-90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Z)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.BOTTOM)
                .end()
                .part()
                .modelFile(models().getExistingFile(modLoc("block/neon_base")))
                .rotationX(90)
                .addModel()
                .condition(BlockStateProperties.AXIS, Direction.Axis.Z)
                .condition(NeonBlock.TYPE, NeonType.DOUBLE, NeonType.TOP)
                .end();
    }
}
