package net.licketysplitter.maplecraft.worldgen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> RED_MAPLE_PLACED_KEY = registerKey("red_maple_placed");
    public static final ResourceKey<PlacedFeature> SUGAR_MAPLE_PLACED_KEY = registerKey("sugar_maple_placed");

    public static final ResourceKey<PlacedFeature> ASTER_PLACED_KEY = registerKey("aster_placed");
    public static final ResourceKey<PlacedFeature> CATTAIL_PLACED_KEY = registerKey("cattail_placed");

    public static final ResourceKey<PlacedFeature> PLACEMENT_FANCY_RED_MAPLE =
            registerKey("placement_fancy_red_maple");
    public static final ResourceKey<PlacedFeature> PLACEMENT_RED_MAPLE =
            registerKey("placement_red_maple");

    public static final ResourceKey<PlacedFeature> PLACEMENT_FANCY_SUGAR_MAPLE =
            registerKey("placement_fancy_sugar_maple");
    public static final ResourceKey<PlacedFeature> PLACEMENT_SUGAR_MAPLE =
            registerKey("placement_sugar_maple");

    public static final ResourceKey<PlacedFeature> LEAF_COVER = registerKey("leaf_cover");

    public static final ResourceKey<PlacedFeature> APPLE_TREE = registerKey("apple_tree");

    public static final ResourceKey<PlacedFeature> DISK_MUD = registerKey("disk_mud");

    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, RED_MAPLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RANDOM_RED_MAPLE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.RED_MAPLE_SAPLING.get()));

        register(context, SUGAR_MAPLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RANDOM_SUGAR_MAPLE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.SUGAR_MAPLE_SAPLING.get()));

        register(context, ASTER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ASTER_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        PlacementUtils.register(
                context, CATTAIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CATTAIL_KEY), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
        );

        Holder<ConfiguredFeature<?, ?>> fancyRedMapleBees =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.FANCY_RED_MAPLE_BEES_005_KEY);
        Holder<ConfiguredFeature<?, ?>> redMapleBees =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_MAPLE_KEY);

        PlacementUtils.register(context,
                PLACEMENT_FANCY_RED_MAPLE, fancyRedMapleBees,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));
        PlacementUtils.register(context,
                PLACEMENT_RED_MAPLE, redMapleBees,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));


        Holder<ConfiguredFeature<?, ?>> fancySugarMapleBees =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.FANCY_SUGAR_MAPLE_BEES_005_KEY);
        Holder<ConfiguredFeature<?, ?>> sugarMapleBees =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SUGAR_MAPLE_KEY);

        PlacementUtils.register(context,
                PLACEMENT_FANCY_SUGAR_MAPLE, fancySugarMapleBees,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));
        PlacementUtils.register(context,
                PLACEMENT_SUGAR_MAPLE, sugarMapleBees,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));

        // CLEAN UP LATER
        //ModTreePlacements.bootstrap(context);

        Holder<ConfiguredFeature<?, ?>> leafCover = configuredFeatures.getOrThrow(ModConfiguredFeatures.LEAF_COVER);

        PlacementUtils.register(context, LEAF_COVER, leafCover, BiomeFilter.biome());

        register(context, APPLE_TREE, configuredFeatures.getOrThrow(ModConfiguredFeatures.WILD_APPLE_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 0),
                        ModBlocks.APPLE_SAPLING.get()));

        PlacementUtils.register(
                context,
                DISK_MUD,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DISK_MUD),
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
                BiomeFilter.biome()
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
