package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SUGAR_GLASS.get())
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.SUGAR_GLASS_SHARD.get())
                .unlockedBy(getHasName(ModItems.SUGAR_GLASS_SHARD.get()), has(ModItems.SUGAR_GLASS_SHARD.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_SYRUP_BLOCK.get())
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.MAPLE_SYRUP_BOTTLE.get())
                .unlockedBy(getHasName(ModItems.MAPLE_SYRUP_BOTTLE.get()), has(ModItems.MAPLE_SYRUP_BOTTLE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MAPLE_SYRUP_BOTTLE.get(), 4)
                .requires(ModBlocks.MAPLE_SYRUP_BLOCK.get())
                .requires(Items.GLASS_BOTTLE, 4)
                .unlockedBy(getHasName(ModBlocks.MAPLE_SYRUP_BLOCK.get()), has(ModBlocks.MAPLE_SYRUP_BLOCK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.MAPLE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_LOG.get()), has(ModBlocks.MAPLE_LOG.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STRIPPED_MAPLE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.STRIPPED_MAPLE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_MAPLE_LOG.get()), has(ModBlocks.STRIPPED_MAPLE_LOG.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MAPLE_PLANKS.get(), 4)
                .requires(ModTags.Items.MAPLE_LOGS)
                .unlockedBy(getHasName(ModBlocks.MAPLE_LOG.get()), has(ModBlocks.MAPLE_LOG.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MAPLE_BUTTON.get())
                .requires(ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_PRESSURE_PLATE.get(), 4)
                .pattern("##")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_FENCE.get(), 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_FENCE_GATE.get())
                .pattern("S#S")
                .pattern("S#S")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MAPLE_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.MAPLE_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.MAPLE_PLANKS.get()), has(ModBlocks.MAPLE_PLANKS.get()))
                .save(recipeOutput);

        oreSmelting(recipeOutput, List.of(ModBlocks.MAPLE_SYRUP_BLOCK.get()), RecipeCategory.MISC,
                ModBlocks.SUGAR_GLASS.get(), 1.0f, 200, "sugar_glass");

        oreSmelting(recipeOutput, List.of(ModItems.VENISON.get()), RecipeCategory.FOOD,
                ModItems.COOKED_VENISON.get(), 0.35F, 200, "cooked_venison");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PURPLE_DYE, 2)
                .requires(ModBlocks.ASTER.get())
                .unlockedBy(getHasName(ModBlocks.ASTER.get()), has(ModBlocks.ASTER.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.WHITE_DYE, 2)
                .requires(ModBlocks.CATTAIL.get())
                .unlockedBy(getHasName(ModBlocks.CATTAIL.get()), has(ModBlocks.CATTAIL.get()))
                .save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                      ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                      ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory,
                pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pSerializer,
                                                                       AbstractCookingRecipe.Factory<T> pRecipeFactory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory,
                                                                       ItemLike pResult, float pExperience, int pCookingTime,
                                                                       String pGroup, String pSuffix) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                    pSerializer, pRecipeFactory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, MaplecraftMod.MOD_ID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }

    }
}
