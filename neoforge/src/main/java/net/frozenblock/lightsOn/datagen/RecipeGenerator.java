package net.frozenblock.lightsOn.datagen;

import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.registry.RegisterBlocks;
import net.frozenblock.lightsOn.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.BLOCKNET_LINK, 4)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', Items.CHAIN)
                .define('Y', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.BLOCKNET_INTERFACE)
                .pattern("XXX")
                .pattern("YZK")
                .pattern("XXX")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.GLASS_PANE)
                .define('Z', Items.REDSTONE_BLOCK)
                .define('K', RegisterBlocks.BLOCKNET_LINK)
                .unlockedBy("has_blocknet_link", has(RegisterBlocks.BLOCKNET_LINK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.LIGHT_BEAM)
                .pattern("XXX")
                .pattern("YZK")
                .pattern("XXX")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.GLASS_PANE)
                .define('Z', Items.REDSTONE_LAMP)
                .define('K', RegisterBlocks.BLOCKNET_LINK)
                .unlockedBy("has_blocknet_link", has(RegisterBlocks.BLOCKNET_LINK))
                .unlockedBy("has_redstone_lamp", has(Items.REDSTONE_LAMP))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, RegisterItems.BLOCKNET_WRENCH)
                .pattern(" X ")
                .pattern(" ZX")
                .pattern("Y  ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.STICK)
                .define('Z', RegisterBlocks.BLOCKNET_LINK)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.BLUE_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.PAPER)
                .define('Z', Items.BLUE_DYE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("blue_floppy_disk_1"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.BLUE_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.BLUE_DYE)
                .define('Z', Items.PAPER)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("blue_floppy_disk_2"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.WORKLIGHT_STAND)
                .pattern("X X")
                .pattern("YYY")
                .pattern(" Y ")
                .define('X', Items.COPPER_BULB)
                .define('Y', Items.STICK)
                .unlockedBy("has_copper_bulb", has(Items.COPPER_BULB))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.RED_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.PAPER)
                .define('Z', Items.RED_DYE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("red_floppy_disk_1"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.RED_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.RED_DYE)
                .define('Z', Items.PAPER)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("red_floppy_disk_2"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.YELLOW_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.PAPER)
                .define('Z', Items.YELLOW_DYE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("yellow_floppy_disk_1"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterItems.YELLOW_FLOPPY_DISK)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Z ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.YELLOW_DYE)
                .define('Z', Items.PAPER)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput, LightsOnConstants.id("yellow_floppy_disk_2"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.RED_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.RED_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.BLUE_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.BLUE_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.YELLOW_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.YELLOW_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.WHITE_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.WHITE_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.LIGHT_GRAY_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.LIGHT_GRAY_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.GRAY_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.GRAY_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.BLACK_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.BLACK_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.BROWN_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.BROWN_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.ORANGE_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.ORANGE_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.LIME_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.LIME_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.GREEN_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.GREEN_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.CYAN_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.CYAN_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.LIGHT_BLUE_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.LIGHT_BLUE_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.PURPLE_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.PURPLE_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.MAGENTA_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.MAGENTA_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegisterBlocks.PINK_NEON, 2)
                .pattern(" X ")
                .pattern("YZY")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.END_ROD)
                .define('Z', Items.PINK_DYE)
                .unlockedBy("has_end_rod", has(Items.END_ROD))
                .save(recipeOutput);
    }
}
