package net.licketysplitter.maplecraft.worldgen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.worldgen.tree.ModTreePlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.OptionalInt;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_KEY = registerKey("red_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_KEY = registerKey("fancy_red_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_BEES_0002_KEY = registerKey("red_maple_bees_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_BEES_002_KEY = registerKey("red_maple_bees_002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_BEES_005_KEY = registerKey("red_maple_bees_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_BEES_0002_KEY = registerKey("fancy_red_maple_bees_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_BEES_002_KEY = registerKey("fancy_red_maple_bees_002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_BEES_005_KEY = registerKey("fancy_red_maple_bees_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_BEES_KEY = registerKey("fancy_red_maple_bees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_RED_MAPLE_KEY = registerKey("random_red_maple");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SUGAR_MAPLE_KEY = registerKey("sugar_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SUGAR_MAPLE_KEY = registerKey("fancy_sugar_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUGAR_MAPLE_BEES_0002_KEY = registerKey("sugar_maple_bees_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUGAR_MAPLE_BEES_002_KEY = registerKey("sugar_maple_bees_002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUGAR_MAPLE_BEES_005_KEY = registerKey("sugar_maple_bees_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SUGAR_MAPLE_BEES_0002_KEY = registerKey("fancy_sugar_maple_bees_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SUGAR_MAPLE_BEES_002_KEY = registerKey("fancy_sugar_maple_bees_002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SUGAR_MAPLE_BEES_005_KEY = registerKey("fancy_sugar_maple_bees_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SUGAR_MAPLE_BEES_KEY = registerKey("fancy_sugar_maple_bees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_SUGAR_MAPLE_KEY = registerKey("random_sugar_maple");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASTER_KEY = registerKey("aster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL_KEY = registerKey("cattail");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        createAllMaples(context, ModBlocks.RED_MAPLE_LEAVES.get(),
                RED_MAPLE_KEY,
                FANCY_RED_MAPLE_KEY,
                RED_MAPLE_BEES_0002_KEY,
                RED_MAPLE_BEES_002_KEY,
                RED_MAPLE_BEES_005_KEY,
                FANCY_RED_MAPLE_BEES_0002_KEY,
                FANCY_RED_MAPLE_BEES_002_KEY,
                FANCY_RED_MAPLE_BEES_005_KEY,
                FANCY_RED_MAPLE_BEES_KEY);

        createAllMaples(context, ModBlocks.SUGAR_MAPLE_LEAVES.get(),
                SUGAR_MAPLE_KEY,
                FANCY_SUGAR_MAPLE_KEY,
                SUGAR_MAPLE_BEES_0002_KEY,
                SUGAR_MAPLE_BEES_002_KEY,
                SUGAR_MAPLE_BEES_005_KEY,
                FANCY_SUGAR_MAPLE_BEES_0002_KEY,
                FANCY_SUGAR_MAPLE_BEES_002_KEY,
                FANCY_SUGAR_MAPLE_BEES_005_KEY,
                FANCY_SUGAR_MAPLE_BEES_KEY);

        register(context,
                ASTER_KEY,
                Feature.FLOWER,
                new RandomPatchConfiguration(32, 6, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.ASTER.get())))));

        register(context,
                CATTAIL_KEY,
                Feature.FLOWER,
                new RandomPatchConfiguration(32, 6, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CATTAIL.get())))));

        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> fancyRed = placedFeatures.getOrThrow(ModPlacedFeatures.PLACEMENT_FANCY_RED_MAPLE);
        Holder<PlacedFeature> regularRed = placedFeatures.getOrThrow(ModPlacedFeatures.PLACEMENT_RED_MAPLE);
        FeatureUtils.register(
                context,
                RANDOM_RED_MAPLE_KEY,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(fancyRed, 0.1f)), regularRed));
        Holder<PlacedFeature> fancySugar = placedFeatures.getOrThrow(ModPlacedFeatures.PLACEMENT_FANCY_SUGAR_MAPLE);
        Holder<PlacedFeature> regularSugar = placedFeatures.getOrThrow(ModPlacedFeatures.PLACEMENT_SUGAR_MAPLE);
        FeatureUtils.register(
                context,
                RANDOM_SUGAR_MAPLE_KEY,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(fancySugar, 0.1f)), regularSugar));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block pLogBlock, Block pLeavesBlock, int pBaseHeight, int pHeightRandA, int pHeightRandB, int pRadius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(pLogBlock),
                new StraightTrunkPlacer(pBaseHeight, pHeightRandA, pHeightRandB), BlockStateProvider.simple(pLeavesBlock),
                new BlobFoliagePlacer(ConstantInt.of(pRadius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createMaple(Block pLeaveBlock) {
        return createStraightBlobTree(ModBlocks.MAPLE_LOG.get(), pLeaveBlock, 4, 2, 0, 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyMaple(Block pLeaveBlock) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
                new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(pLeaveBlock),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    private static void createAllMaples(BootstrapContext<ConfiguredFeature<?, ?>> pContext, Block pLeaveBlock,
                                 ResourceKey<ConfiguredFeature<?, ?>> pMaple, ResourceKey<ConfiguredFeature<?, ?>> pFancyMaple,
                                 ResourceKey<ConfiguredFeature<?, ?>> pMapleBees0002, ResourceKey<ConfiguredFeature<?, ?>> pMapleBees002,
                                 ResourceKey<ConfiguredFeature<?, ?>> pMapleBees005, ResourceKey<ConfiguredFeature<?, ?>> pFancyMapleBees0002,
                                 ResourceKey<ConfiguredFeature<?, ?>> pFancyMapleBees002, ResourceKey<ConfiguredFeature<?, ?>> pFancyMapleBees005,
                                 ResourceKey<ConfiguredFeature<?, ?>> pFancyMapleBees) {
        BeehiveDecorator $$3 = new BeehiveDecorator(0.002F);
        BeehiveDecorator $$5 = new BeehiveDecorator(0.02F);
        BeehiveDecorator $$6 = new BeehiveDecorator(0.05F);
        BeehiveDecorator $$7 = new BeehiveDecorator(1.0F);


        FeatureUtils.register(pContext, pMaple, Feature.TREE, createMaple(pLeaveBlock).build());
        FeatureUtils.register(pContext, pFancyMaple, Feature.TREE, createFancyMaple(pLeaveBlock).build());

        FeatureUtils.register(pContext, pMapleBees0002, Feature.TREE, createMaple(pLeaveBlock).decorators(List.of($$3)).build());
        FeatureUtils.register(pContext, pMapleBees002, Feature.TREE, createMaple(pLeaveBlock).decorators(List.of($$5)).build());
        FeatureUtils.register(pContext, pMapleBees005, Feature.TREE, createMaple(pLeaveBlock).decorators(List.of($$6)).build());

        FeatureUtils.register(pContext, pFancyMapleBees0002, Feature.TREE, createFancyMaple(pLeaveBlock).decorators(List.of($$3)).build());
        FeatureUtils.register(pContext, pFancyMapleBees002, Feature.TREE, createFancyMaple(pLeaveBlock).decorators(List.of($$5)).build());
        FeatureUtils.register(pContext, pFancyMapleBees005, Feature.TREE, createFancyMaple(pLeaveBlock).decorators(List.of($$6)).build());
        FeatureUtils.register(pContext, pFancyMapleBees, Feature.TREE, createFancyMaple(pLeaveBlock).decorators(List.of($$7)).build());
    }
}
