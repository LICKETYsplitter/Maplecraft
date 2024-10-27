package net.licketysplitter.maplecraft.worldgen.tree;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.worldgen.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> PLACEMENT_FANCY_RED_MAPLE_BEES_0002 =
            registerKey("placement_fancy_red_maple_bees_0002_placement");
    public static final ResourceKey<PlacedFeature> PLACEMENT_RED_MAPLE_BEES_0002 =
            registerKey("placement_red_maple_bees_0002");

    public static void bootstrap(BootstrapContext<PlacedFeature> pContext){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = pContext.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> fancyRedMapleBees0002 =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.FANCY_RED_MAPLE_BEES_0002_KEY);
        Holder<ConfiguredFeature<?, ?>> redMapleBees0002 =
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_MAPLE_BEES_0002_KEY);


        PlacementUtils.register(pContext,
                PLACEMENT_FANCY_RED_MAPLE_BEES_0002, fancyRedMapleBees0002,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));
        PlacementUtils.register(pContext,
                PLACEMENT_RED_MAPLE_BEES_0002, redMapleBees0002,
                PlacementUtils.filteredByBlockSurvival(ModBlocks.RED_MAPLE_SAPLING.get()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
    }
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
