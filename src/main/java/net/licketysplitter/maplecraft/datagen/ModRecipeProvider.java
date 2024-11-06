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
import net.minecraft.world.level.block.Block;
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

        woodFromLogs(recipeOutput, ModBlocks.MAPLE_WOOD.get(), ModBlocks.MAPLE_LOG.get());
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_LOG.get());
        planksFromLogs(recipeOutput, ModBlocks.MAPLE_PLANKS.get(), ModTags.Items.MAPLE_LOGS, 4);
        unlockAndSave(stairBuilder(ModBlocks.MAPLE_STAIRS.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);
        slab(recipeOutput, RecipeCategory.MISC, ModBlocks.MAPLE_SLAB.get(), ModBlocks.MAPLE_PLANKS.get());
        unlockAndSave(buttonBuilder(ModBlocks.MAPLE_BUTTON.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.MAPLE_PRESSURE_PLATE.get(), ModBlocks.MAPLE_PLANKS.get());
        unlockAndSave(fenceBuilder(ModBlocks.MAPLE_FENCE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(fenceGateBuilder(ModBlocks.MAPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(doorBuilder(ModBlocks.MAPLE_DOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(trapdoorBuilder(ModBlocks.MAPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get())),
                ModBlocks.MAPLE_PLANKS.get(), recipeOutput);


        woodFromLogs(recipeOutput, ModBlocks.APPLE_WOOD.get(), ModBlocks.APPLE_LOG.get());
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_APPLE_WOOD.get(), ModBlocks.STRIPPED_APPLE_LOG.get());
        planksFromLogs(recipeOutput, ModBlocks.APPLE_PLANKS.get(), ModTags.Items.APPLE_LOGS, 4);
        unlockAndSave(stairBuilder(ModBlocks.APPLE_STAIRS.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);
        slab(recipeOutput, RecipeCategory.MISC, ModBlocks.APPLE_SLAB.get(), ModBlocks.APPLE_PLANKS.get());
        unlockAndSave(buttonBuilder(ModBlocks.APPLE_BUTTON.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.APPLE_PRESSURE_PLATE.get(), ModBlocks.APPLE_PLANKS.get());
        unlockAndSave(fenceBuilder(ModBlocks.APPLE_FENCE.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(fenceGateBuilder(ModBlocks.APPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(doorBuilder(ModBlocks.APPLE_DOOR.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);
        unlockAndSave(trapdoorBuilder(ModBlocks.APPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.APPLE_PLANKS.get())),
                ModBlocks.APPLE_PLANKS.get(), recipeOutput);

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

    private void unlockAndSave(RecipeBuilder recipeBuilder, ItemLike unlockedBy, RecipeOutput recipeOutput){
        recipeBuilder.unlockedBy(getHasName(unlockedBy), has(unlockedBy)).save(recipeOutput);
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
